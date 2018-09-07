package com.example.edu;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.edu.databinding.ActivityOpenMeetingBinding;
import com.example.edu.model.BoardModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

public class OpenMeetingActivity extends AppCompatActivity {

    ActivityOpenMeetingBinding b;
    ArrayAdapter spinnerAdapter;
    RecyclerAdapter_Likes adapter;

    Spinner spinner;
    RadioGroup rgStyle;
    RadioButton rbMentor, rbStudy;
    Button btnRegister;
    EditText etGroupTitle, etShortTitle, etLimit, etExplain;
    ImageView ivCheckTitle, ivCheckLimit;
    RecyclerView recycle;
    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;

    private View h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = DataBindingUtil.setContentView(this, R.layout.activity_open_meeting);


        btnRegister = (Button) findViewById(R.id.btnRegister);
        etGroupTitle = (EditText) findViewById(R.id.etGroupTitle);
        etShortTitle = (EditText) findViewById(R.id.etShortTitle);
        etLimit = (EditText) findViewById(R.id.etLimit);
        etExplain = (EditText) findViewById(R.id.etExplain);
        ivCheckLimit = (ImageView) findViewById(R.id.ivCheckLimit);
        ivCheckTitle = (ImageView) findViewById(R.id.ivCheckTitle);
        h = getLayoutInflater().inflate(R.layout.activity_main_fragment_1, null, false);
        recycle = (RecyclerView) h.findViewById(R.id.recycleView);
        spinner = (Spinner) findViewById(R.id.spnTopic);
        rgStyle = (RadioGroup) findViewById(R.id.rgStyle);
        rbMentor = (RadioButton) findViewById(R.id.rbMentor);
        rbStudy = (RadioButton) findViewById(R.id.rbStudy);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tBar);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.topic, android.R.layout.simple_dropdown_item_1line);
        adapter = new RecyclerAdapter_Likes(this);

        recycle.setAdapter(adapter);
        spinner.setAdapter(spinnerAdapter);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate() == false) { // 데이터 로컬에서 자체 검증
                    return;
                } else { // 로컬 자체 검증이 끝나면 서버 검증을 통해 로그인이 정상적으로 되었는지 체크
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


    void RegisterEvent() { // 게시글 등록이 정상적으로 됐는지 확인해주고 다음 화면으로 넘겨줌
        Intent intent = getIntent(); //uid값 받아옴
        String uid = intent.getStringExtra("uid");
        int id = rgStyle.getCheckedRadioButtonId();
        RadioButton rb = (RadioButton)findViewById(id);


        BoardModel BoardModel = new BoardModel();
        BoardModel.groupName = etGroupTitle.getText().toString();
        BoardModel.groupShortTitle = etShortTitle.getText().toString();
        BoardModel.groupLimit = Integer.parseInt(etLimit.getText().toString());
        BoardModel.groupStyle = rb.getText().toString();
        BoardModel.groupTopic = spinner.getSelectedItem().toString();
        BoardModel.groupExplain = etExplain.getText().toString();

        BoardModel.uid = uid;

//        FirebaseDatabase.getInstance().getReference().child("group").child(uid).setValue(BoardModel).addOnSuccessListener(new OnSuccessListener<Void>() {
//        FirebaseDatabase.getInstance().getReference().child("group").push().child(uid).setValue(BoardModel).addOnSuccessListener(new OnSuccessListener<Void>() {
        FirebaseDatabase.getInstance().getReference().child("group").push().setValue(BoardModel).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override //게시글 작성이 성공하면 화면 toast메시지 출력 및 finish() 실행
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(), "게시글 등록이 완료되었습니다.", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    private boolean validate() {
        boolean valid = true;

        if (etGroupTitle.getText().toString().isEmpty()) {
            etGroupTitle.setError("제목을 입력해 주세요!");
            valid = false;
        } else {
            etGroupTitle.setError(null);
        }
        if (etShortTitle.getText().toString().isEmpty()) {
            etShortTitle.setError("짧은 소개 부분를 입력해 주세요!");
            valid = false;
        } else {
            etShortTitle.setError(null);
        }
        if (etLimit.getText().toString().isEmpty()) {
            etLimit.setError("제한 인원을 입력해 주세요!");
            valid = false;
        } else {
            etLimit.setError(null);
        }
//        if (spinner.getSelectedItem().toString().isEmpty()) {
//            spinner.setError("Password를 입력해 주세요!");
//            valid = false;
//        } else {
//            spinner.setError(null);
//        }
        if (etExplain.getText().toString().isEmpty()) {
            etExplain.setError("상세 설명을 입력해 주세요!");
            valid = false;
        } else {
            etExplain.setError(null);
        }
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

    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime)
        {
            super.onBackPressed();
        }
        else
        {
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(), "한 번 더 누르시면 전 화면으로 돌아갑니다", Toast.LENGTH_SHORT).show();
        }
    }
}
