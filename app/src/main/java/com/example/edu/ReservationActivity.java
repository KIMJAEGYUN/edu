package com.example.edu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

public class ReservationActivity extends AppCompatActivity {

    Spinner spnRoom;
    GridView gridTime;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        spnRoom = findViewById(R.id.spnRoom);
        gridTime = findViewById(R.id.gridTime);

        final String[] roomNumber = getResources().getStringArray(R.array.roomNumber);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, roomNumber);
        spnRoom.setAdapter(adapter);

        gridTime.setAdapter(new ButtonAdapter(this));

        gridTime.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ReservationActivity.this,""+position,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
