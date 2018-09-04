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
    ActivityMainFragment1Binding b;
    TextView test;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        b = DataBindingUtil.inflate(inflater, R.layout.activity_main_fragment_1, container, false);

        //b.recycle.setHasFixedSize(true);

        manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false); // 세번째 파라미터 = 방향 설정
        b.recycle.setLayoutManager(manager); // 리사이클러뷰가 세로 방향으로 설정됨.

        // item.xml >> 리사이클러뷰에 담을 아이템에 대한 XML 과 아이템 데이터를 담을 Items.java class 작성.

        RecyclerAdpater adapter = new RecyclerAdpater(getActivity());

        b.recycle.setAdapter(adapter);
        //b.test.setText(adapter.items.size());





        b.recycle.addOnItemTouchListener(

                new RecyclerItemClickListener(getActivity(), b.recycle, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
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


}
