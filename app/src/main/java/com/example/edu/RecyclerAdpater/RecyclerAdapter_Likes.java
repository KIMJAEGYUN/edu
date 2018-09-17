package com.example.edu.RecyclerAdpater;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.edu.R;
import com.example.edu.model.BoardModel;
import com.example.edu.model.LikesModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter_Likes extends RecyclerView.Adapter<RecyclerAdapter_Likes.ViewHolder> {

    Context context;

    ArrayList<BoardModel> boardModels = new ArrayList<>();
    ArrayList<LikesModel> likesModels = new ArrayList<>();
    String uid;
    List<String> uidList = new ArrayList<>();


    public RecyclerAdapter_Likes(final Context context) {

        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        this.context = context;

        FirebaseDatabase.getInstance().getReference().child("group").orderByChild("users/" + uid).equalTo(true).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                likesModels.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    likesModels.add(dataSnapshot1.getValue(LikesModel.class));

                }
                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public RecyclerAdapter_Likes.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_board, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter_Likes.ViewHolder holder, int position) {

        holder.tvTitle.setText(likesModels.get(position).groupName);


    }

    @Override
    public int getItemCount() {
        return likesModels.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;

        public ViewHolder(View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);

        }
    }
}
