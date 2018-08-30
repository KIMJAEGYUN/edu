package com.example.edu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Window;

public class PopUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm); // 창 크기 조절

        int width = dm.widthPixels;
        int height = dm.heightPixels; //높이 너비 가져옴

        getWindow().setLayout((int)(width * 0.9), (int)(height * 0.85)); // 크기 설정 너비 90%, 높이 85%
    }
}
