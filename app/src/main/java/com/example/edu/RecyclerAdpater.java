package com.example.edu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerAdpater extends RecyclerView.Adapter<RecyclerAdpater.ViewHolder> {

    Context context; // 데이터만 보관, 리사이클러뷰는 뷰를 담고있음

    ArrayList<Items> items = new ArrayList<Items>(); // 각각의 데이터 보관

    public RecyclerAdpater(Context context) {

        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { // 뷰홀더가 만들어지는 시점에 호출되는 메소드
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.item, parent, false); // parent : 각각의 아이템을 위해서 정의한 xml 레이아웃의 최상위 레이아웃 , 어떤 것 (item.xml)으로 뷰를 만들고 그것을 뷰홀더에 넣어줄지 결정.

        return new ViewHolder(itemView); // >> 각각의 아이템을 위한 뷰를 담고 있는 뷰홀더 객체를 만들어서 리턴
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) { // 데이터와 뷰가 서로 결합되는 경우

        Items item = items.get(position); // "items 안의 데이터 중에서 몇 번째 데이터가 보여야한다."
        holder.setItem(item); // item 객체를 홀더에 넣어줌으로써 어떤 데이터인지 식별
    }

    public void addItem(Items item) { // 어댑터에 아이템을 추가하는 경우
        items.add(item);
    }

    public void addItems(ArrayList<Items> items) { // //ArrayList라고 하는 Itmes를 그대로 설정하고 싶은 경우
        this.items = items;
    }

    public Items getItem(int position) {
        return items.get(position);
    }


    @Override
    public int getItemCount() {
        return items.size(); // 아이템 몇갠지.
    }

    // 어댑터안에 뷰홀더를 정의하기 위함 // 뷰홀더 : 각각의 아이템을 보여주기 위한 뷰를 담고 있는 객체

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iv;
        TextView tv, tv2, tv3;


        public ViewHolder(View itemView) { // 뷰홀더 생성자 , 뷰홀더는 각각의 아이템을 위한 뷰를 담고 있다
            super(itemView); // 그러므로 뷰홀더는 Items.xml 의 뷰를 전달해주는 것, Items의 컨텐츠를 이용해 데이터 설정등등 진행


            // 뷰와 실제 데이터 ( java 내의 할당된 데이터 ) 매칭 과정
            tv = (TextView) itemView.findViewById(R.id.tv);
            tv2 = (TextView) itemView.findViewById(R.id.tv2);
            tv3 = (TextView) itemView.findViewById(R.id.tv3);
            iv = (ImageView) itemView.findViewById(R.id.iv);

        }

        public void setItem(Items item) {
            tv.setText(item.getTitle());
            tv2.setText(item.getMembers());
            tv3.setText(item.getLimitMemb());
            iv.setImageResource(item.getId());
        }

    }
}
