package com.example.edu;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

import com.example.edu.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;

public class RegisterActivity extends AppCompatActivity {

    Toolbar toolbar;
    private EditText etEmail, etName, etPassword, etPassword2, etAnswer;
    private RadioGroup rgGender;
    private RadioButton rbMan,rbWomen;
    private Button btnRegister;
    private Spinner spnQuestion;
    private ImageView ivUserPhoto,ivCheckEmail,ivCheckName,ivCheckPassword,ivCheckPassword2,ivCheckGender,ivCheckQ;
    private Uri imageUri;
    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;
    private final int CAMERA_CODE = 1111;
    private final int REQUEST_PERMISSION_CODE = 2222;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etEmail = findViewById(R.id.etEmail);
        etName = findViewById(R.id.etName);
        etPassword = findViewById(R.id.etPassword);
        etPassword2 = findViewById(R.id.etPassword2);
        etAnswer = findViewById(R.id.etAnswer);
        btnRegister = findViewById(R.id.btnRegister);
        spnQuestion = findViewById(R.id.spnQuestion);
        ivUserPhoto = findViewById(R.id.ivUserPhoto);
        ivCheckEmail = findViewById(R.id.ivCheckEmail);
        ivCheckName = findViewById(R.id.ivCheckName);
        ivCheckPassword = findViewById(R.id.ivCheckPassword);
        ivCheckPassword2 = findViewById(R.id.ivCheckPassword2);
        ivCheckGender = findViewById(R.id.ivCheckGender);
        ivCheckQ = findViewById(R.id.ivCheckQ);
        rgGender = findViewById(R.id.rgGender);
        rbMan = findViewById(R.id.rbMan);
        rbWomen = findViewById(R.id.rbWomen);

