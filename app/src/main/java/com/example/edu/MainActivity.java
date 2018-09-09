package com.example.edu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.edu.fragment.ChatActivity;
import com.example.edu.fragment.MainFragment_1;
import com.example.edu.fragment.MainFragment_2;
import com.google.firebase.auth.FirebaseAuth;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;


public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;
    private FirebaseAuth firebaseAuth;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.main_navigationView);
        toolbar = findViewById(R.id.tBar);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentPagerAdapter adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(),
                FragmentPagerItems.with(this)
                        .add("스터디 현황 ", MainFragment_1.class)
                        .add("관심 목록", MainFragment_2.class)
                        .create());

        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);

        SmartTabLayout viewPagerTab = findViewById(R.id.pagerTab);
        viewPagerTab.setViewPager(viewPager);


        findViewById(R.id.btnRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                Intent nextAct = new Intent(MainActivity.this, OpenMeetingActivity.class);
                nextAct.putExtra("uid",intent.getStringExtra("uid"));
                startActivity(nextAct);

            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_list:
                        return true;
                    case R.id.action_chat:
                        Toast.makeText(MainActivity.this,"챗 클릭함",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.action_account:
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(0, 1, 0, "회원정보 수정");
        menu.add(0, 2, 0, "로그아웃");

        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(android.view.MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                finish();
                return true;

            case 1:
                Intent infoUpdate = new Intent(MainActivity.this, InfoUpdateActivity.class);
                startActivity(infoUpdate);
                finish(); //test
                return true;

            case 2:
                firebaseAuth = FirebaseAuth.getInstance(); //로그아웃 test : 추후 코드 리스너 등의 방법으로 수정할 예정
                firebaseAuth.signOut(); //로그아웃 test : 추후 코드 리스너 등의 방법으로 수정할 예정
                Intent logOut = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(logOut);
                finish(); //로그아웃 test : 추후 코드 리스너 등의 방법으로 수정할 예정
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime)
        {
            super.onBackPressed();
        }
        else
        {
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(), "한 번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
    }
}
