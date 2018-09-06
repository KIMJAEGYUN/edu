package com.example.edu;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import com.example.edu.chat.MessageActivity;

public class PopUp extends AppCompatActivity {
    Button btnChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);
        btnChat = (findViewById(R.id.btnPopup));


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm); // 창 크기 조절

        int width = dm.widthPixels;
        int height = dm.heightPixels; //높이 너비 가져옴

        getWindow().setLayout((int)(width * 0.9), (int)(height * 0.85)); // 크기 설정 너비 90%, 높이 85%

        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MessageActivity.class);
                ActivityOptions activityOptions = ActivityOptions.makeCustomAnimation(view.getContext(), R.anim.fromright, R.anim.toleft);
                startActivity(intent);
            }
        });
    }
}
