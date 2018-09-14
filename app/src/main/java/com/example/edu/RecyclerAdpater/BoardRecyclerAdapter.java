package com.example.edu.RecyclerAdpater;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.edu.PopUp;
import com.example.edu.R;
import com.example.edu.model.BoardModel;
import com.example.edu.model.PopModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sackcentury.shinebuttonlib.ShineButton;

import java.util.ArrayList;
import java.util.List;

public class BoardRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<BoardModel> boardModels;

    public BoardRecyclerAdapter(Context context) {
        this.context = context;
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

    // 뷰 홀더를 생성하고 뷰를 붙여주는 부분이다
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_board, parent, false);
        // parent : 각각의 아이템을 위해서 정의한 xml 레이아웃의 최상위 레이아웃 , 어떤 것 (item_board.xml)으로 뷰를 만들고 그것을 뷰홀더에 넣어줄지 결정.
        return new CustomViewHolder(view); // 각각의 아이템을 위한 뷰를 담고 있는 뷰홀더 객체를 만들어서 리턴
    }

    // 재활용 되는 뷰가 호출하여 실행되는 메소드, 뷰 홀더를 전달하고 어뎁터는 position의 데이터를 결합시킨다
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ((CustomViewHolder) holder).tvTitle.setText(boardModels.get(position).groupName);
        ((CustomViewHolder) holder).tvShortTitle.setText(boardModels.get(position).groupShortTitle);
        ((CustomViewHolder) holder).tvLimit.setText(Integer.toString(boardModels.get(position).groupLimit));
        ((CustomViewHolder) holder).tvCurrentMembers.setText(Integer.toString(boardModels.get(position).groupCurrentMembers));



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), PopUp.class);

                PopModel popModel = new PopModel();
                popModel.setUid((boardModels.get(position).uid));
                popModel.setGroupName(boardModels.get(position).groupName);
                popModel.setGroupShortTitle(boardModels.get(position).groupShortTitle);
                popModel.setGroupStyle(boardModels.get(position).groupStyle);
                popModel.setGroupTopic(boardModels.get(position).groupTopic);
                popModel.setGroupLimit(boardModels.get(position).groupLimit);
                popModel.setGroupExplain(boardModels.get(position).groupExplain);
                popModel.setGroupCurrentMemebers(boardModels.get(position).groupCurrentMembers);
                intent.putExtra("popModel", popModel);

                ActivityOptions activityOptions = null;
                activityOptions = ActivityOptions.makeCustomAnimation(view.getContext(), R.anim.fromright, R.anim.toleft);
                view.getContext().startActivity(intent, activityOptions.toBundle());
//                // api 몇 부터 되는지 물어보는 오류 발생 시 아래 코드로 진행하면 된다
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                    activityOptions = ActivityOptions.makeCustomAnimation(view.getContext(), R.anim.fromright, R.anim.toleft);
//                    view.getContext().startActivity(intent, activityOptions.toBundle());
//                }

            }
        });


        // 관심목록(별모양)을 클릭하였을 때 발생되는 이벤트로 추후 구현할 예정이다
        ((CustomViewHolder) holder).shineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BoardModel boardModel = new BoardModel();
                boardModel.favorites.put(boardModels.get(position).uid, true);


//                FirebaseDatabase.getInstance().getReference().child("users")
//                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("userFavorites").push().setValue(boardModel.favorites)
////                FirebaseDatabase.getInstance().getReference().child("users")
////                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("userFavorites")
////                        .setValue(boardModel.favorites)
//                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        //TODO: Toast 이렇게 써도 되나 몰라1
//                        Toast.makeText(context, "관심목록 추가 성공!", Toast.LENGTH_SHORT).show();
//                    }
//                });





            }
        });
    }

    // 데이터 개수를 반환한다
    @Override
    public int getItemCount() {
        return boardModels.size();
    }

    // ViewHolder 커스텀
    private class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView tvTitle, tvShortTitle, tvCurrentMembers, tvLimit;
        ShineButton shineButton;

        // 재활용 View에 대한 모든 서브 뷰 보유
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
