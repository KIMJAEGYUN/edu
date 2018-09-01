package com.example.edu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin, btnRegister;
    TextView btnFindPassword;
    EditText etId, etPassword;
    ImageView ivCheckId, ivCheckPassword;
    private FirebaseRemoteConfig firebaseRemoteConfig;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();

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
                if (validate() == false) {
                    return;
                } else {
                    loginEvent();
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        // 로그인 인터페이스 리스너 (로그인 됐는지만 확인)
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null) {
                    //로그인
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    //로그아웃

                }
            }
        };

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
    void loginEvent() { // 로그인이 정상적으로 됐는지 안 됐는지 확인만 해주는. 다음 화면으로 넘겨주는 애는 다른 애다.
        firebaseAuth.signInWithEmailAndPassword(etId.getText().toString(), etPassword.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()) {
                    //로그인 실패한 부분
                    Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
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

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }
}
