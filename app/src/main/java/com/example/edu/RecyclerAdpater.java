package com.example.edu;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.edu.model.BoardModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdpater extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<BoardModel> boardModels;

    public RecyclerAdpater() {
        boardModels = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("group").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boardModels.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    boardModels.add(snapshot.getValue(BoardModel.class));
                }
                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { // 뷰홀더가 만들어지는 시점에 호출되는 메소드
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        // parent : 각각의 아이템을 위해서 정의한 xml 레이아웃의 최상위 레이아웃 , 어떤 것 (item.xml)으로 뷰를 만들고 그것을 뷰홀더에 넣어줄지 결정.
        return new CustomViewHolder(view); // >> 각각의 아이템을 위한 뷰를 담고 있는 뷰홀더 객체를 만들어서 리턴
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) { // 데이터와 뷰가 서로 결합되는 경우
        ((CustomViewHolder)holder).tv.setText(boardModels.get(position).groupName);
    }

    @Override
    public int getItemCount() {
        return boardModels.size(); // 아이템 몇갠지.
    }

    private class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView tv, tv2, tv3;
        public CustomViewHolder(View view) { // 뷰홀더 생성자 , 뷰홀더는 각각의 아이템을 위한 뷰를 담고 있다
            super(view); // 그러므로 뷰홀더는 Items.xml 의 뷰를 전달해주는 것, Items의 컨텐츠를 이용해 데이터 설정등등 진행
            // 뷰와 실제 데이터 ( java 내의 할당된 데이터 ) 매칭 과정
            iv = view.findViewById(R.id.iv);
            tv = view.findViewById(R.id.tv);
            tv2 = view.findViewById(R.id.tv2);
            tv3 = view.findViewById(R.id.tv3);
        }
    }
}
