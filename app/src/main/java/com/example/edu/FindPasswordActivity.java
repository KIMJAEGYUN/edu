package com.example.edu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FindPasswordActivity extends AppCompatActivity {
    Button btnCommit;
    EditText etEmail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password);

        btnCommit = findViewById(R.id.btnCommit);
        etEmail = findViewById(R.id.etEmail);

        btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findPassword();
            }
        });
    }

    private void findPassword() {
        String email = etEmail.getText().toString();

        if (email.isEmpty()) {
            etEmail.setError("Email을 입력해 주세요!");
        } else {
            etEmail.setError(null);
            //여기부터 기능추가
            Toast.makeText(this, "비밀번호 찾기 성공", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(FindPasswordActivity.this, LoginActivity.class);
            startActivity(intent);
            finishAffinity(); //스택에 있는 모든 엑티비티 종료(삭제)
        }
    }
}
