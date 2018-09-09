package com.example.edu.RecyclerAdpater;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.edu.PopUp;
import com.example.edu.R;
import com.example.edu.model.BoardModel;
import com.example.edu.model.UserModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sackcentury.shinebuttonlib.ShineButton;

import java.util.ArrayList;
import java.util.List;

public class BoardRecyclerAdpater extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<BoardModel> boardModels;


    public BoardRecyclerAdpater() {
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_board, parent, false);
        // parent : 각각의 아이템을 위해서 정의한 xml 레이아웃의 최상위 레이아웃 , 어떤 것 (item_board_board.xml)으로 뷰를 만들고 그것을 뷰홀더에 넣어줄지 결정.
        return new CustomViewHolder(view); // >> 각각의 아이템을 위한 뷰를 담고 있는 뷰홀더 객체를 만들어서 리턴
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) { // 데이터와 뷰가 서로 결합되는 경우
        ((CustomViewHolder) holder).tvTitle.setText(boardModels.get(position).groupName);
        ((CustomViewHolder) holder).tvShortTitle.setText(boardModels.get(position).groupShortTitle);
        //((CustomViewHolder) holder).tvCurrentMembers.setText(Integer.toString(boardModels.get(position).groupCurrentMemebers)); 그룹에 몇명 들어왔는지
        ((CustomViewHolder) holder).tvLimit.setText(Integer.toString(boardModels.get(position).groupLimit));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), PopUp.class);
                intent.putExtra("destinationUid", boardModels.get(position).uid);
                Log.e("kkkk", boardModels.get(position).uid); //test중입니다
                Log.e("kkkk", "test"); //test중입니다

                ActivityOptions activityOptions = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    activityOptions = ActivityOptions.makeCustomAnimation(view.getContext(), R.anim.fromright, R.anim.toleft);
                    view.getContext().startActivity(intent, activityOptions.toBundle());
                }
            }
        });
        ((CustomViewHolder) holder).shineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //BoardModel BoardModel = new BoardModel();
                UserModel userModel = new UserModel();


                //본인 uid를 가져와서 userFavorites에 group uid 저장

               Toast.makeText(view.getContext(), " "+ boardModels.get(position).uid, Toast.LENGTH_LONG).show(); //test

               //userModel.userFavorites = boardModels.get(position).uid;

            }
        });
    }

    @Override
    public int getItemCount() {
        return boardModels.size(); // 아이템 몇갠지.
    }

    private class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView tvTitle, tvShortTitle, tvCurrentMembers, tvLimit;
        ShineButton shineButton;

        public CustomViewHolder(View view) { // 뷰홀더 생성자 , 뷰홀더는 각각의 아이템을 위한 뷰를 담고 있다
            super(view); // 그러므로 뷰홀더는 Items.xml 의 뷰를 전달해주는 것, Items의 컨텐츠를 이용해 데이터 설정등등 진행
            // 뷰와 실제 데이터 ( java 내의 할당된 데이터 ) 매칭 과정
            iv = view.findViewById(R.id.iv);
            tvTitle = view.findViewById(R.id.tvTitle);
            tvShortTitle = view.findViewById(R.id.tvShortTitle);
            tvCurrentMembers = view.findViewById(R.id.tvCurrentMembers);
            tvLimit = view.findViewById(R.id.tvLimit);
            shineButton = view.findViewById(R.id.btnFavorites);
        }
    }
}
