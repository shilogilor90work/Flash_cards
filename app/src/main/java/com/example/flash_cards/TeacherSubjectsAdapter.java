package com.example.flash_cards;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class TeacherSubjectsAdapter extends ArrayAdapter {
    DatabaseReference root_database;
    FirebaseUser user;
    ArrayList<String> records;
    public TeacherSubjectsAdapter(Context context, ArrayList<String> records) {
        super(context, 0, records);
        this.records = records;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        root_database = FirebaseDatabase.getInstance().getReference().child("users");
        user = FirebaseAuth.getInstance().getCurrentUser();
        String item = (String) getItem(position);
        if (convertView==null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.teacher_subjects_listview, null);
        }
        TextView subjects_text = (TextView) convertView.findViewById(R.id.teacher_subjects_text);
        Button subject_delete = (Button) convertView.findViewById(R.id.teacher_subject_delete);
        Button btn_definitions = (Button) convertView.findViewById(R.id.teacher_btn_definitions);
        Button teacher_move_to_students = (Button) convertView.findViewById(R.id.teacher_move_to_students);

        subjects_text.setText(item);

        subject_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference db_node = root_database.child( user.getEmail().substring(0, user.getEmail().indexOf("@"))).child("subjects").child(item);
                db_node.removeValue();
                records.remove(position);
                notifyDataSetChanged();
            }
        });

        btn_definitions.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),TeacherDefinitions.class);
                i.putExtra("subject",item);
                getContext().startActivity(i);
            }
        });
        teacher_move_to_students.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),Students.class);
                i.putExtra("subject",item);
                getContext().startActivity(i);
            }
        });
        return convertView;
    }
}
