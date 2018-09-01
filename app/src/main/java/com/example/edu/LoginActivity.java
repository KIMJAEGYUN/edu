package com.example.edu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin, btnRegister;
    TextView btnFindPassword;
    EditText etId, etPassword;
    ImageView ivCheckId, ivCheckPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        btnFindPassword = findViewById(R.id.btnFindPassword);

        etId = findViewById(R.id.etId);
        etPassword = findViewById(R.id.etPassword);

        ivCheckId = findViewById(R.id.ivCheckId);
        ivCheckPassword = findViewById(R.id.ivCheckPassword);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnFindPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //비밀번호찾기 연결
            }
        });

        etId.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                checkInputId();
                return false;
            }
        });

        etId.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                checkInputId();
            }
        });

        etPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                checkInputPassword();
                return false;
            }
        });

        etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                checkInputPassword();
            }
        });
    }

    private boolean validate(){
        boolean valid = true;
        String id, password;
        id = etId.getText().toString();
        password = etPassword.getText().toString();

        if (id.isEmpty()) {
            etId.setError("ID를 입력해 주세요!");
            valid = false;
        } else {
            etId.setError(null);
        }

        if (password.isEmpty()) {
            etPassword.setError("Password를 입력해 주세요!");
            valid = false;
        } else {
            etPassword.setError(null);
        }
        return valid;
    }

    //로그인
    public void login() {
        if (validate() == false){
            return;
        }
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void checkInputId() {
        String id = etId.getText().toString();
        if (id.isEmpty()) {
            ivCheckId.setImageResource(R.drawable.ic_check_gray);
        } else {
            ivCheckId.setImageResource(R.drawable.ic_check_black);
        }
    }

    private void checkInputPassword() {
        String password = etPassword.getText().toString();
        if (password.isEmpty()) {
            ivCheckPassword.setImageResource(R.drawable.ic_check_gray);
        } else {
            ivCheckPassword.setImageResource(R.drawable.ic_check_black);
        }
    }
}
