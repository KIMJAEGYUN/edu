package com.example.edu;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.edu.databinding.ActivityMainFragment1Binding;

import java.util.ArrayList;


public class MainFragment_1 extends Fragment {

    RecyclerView.LayoutManager manager;
    Adapter adapter;
    ArrayList<Items> data;
    ActivityMainFragment1Binding b;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        b = DataBindingUtil.inflate(inflater, R.layout.activity_main_fragment_1, container, false);

        b.recycle.setHasFixedSize(true);

        manager = new LinearLayoutManager(getActivity());
        b.recycle.setLayoutManager(manager);

        data = new ArrayList<Items>();
        data.add(new Items(R.drawable.ic_supervisor_account_black_24dp, "토익 스터디"));
        data.add(new Items(R.drawable.ic_supervisor_account_black_24dp, "볼링"));
        data.add(new Items(R.drawable.ic_supervisor_account_black_24dp, "자격증 스터디"));
        data.add(new Items(R.drawable.ic_supervisor_account_black_24dp, "탁구"));
        data.add(new Items(R.drawable.ic_supervisor_account_black_24dp, "농구"));
        data.add(new Items(R.drawable.ic_supervisor_account_black_24dp, "자바 스터디"));
        data.add(new Items(R.drawable.ic_supervisor_account_black_24dp, "자바 스터디"));
        data.add(new Items(R.drawable.ic_supervisor_account_black_24dp, "자바 스터디"));


        adapter = new Adapter(data);

        b.recycle.setAdapter(adapter);

        b.recycle.addOnItemTouchListener(

                new RecyclerItemClickListener(getActivity(), b.recycle, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Toast.makeText(getActivity(), position + "번 째 아이템 클릭", Toast.LENGTH_SHORT).show();
                        Intent POP = new Intent(getActivity(), PopUp.class);
                        startActivity(POP);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        Toast.makeText(getActivity(), position + "번 째 아이템 롱 클릭", Toast.LENGTH_SHORT).show();
                    }
                }));


        return b.getRoot();

    }

    public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> { // 어댑터를 내부클래스로 빼고 싶은데 안됨...
        ArrayList<Items> data;

        public Adapter(ArrayList<Items> data) {
            this.data = data;
        }


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);

            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            MyViewHolder viewHolder = (MyViewHolder) holder;
            viewHolder.iv.setImageResource(data.get(position).getId());
            viewHolder.tv.setText(data.get(position).getName());
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {


            TextView tv;
            ImageView iv;

            public MyViewHolder(View itemView) {
                super(itemView);

                iv = itemView.findViewById(R.id.iv);
                tv = itemView.findViewById(R.id.tv);

            }
        }
    }


}
