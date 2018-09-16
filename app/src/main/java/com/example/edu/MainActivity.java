package com.example.edu;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.edu.fragment.ChatFragment;
import com.example.edu.fragment.MainFragment_1;
import com.example.edu.fragment.MainFragment_2;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;
    private FirebaseAuth firebaseAuth;
    private BottomNavigationView bottomNavigationView;

    public static Fragment sF1, sF2;
    ChatFragment chat = new ChatFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.main_navigationView);
        toolbar = findViewById(R.id.tBar);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final FragmentPagerAdapter adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(),
                FragmentPagerItems.with(this)
                        .add("스터디 현황 ", MainFragment_1.class)
                        .add("관심 목록", MainFragment_2.class)
                        .create());

        final ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);

        final SmartTabLayout viewPagerTab = findViewById(R.id.pagerTab);
        viewPagerTab.setViewPager(viewPager);


        findViewById(R.id.btnRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, OpenMeetingActivity.class);
                startActivity(intent);

            }
        });

        final FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, chat, "tag");
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        final RecyclerView chat_recyclerview = findViewById(R.id.chat_recyclerview);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_list:
//                        getSupportFragmentManager().beginTransaction().detach(sF1).detach(sF2).attach(sF1).attach(sF2).commit();
//                        viewPager.setAdapter(adapter);
//                        viewPagerTab.setViewPager(viewPager);
//                        fragmentTransaction.hide(chat);
//                        fragmentTransaction.commit();
                        try {
                            chat_recyclerview.setVisibility(View.INVISIBLE);
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(),"에러나네",Toast.LENGTH_SHORT).show();
                        }
                        viewPager.setVisibility(View.VISIBLE);
                        viewPagerTab.setVisibility(View.VISIBLE);
                        return true;
                    case R.id.action_chat:
                        fragmentTransaction.commit();
                        viewPager.setVisibility(View.GONE);
                        viewPagerTab.setVisibility(View.GONE);
                        try {
                            chat_recyclerview.setVisibility(View.VISIBLE);
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(),"에러나네",Toast.LENGTH_SHORT).show();
                    }

                        return true;
                    case R.id.action_account:
                        return true;
                }
                return false;
            }
        });
        passPushTokenToServer(); //push 테스트
    }

    void passPushTokenToServer() { //push 테스트

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String token = FirebaseInstanceId.getInstance().getToken();
        //firebase상에서는 해쉬맵으로만 업데이트 가능하다 (push 토큰)
        Map<String,Object> map = new HashMap<>();
        map.put("pushToken",token);

        FirebaseDatabase.getInstance().getReference().child("users").child(uid).updateChildren(map);
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
                firebaseAuth = FirebaseAuth.getInstance(); //로그아웃
                firebaseAuth.signOut(); //로그아웃
                Intent logOut = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(logOut);
                finish(); //로그아웃 test
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime) {
            super.onBackPressed();
        } else {
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(), "한 번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
    }
}
