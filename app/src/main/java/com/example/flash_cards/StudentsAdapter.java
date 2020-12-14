package com.example.flash_cards;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentsAdapter extends ArrayAdapter {
    DatabaseReference root_database;
    FirebaseUser user;
    public StudentsAdapter(Context context, ArrayList<String> records) {
        super(context, 0, records);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        root_database = FirebaseDatabase.getInstance().getReference().child("users");
        user = FirebaseAuth.getInstance().getCurrentUser();
        String item = (String) getItem(position);
        if (convertView==null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.students_listview, null);
        }
        TextView list_txt = (TextView) convertView.findViewById(R.id.students_list_txt);
        TextView score = (TextView) convertView.findViewById(R.id.Score_List_txt);
        String key = item.substring(0,item.indexOf("|split|"));
        String value = item.substring(item.indexOf("|split|") + 7 );
        list_txt.setText(key);
        score.setText(value);
        return convertView;
    }
}
