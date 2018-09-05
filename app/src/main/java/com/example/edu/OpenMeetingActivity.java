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
import android.widget.Spinner;

import com.example.edu.databinding.ActivityOpenMeetingBinding;

public class OpenMeetingActivity extends AppCompatActivity {

    ActivityOpenMeetingBinding b;
    ArrayAdapter spinnerAdapter;
    RecyclerAdpater adpater;

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
        //adpater = new RecyclerAdpater(this); // 오류로 인하여 주석처리. 추후 병호가 코딩할 예정.

        recycle.setAdapter(adpater);
        spinner.setAdapter(spinnerAdapter);


        regitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                a = etGroupTitle.getText().toString();
                // d = etLimit.getText().toString();






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
