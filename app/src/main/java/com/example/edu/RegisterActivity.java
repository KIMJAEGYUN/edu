package com.example.edu;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.edu.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    Toolbar toolbar;
    private EditText etEmail;
    private EditText etName;
    private EditText etPassword;
    private EditText etAnswer;
    private Button btnRegister;
    private Spinner spnQuestion;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etName = (EditText) findViewById(R.id.etName);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etAnswer = (EditText) findViewById(R.id.etAnswer);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        spnQuestion = (Spinner) findViewById(R.id.spnQuestion);

        final String[] question = getResources().getStringArray(R.array.question);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,question);
        spnQuestion.setAdapter(adapter);

        /*spnQuestion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });*/

        toolbar = findViewById(R.id.tBar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //뒤로가기 버튼 생성

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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                //뒤로가기 버튼 클릭 시 로그인 화면 연결
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    void RegisterEvent() { // 회원가입이 정상적으로 됐는지 확인해주고 다음 화면으로 넘겨줌. 확인하고 넘겨주는 이 2가지를 분리할 예정. LoginActivity 처럼.
        FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(etEmail.getText().toString(),etPassword.getText().toString())
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "회원가입 오류 : "+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "회원가입이 완료 되었습니다.", Toast.LENGTH_LONG).show();

                            UserModel userModel = new UserModel();
                            userModel.userName = etName.getText().toString();
                            userModel.uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            //회원가입 할 때마다 uid가 담겨서 회원가입이 된다.
                            //이 uid를 통해 내가 원하는 사람이랑 채팅을 할 수 있게 된다.

                            String uid = task.getResult().getUser().getUid();
                            FirebaseDatabase.getInstance().getReference().child("users").child(uid).setValue(userModel);
                            finish();
                        }
                    }
                });
    }

    private boolean validate(){
        boolean valid = true;
        String email, name, password;
        email = etEmail.getText().toString();
        name = etName.getText().toString();
        password = etPassword.getText().toString();

        if (email.isEmpty()) {
            etEmail.setError("Email를 입력해 주세요!");
            valid = false;
        } else {
            etEmail.setError(null);
        }

        if (name.isEmpty()) {
            etName.setError("이름을 입력해 주세요!");
            valid = false;
        } else {
            etName.setError(null);
        }

        if (password.isEmpty()) {
            etPassword.setError("Password를 입력해 주세요!");
            valid = false;
        } else {
            etPassword.setError(null);
        }
        return valid;
    }
//    @Override //뒤로가기 2번 누를 때 종료되는 코드
//    public void onBackPressed() {
//        if ( pressedTime == 0 ) {
//            Toast.makeText(MainActivity.this, " 한 번 더 누르면 종료됩니다." , Toast.LENGTH_LONG).show();
//            pressedTime = System.currentTimeMillis();
//        }
//        else {
//            int seconds = (int) (System.currentTimeMillis() - pressedTime);
//
//            if ( seconds > 2000 ) {
//                Toast.makeText(MainActivity.this, " 한 번 더 누르면 종료됩니다." , Toast.LENGTH_LONG).show();
//                pressedTime = 0 ;
//            }
//            else {
//                super.onBackPressed();
////                finish(); // app 종료 시키기
//            }
//        }

}
