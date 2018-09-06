package com.example.edu;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.Spinner;
import android.widget.Toast;

import com.example.edu.databinding.ActivityOpenMeetingBinding;
import com.example.edu.model.UserModel;
import com.example.edu.model.groupTest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
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

               /* FirebaseAuth.getInstance()
                        .createUserWithEmailAndPassword(etGroupTitle.getText().toString(),etLimit.getText().toString())
                        .addOnCompleteListener(OpenMeetingActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(!task.isSuccessful()) {
                                    Toast.makeText(OpenMeetingActivity.this, "회원가입 오류 : "+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "회원가입이 완료 되었습니다.", Toast.LENGTH_LONG).show();

                                   groupTest gTest = new groupTest();
                                   gTest.groupName = etGroupTitle.getText().toString();
                                   gTest.groupCode = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                    //회원가입 할 때마다 uid가 담겨서 회원가입이 된다.
                                    //이 uid를 통해 내가 원하는 사람이랑 채팅을 할 수 있게 된다.

                                    String uid = task.getResult().getUser().getUid();
                                    FirebaseDatabase.getInstance().getReference().child("groups").child(uid).setValue(gTest);
                                    finish();
                                }
                            }
                        });*/





                Intent toMain = new Intent(OpenMeetingActivity.this, MainActivity.class);
                startActivity(toMain);

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
