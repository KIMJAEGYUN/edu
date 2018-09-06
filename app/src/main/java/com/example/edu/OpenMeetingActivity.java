package com.example.edu;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.edu.databinding.ActivityOpenMeetingBinding;
import com.example.edu.model.BoardModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OpenMeetingActivity extends AppCompatActivity {

    ActivityOpenMeetingBinding b;
    ArrayAdapter spinnerAdapter;
    RecyclerAdapter_Likes adapter;

    Spinner spinner;
    Button regitBtn;
    EditText etGroupTitle, etShortTitle, etLimit, etExplain;
    ImageView ivCheckTitle, ivCheckLimit;
    RecyclerView recycle;
    String a, c, d;
    private View h;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = DataBindingUtil.setContentView(this, R.layout.activity_open_meeting);


        regitBtn = (Button) findViewById(R.id.regitBtn);
        etGroupTitle = (EditText) findViewById(R.id.etGroupTitle);
        etShortTitle = (EditText) findViewById(R.id.etShortTitle);
        etLimit = (EditText) findViewById(R.id.etLimit);
        ivCheckLimit = (ImageView) findViewById(R.id.ivCheckLimit);
        ivCheckTitle = (ImageView) findViewById(R.id.ivCheckTitle);
        h = getLayoutInflater().inflate(R.layout.activity_main_fragment_1, null, false);
        recycle = (RecyclerView) h.findViewById(R.id.recycle);
        spinner = (Spinner) findViewById(R.id.topicSpinner);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tBar);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.topic, android.R.layout.simple_dropdown_item_1line);
        adapter = new RecyclerAdapter_Likes(this); // 오류로 인하여 주석처리. 추후 병호가 코딩할 예정.

        recycle.setAdapter(adapter);
        spinner.setAdapter(spinnerAdapter);


        regitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate() == false) { // 데이터 로컬에서 자체 검증
                    return;
                } else { // 로컬 자체 검증이 끝나면 서버 검증을 통해 로그인이 정상적으로 되었는지 체크
                    Log.e("test2","미팅등록 버튼까지 옴");
                    RegisterEvent();
                }
            }
        });

        etGroupTitle.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                checkInputTitle();
                return false;
            }
        });

        etGroupTitle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                checkInputTitle();
            }
        });

        etLimit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                checkInputLimit();
                return false;
            }
        });

        etLimit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                checkInputLimit();
            }
        });
    }

    void RegisterEvent() { // 회원가입이 정상적으로 됐는지 확인해주고 다음 화면으로 넘겨줌. 확인하고 넘겨주는 이 2가지를 분리할 예정. LoginActivity 처럼.
        Intent intent = getIntent(); //uid값 받아옴

        Log.e("test2","미팅등록이벤트까지 옴");
        String uid = intent.getStringExtra("uid");
        Log.e("test2","미팅등록이벤트에서 user받아온 뒤 uid에 저장 성공");

        BoardModel BoardModel = new BoardModel();
        BoardModel.groupName = etGroupTitle.getText().toString();
        BoardModel.uid = uid;

        FirebaseDatabase.getInstance().getReference().child("group").child(uid).setValue(BoardModel).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override //게시글 작성이 성공하면 화면 finish()
            public void onSuccess(Void aVoid) {
                finish();
            }
        });
    }

    private boolean validate(){
        boolean valid = true;
        String title;
        title = etGroupTitle.getText().toString();

        if (title.isEmpty()) {
            etGroupTitle.setError("제목을 입력해 주세요!");
            valid = false;
        } else {
            etGroupTitle.setError(null);
        }

//        if (name.isEmpty()) {
//            etName.setError("이름을 입력해 주세요!");
//            valid = false;
//        } else {
//            etName.setError(null);
//        }
//
//        if (password.isEmpty()) {
//            etPassword.setError("Password를 입력해 주세요!");
//            valid = false;
//        } else {
//            etPassword.setError(null);
//        }
        return valid;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void checkInputTitle() {
        String id = etGroupTitle.getText().toString();
        if (id.isEmpty()) {
            ivCheckTitle.setImageResource(R.drawable.ic_check_gray);
        } else {
            ivCheckTitle.setImageResource(R.drawable.ic_check_black);
        }
    }

    private void checkInputLimit() {
        String password = etLimit.getText().toString();
        if (password.isEmpty()) {
            ivCheckLimit.setImageResource(R.drawable.ic_check_gray);
        } else {
            ivCheckLimit.setImageResource(R.drawable.ic_check_black);
        }
    }
}
