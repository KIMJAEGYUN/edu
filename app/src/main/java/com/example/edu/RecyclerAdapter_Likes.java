package com.example.edu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.edu.model.groupTest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RecyclerAdapter_Likes extends RecyclerView.Adapter<RecyclerAdapter_Likes.ViewHolder> {

    Context context;

    ArrayList<groupTest> groupTest;

    public RecyclerAdapter_Likes(Context context) {

        this.context = context;

        groupTest = new ArrayList<groupTest>();
        FirebaseDatabase.getInstance().getReference().child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                groupTest.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    groupTest.add(snapshot.getValue(groupTest.class));
                }
                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public RecyclerAdapter_Likes.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter_Likes.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return groupTest.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv;

        public ViewHolder(View itemView) {
            super(itemView);

            tv = (TextView) itemView.findViewById(R.id.tv);

        }
    }
}
