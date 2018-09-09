package com.example.edu.RecyclerAdpater;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.edu.R;
import com.example.edu.model.ChatModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ChatModel> chatModels = new ArrayList<>(); //채팅에 대한 정보를 가지는
    private String uid;

    public ChatRecyclerAdapter() {

        //데이터 받아오는 준비 코드들
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference().child("chatrooms").orderByChild("users"+uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chatModels.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    chatModels.add(item.getValue(ChatModel.class));
                }
                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { //준비과 완료 됐으니 보여주면 된다. 보여주기 코드.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat,parent,false);

        return new CustomViewHolder(view); //view만 하면 오류난다, 뷰홀더 사용 -> 메모리 절약 -> 재사용 목적
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return chatModels.size();
    }

    private class CustomViewHolder extends RecyclerView.ViewHolder {
        public CustomViewHolder(View view) {
            super(view);
        }
    }
}
