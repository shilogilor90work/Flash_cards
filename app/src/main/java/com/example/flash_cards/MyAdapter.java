package com.example.flash_cards;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends ArrayAdapter {
    public MyAdapter(Context context, ArrayList<String> records) {
        super(context, 0, records);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String item = (String) getItem(position);
        if (convertView==null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_custom, null);
        }
        TextView list_txt = (TextView) convertView.findViewById(R.id.List_txt);
        Button list_btn = (Button) convertView.findViewById(R.id.List_btn);
        list_txt.setText(item);
        list_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list_txt.setText("change code to set data base");
//                share my subjects with a friend/
            }
        });
        return convertView;
    }
}
