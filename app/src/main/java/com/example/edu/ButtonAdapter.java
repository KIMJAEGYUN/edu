package com.example.edu;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

public class ButtonAdapter extends BaseAdapter {
    private Context mContext;

    public ButtonAdapter(Context context){
        mContext = context;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Button btn;
        if (convertView == null){
            btn = new Button(mContext);
            btn.setLayoutParams(new ViewGroup.LayoutParams(85,85));
            btn.setPadding(8,8,8,8);
        } else {
            btn = (Button) convertView;
        }
        return btn;
    }
}
