package com.example.edu.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.edu.R;
import com.example.edu.RecyclerAdpater.ChatRecyclerAdapter;

public class ChatFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_chat,container,false);

        RecyclerView recyclerView = view.findViewById(R.id.chat_recyclerview);
        recyclerView.setAdapter(new ChatRecyclerAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(inflater.getContext())); //inflater.getContext() 대신 this를 넣으면 오류남

        return view;
    }
}