        final String[] question = getResources().getStringArray(R.array.question);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, question);
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
                if (validate() == false) {
                    return;
                } else {
                    RegisterEvent();
                }
            }
        });

        etEmail.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                checkInputEmail();
                return false;
            }
        });

        etEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                checkInputEmail();
            }
        });

        etName.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                checkInputName();
                return false;
            }
        });

        etName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                checkInputName();
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

        etPassword2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                checkInputPassword2();
                return false;
            }
        });

        etPassword2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                checkInputPassword2();
            }
        });

        rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                ivCheckGender.setImageResource(R.drawable.ic_check_black);
            }
        });

        etAnswer.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                checkInputAnswer();
                return false;
            }
        });

        etAnswer.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                checkInputAnswer();
            }
        });
    }
    private void checkInputEmail() {
        String Email = etEmail.getText().toString();
        if (Email.isEmpty()) {
            ivCheckEmail.setImageResource(R.drawable.ic_check_gray);
        } else {
            ivCheckEmail.setImageResource(R.drawable.ic_check_black);
        }
    }

    private void checkInputName() {
        String Name = etName.getText().toString();
        if (Name.isEmpty()) {
            ivCheckName.setImageResource(R.drawable.ic_check_gray);
        } else {
            ivCheckName.setImageResource(R.drawable.ic_check_black);
        }
    }

    private void checkInputPassword() {
        String Password = etPassword.getText().toString();
        if (Password.isEmpty()) {
            ivCheckPassword.setImageResource(R.drawable.ic_check_gray);
        } else {
            ivCheckPassword.setImageResource(R.drawable.ic_check_black);
        }
    }

    private void checkInputPassword2() {
        String Password2 = etPassword2.getText().toString();
        if (Password2.isEmpty()) {
            ivCheckPassword2.setImageResource(R.drawable.ic_check_gray);
        } else {
            ivCheckPassword2.setImageResource(R.drawable.ic_check_black);
        }
    }

    private void checkInputAnswer() {
        String Answer = etAnswer.getText().toString();
        if (Answer.isEmpty()) {
            ivCheckPassword2.setImageResource(R.drawable.ic_check_gray);
        } else {
            ivCheckPassword2.setImageResource(R.drawable.ic_check_black);
        }
    }

    //카메라에서 사진 촬영
    public void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        String url = "temp_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
        imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), url));

        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, 0);
    }

    //앨범에서 사진 가져오기
    public void takeAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK)
            return;

        switch (requestCode) {
            case 1: {
                imageUri = data.getData();
                Log.d("SmartWheel", imageUri.getPath().toString());
            }
            case 0: {
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(imageUri, "image/*");

                intent.putExtra("outputX", 200);
                intent.putExtra("outputY", 100);
                intent.putExtra("aspectX", 1);
                intent.putExtra("aspectY", 1);
                intent.putExtra("scale", true);
                intent.putExtra("return_data", true);
                startActivityForResult(intent, 2);
                break;
            }
            case 2: {
                //크롭 이후의 이미지를 넘겨받음
                if (resultCode != RESULT_OK) {
                    return;
                }

                final Bundle extras = data.getExtras();

                if (extras != null) {
                    Bitmap photo = extras.getParcelable("data");
                    ivUserPhoto.setImageBitmap(photo);
                    break;
                }

                File f = new File(imageUri.getPath());
                if (f.exists()) {
                    f.delete();
                }
            }
        }
    }

    public void onClick(View v) {

        DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                takePhoto();
            }
        };

        DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                takeAlbum();
            }
        };

        new AlertDialog.Builder(this)
                .setTitle("학생증 이미지 선택")
                .setPositiveButton("사진촬영", cameraListener)
                .setNeutralButton("앨범선택", albumListener)
                .show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                //뒤로가기 버튼 클릭 시 로그인 화면 연결
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    void RegisterEvent() { // 회원가입이 정상적으로 됐는지 확인해주고 다음 화면으로 넘겨줌. 확인하고 넘겨주는 이 2가지를 분리할 예정. LoginActivity 처럼.


        FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(etEmail.getText().toString(), etPassword.getText().toString())
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "회원가입 오류 : " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "회원가입이 완료 되었습니다.", Toast.LENGTH_LONG).show();

                            int id = rgGender.getCheckedRadioButtonId();
                            RadioButton rb = (RadioButton) findViewById(id); // 라디오 버튼값 획득

                            UserModel userModel = new UserModel();
                            userModel.userName = etName.getText().toString();
                            userModel.userGender = rb.getText().toString();
                            userModel.userPwQuestion = spnQuestion.getSelectedItem().toString();
                            userModel.userPwAnswer = etAnswer.getText().toString();
                            //userModel.userFavorites = 별 누르면 그룹이 관심목록에 표시 clickListener > 파베에 group Uid가 userFavorites에 추가 > 이것을 관심목록에 표시
                            userModel.uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            //회원가입 할 때마다 uid가 담겨서 회원가입이 된다.
                            //이 uid를 통해 내가 원하는 사람이랑 채팅을 할 수 있게 된다.

                            String uid = task.getResult().getUser().getUid();
                            FirebaseDatabase.getInstance().getReference().child("users").child(uid).setValue(userModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    finish();
                                }
                            });
                        }
                    }
                });
    }

    private boolean validate() {
        boolean valid = true;
        String email, name, password, password2,answer;
        email = etEmail.getText().toString();
        name = etName.getText().toString();
        password = etPassword.getText().toString();
        password2 = etPassword2.getText().toString();
        answer = etAnswer.getText().toString();

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

        if (password2.isEmpty()) {
            etPassword2.setError("Password를 입력해 주세요!");
            valid = false;
        } else {
            etPassword2.setError(null);
        }

        if(rbMan.isChecked() == false && rbWomen.isChecked() == false){
            rbWomen.setError("성별을 선택하세요!");
        } else{
            rbWomen.setError(null);
        }

        if (answer.isEmpty()) {
            etAnswer.setError("답변을 입력해 주세요!");
            valid = false;
        } else {
            etAnswer.setError(null);
        }

        return valid;
    }

    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime) {
            super.onBackPressed();
        } else {
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(), "한 번 더 누르시면 로그인 화면으로 돌아갑니다", Toast.LENGTH_SHORT).show();
        }
    }

}
