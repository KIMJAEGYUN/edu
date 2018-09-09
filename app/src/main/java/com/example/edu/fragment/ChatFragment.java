package com.example.edu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_chat,container,false);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
    }
}
