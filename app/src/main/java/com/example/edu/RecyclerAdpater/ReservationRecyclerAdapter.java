package com.example.edu.RecyclerAdpater;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.edu.MainActivity;
import com.example.edu.R;
import com.example.edu.ReservationActivity;
import com.example.edu.model.StudyRoomModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReservationRecyclerAdapter extends RecyclerView.Adapter<ReservationRecyclerAdapter.ViewHolder> {
//    ArrayList<Boolean> data;
    Context context;
    String sCurrentRoom, sCurrentDayOfWeek;
    List<StudyRoomModel.Day> studyRoomModels = new ArrayList<>();
    private static int i,j,k=0;

    //초기화
    public ReservationRecyclerAdapter(Context context, String sCurrentRoom, String sCurrentDayOfWeek) {
//        this.data = data;
        this.context = context;
        if (sCurrentRoom.equals("101호")) {
            this.sCurrentRoom = "No101";
            i = 0;
        } else if (sCurrentRoom.equals("102호")) {
            this.sCurrentRoom = "No102";
            i = 1;
        } else if (sCurrentRoom.equals("103호")) {
            this.sCurrentRoom = "No103";
            i = 2;
        }
        if (sCurrentDayOfWeek.equals("월")) {
            this.sCurrentDayOfWeek = "Monday";
            j = 0;
        } else if (sCurrentDayOfWeek.equals("화")) {
            this.sCurrentDayOfWeek = "Tuesday";
            j = 1;
        } else if (sCurrentDayOfWeek.equals("수")) {
            this.sCurrentDayOfWeek = "Wednesday";
            j = 2;
        } else if (sCurrentDayOfWeek.equals("목")) {
            this.sCurrentDayOfWeek = "Thursday";
            j = 3;
        } else if (sCurrentDayOfWeek.equals("금")) {
            this.sCurrentDayOfWeek = "Friday";
            j = 4;
        }

        FirebaseDatabase.getInstance().getReference()
                .child("studyroom").child(this.sCurrentRoom).child(this.sCurrentDayOfWeek)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        studyRoomModels.clear();
                        for (DataSnapshot item : dataSnapshot.getChildren()) {
                            studyRoomModels.add(item.getValue(StudyRoomModel.Day.class));
                        }
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    //뷰홀더 생성후 리턴
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_room, parent, false);

        return new ViewHolder(view);
    }

    //뷰의 내용
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tvRoom.setText((position + 9) + " : 00 ~ " + (position + 10) + " : 00");

        if (studyRoomModels.get(position).reservation) {
            holder.tvRoom.setTextColor(Color.GRAY);
            holder.tvRoom.setEnabled(false);
        } else {
            holder.tvRoom.setTextColor(Color.BLACK);
            holder.tvRoom.setEnabled(true);
        }

        final String tvRoom = holder.tvRoom.getText().toString();
        holder.tvRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogInterface.OnClickListener reservationListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //예약 실행
                        //요일은 dayOfWeek에서, 시간은 position으로 컨트롤.
                        Toast.makeText(context, ReservationActivity.sCurrentRoom + " " + ReservationActivity.sCurrentDayOfWeek + "요일 " + tvRoom + " " + " 예약 완료", Toast.LENGTH_SHORT).show();
                        String uid;
                        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                        StudyRoomModel.Day studyRoomModel = new StudyRoomModel.Day();
                        studyRoomModel.reservation = true;
                        studyRoomModel.time = String.valueOf(position+9);
                        studyRoomModel.uid = uid;
//                        FirebaseDatabase.getInstance().getReference().child("studyroom").child(sCurrentRoom)
//                                .child(sCurrentDayOfWeek).child(String.valueOf(MainActivity.key[i][j][position])).setValue(studyRoomModel);

//                        Map<String, Object> childUpdate = new HashMap<>();
//                        childUpdate.put("/studyroom"+"/"+sCurrentRoom+"/"+sCurrentDayOfWeek+"/"+MainActivity.key[i][j][position]+"/"+"reservation", true);
//                        childUpdate.put("/studyroom"+"/"+sCurrentRoom+"/"+sCurrentDayOfWeek+"/"+MainActivity.key[i][j][position]+"/"+"uid", uid);
//                        FirebaseDatabase.getInstance().getReference().updateChildren(childUpdate);
                        Log.e("test",String.valueOf(MainActivity.key[i][j][k])+"와 / "+String.valueOf(MainActivity.key[i][j][position]));


//                        StudyRoomModel.Day day = new StudyRoomModel.Day();
//                        day.reservation = true;
//                        day.uid = uid;
//                        FirebaseDatabase.getInstance().getReference().child("studyroom").child(sCurrentRoom)
//                                .child(sCurrentDayOfWeek).updateChildren(childUpdate);

                    }
                };

                new AlertDialog.Builder(context)
                        .setTitle(ReservationActivity.sCurrentRoom + " " + ReservationActivity.sCurrentDayOfWeek + "요일 " + tvRoom + " " + " 예약 하시겠습니까?")
                        .setPositiveButton("예약", reservationListener)
                        .setNegativeButton("취소", null)
                        .show();
            }
        });
    }

    //생성할 수
    @Override
    public int getItemCount() {
        return studyRoomModels.size();
    }

    //뷰홀더
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvRoom;

        public ViewHolder(View itemView) {
            super(itemView);
            tvRoom = itemView.findViewById(R.id.tvRoom);
        }
    }
}
