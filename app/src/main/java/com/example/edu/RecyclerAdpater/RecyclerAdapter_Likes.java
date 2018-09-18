package com.example.edu.RecyclerAdpater;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.edu.R;
import com.example.edu.model.BoardModel;
import com.example.edu.model.LikesModel;
import com.example.edu.model.PopModel;
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
    private String uid;
    List<LikesModel> likesModels = new ArrayList<>();


    public RecyclerAdapter_Likes(final Context context) {

        this.context = context;

        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference().child("group").orderByChild("join/" + uid).equalTo(true)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        likesModels.clear();
                        for (DataSnapshot item : dataSnapshot.getChildren()) {
                            likesModels.add(item.getValue(LikesModel.class));
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

        ImageView iv;
        TextView tvTitle, tvShortTitle, tvCurrentMembers, tvLimit;


        public ViewHolder(View itemView) {
            super(itemView);

            iv = itemView.findViewById(R.id.iv);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvShortTitle = itemView.findViewById(R.id.tvShortTitle);
            tvCurrentMembers = itemView.findViewById(R.id.tvCurrentMembers);
            tvLimit = itemView.findViewById(R.id.tvLimit);

        }
    }
}
