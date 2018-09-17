package com.example.edu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.edu.RecyclerAdpater.ViewPagerAdapter;
import com.example.edu.fragment.AccountFragment;
import com.example.edu.fragment.ChatFragment;
import com.example.edu.fragment.MainFragment_1;
import com.example.edu.fragment.MainFragment_2;
import com.example.edu.model.StudyRoomModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;
    private FirebaseAuth firebaseAuth;

    private BottomNavigationView bottomNavigationView;
    private ViewPager viewPager;
    private MenuItem prevMenuItem;

    private String week;
    private String roomNumber;


    public static Fragment sF1, sF2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 툴바 설정
        toolbar = findViewById(R.id.tBar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);


        // Navigation과 ViewPager 설정
        bottomNavigationView = findViewById(R.id.main_navigationView);
        viewPager = findViewById(R.id.viewPager);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(MainFragment_1.newInstance());
        adapter.addFragment(ChatFragment.newInstance());
        adapter.addFragment(MainFragment_2.newInstance());
        adapter.addFragment(AccountFragment.newInstance());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        findViewById(R.id.btnRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, OpenMeetingActivity.class);
                startActivity(intent);

            }
        });
        passPushTokenToServer(); //push 테스트
    }

    void passPushTokenToServer() { //push 테스트
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String token = FirebaseInstanceId.getInstance().getToken();
        //firebase상에서는 해쉬맵으로만 업데이트 가능하다 (push 토큰)
        Map<String, Object> map = new HashMap<>();
        map.put("pushToken", token);

        FirebaseDatabase.getInstance().getReference().child("users").child(uid).updateChildren(map);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, "회원정보 수정");
        menu.add(0, 2, 0, "예약하기");
        menu.add(0, 3, 0, "로그아웃");
        menu.add(0, 4, 0, "관리자 db 생성");

        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case 1:
                Intent infoUpdate = new Intent(MainActivity.this, InfoUpdateActivity.class);
                startActivity(infoUpdate);
                return true;
            case 2:
                Intent Intent = new Intent(MainActivity.this, ReservationActivity.class);//예약화면 임시 확인용!!
                startActivity(Intent);
                return true;
            case 3:
                firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.signOut(); // 로그아웃
                Intent logOut = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(logOut);
                finish();
                return true;
            case 4:
                int i, j, k;
                StudyRoomModel.Day day = new StudyRoomModel.Day();
                day.uid = "uidtest";
                day.reservation = false;
                for (i = 0; i < 3; i++) {
                    switch (i) {
                        case 0:
                            roomNumber = "No101";
                            break;
                        case 1:
                            roomNumber = "No102";
                            break;
                        case 2:
                            roomNumber = "No103";
                            break;
                    }
                    for (j = 0; j < 5; j++) {
                        switch (j) {
                            case 0:
                                week = "Monday";
                                break;
                            case 1:
                                week = "Tuesday";
                                break;
                            case 2:
                                week = "Wednesday";
                                break;
                            case 3:
                                week = "Thursday";
                                break;
                            case 4:
                                week = "Friday";
                                break;
                        }
                        for (k = 9; k < 17; k++) {
                            day.time = k;
                            FirebaseDatabase.getInstance().getReference().child("studyroom").child(roomNumber).child(week).push().setValue(day).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
//                                Toast.makeText(getApplicationContext(),"월요일 추가 완료",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }

                }



//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//                ChatModel chatModel = new ChatModel();
//                chatModel.users.put(uid,true);
//                chatModel.users.put(destinationUid, true);
//                if(chatRoomUid == null) {
//                    button.setEnabled(false); //전송 버튼을 연속해서 누를 경우 체크도 하기 전에 방이 n만큼 만들어질 수 있다(버그)
//                    //때문에 한번 전송을 누르면 체크가 끝날 때까지 버튼을 비활성화 상태로 변경한다.
//                    FirebaseDatabase.getInstance().getReference().child("chatrooms").push().setValue(chatModel)
//                            .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void aVoid) {
//                                    checkChatRoom();
//                                }
//                            });
//                    //checkChatRoom(); 이곳에 작성하게 되면 FirebaseDatabase... 요청 시 인터넷이 끊기는 경우가 간혹 있는데
//                    //이 경우 데이터를 넣지도 않았는데 방을 체크하게 되는 경우가 발생한다. 때문에 데이터 입력이 완료 되었다고 했을 때
//                    //체크하도록 코딩한다.
//                } else {
//                    ChatModel.Comment comment = new ChatModel.Comment();
//                    comment.uid = uid;
//                    comment.message = editText.getText().toString();
//                    comment.timestamp = ServerValue.TIMESTAMP; //firebase가 제공하는 메소드
//                    FirebaseDatabase.getInstance().getReference()
//                            .child("chatrooms").child(chatRoomUid).child("comments").push().setValue(comment).addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            sendFcm();
//                            editText.setText(""); //db에 메세지를 정상적으로 전송하였으면 text 부분 공백 처리
//                        }
//                    });
//                }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//                void checkChatRoom () {
//                FirebaseDatabase.getInstance().getReference().child("chatrooms").orderByChild("users/" + uid).equalTo(true) //orderByChild : 지정된 하위 키의 값에 따라 결과를 정렬
//                        .addListenerForSingleValueEvent(new ValueEventListener() { //equalTo : 지정된 키 또는 값과 동일한 항목을 반환
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                for (DataSnapshot item : dataSnapshot.getChildren()) {
//                                    ChatModel chatModel = item.getValue(ChatModel.class);
//                                    if (chatModel.users.containsKey(destinationUid)) { //hashmap에 값이 있으면 true, 없으면 false 반환
//                                        chatRoomUid = item.getKey(); // room 방 id (여기서 id는 최초 생성 시 랜덤으로 생성되는 값을 말함)
//                                        button.setEnabled(true); // 체크가 끝났으므로 버튼 활성화
//                                        recyclerView.setLayoutManager(new LinearLayoutManager(MessageActivity.this));
//                                        recyclerView.setAdapter(new RecyclerViewAdapter());
//                                    }
//                                }
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                            }
//                        });
//            }
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_list:
                viewPager.setCurrentItem(0);
                return true;
            case R.id.action_chat:
                viewPager.setCurrentItem(1);
                return true;
            case R.id.action_star:
                viewPager.setCurrentItem(2);
                return true;
            case R.id.action_account:
                viewPager.setCurrentItem(3);
                return true;
        }
        return false;
    }
}
