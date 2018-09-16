package com.example.edu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.edu.RecyclerAdpater.ReservationRecyclerAdapter;

import java.util.ArrayList;

public class ReservationActivity extends AppCompatActivity {

    Spinner spnRoom;
    RecyclerView rvRoom;
    LinearLayoutManager linearLayoutManager;
    ReservationRecyclerAdapter reservationRecyclerAdapter;
    public static String sCurrentDayOfWeek = "월";
    public static String sCurrentRoom = "101호";
    ArrayList<Boolean> data;
    String[] roomNumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        spnRoom = findViewById(R.id.spnRoom);
        rvRoom = findViewById(R.id.rvRoom);

        roomNumber = getResources().getStringArray(R.array.roomNumber);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, roomNumber);
        spnRoom.setAdapter(adapter);
        spnRoom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectRoom(adapterView, view, i, l);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        data = new ArrayList<>();
        data.add(true);
        data.add(true);
        data.add(true);
        data.add(false);
        data.add(true);
        data.add(false);
        data.add(true);
        data.add(true);
        reservationRecyclerAdapter = new ReservationRecyclerAdapter(data, this);
        rvRoom.setAdapter(reservationRecyclerAdapter);
        linearLayoutManager = new LinearLayoutManager(this);
        rvRoom.setLayoutManager(linearLayoutManager);
    }

    public void selectDayOfWeek(View view) {
        Button btn = (Button)view;

        sCurrentDayOfWeek = btn.getText().toString();
        clearButtons();
        btn.setEnabled(false);

        refreshTimes();
    }

    public void selectRoom(AdapterView<?> adapterView, View view, int i, long l) {
        sCurrentRoom = roomNumber[i];
        Toast.makeText(this, sCurrentRoom, Toast.LENGTH_SHORT).show();

        selectDayOfWeek(findViewById(R.id.btnMonday));
    }

    private void refreshTimes() {
        data = new ArrayList<>();
        data.add(true);
        data.add(true);
        data.add(true);
        data.add(false);
        data.add(true);
        data.add(false);
        data.add(true);
        data.add(true);
        //파이어베이스에서 currentRoom과 currentDayOfWeek를 토대로데이터 불러와서 data.add(); 해줘야함

        reservationRecyclerAdapter.notifyDataSetChanged();
    }

    private void clearButtons() {
        (findViewById(R.id.btnMonday)).setEnabled(true);
        (findViewById(R.id.btnTuesday)).setEnabled(true);
        (findViewById(R.id.btnWednesday)).setEnabled(true);
        (findViewById(R.id.btnThursday)).setEnabled(true);
        (findViewById(R.id.btnFriday)).setEnabled(true);
    }
}
