package com.example.edu;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


public class MainFragment_1 extends Fragment {

    private View h;
    Button btnFavorites;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main_fragment_1,container,false);
        RecyclerView recyclerView = view.findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
        recyclerView.setAdapter(new RecyclerAdpater());

        //h = getActivity().getLayoutInflater().inflate(R.layout.item, null, false);

        //btnFavorites = (Button)h.findViewById(R.id.btnFavorites);

        /*btnFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "dgddfgdg", Toast.LENGTH_SHORT).show();
            }
        });*/

        return view;
    }


}
