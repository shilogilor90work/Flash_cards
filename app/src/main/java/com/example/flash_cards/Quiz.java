package com.example.flash_cards;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Quiz extends AppCompatActivity {
    private ListView questions_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        setTitle("Quiz");

        questions_list = findViewById(R.id.questions_list);
        ArrayList<String> subjects_list = new ArrayList<>();
        ArrayAdapter adapter = new ArrayAdapter<String>( this, android.R.layout.simple_list_item_1, subjects_list);
        questions_list.setAdapter(adapter);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("subjects");
        ref.addValueEventListener(new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                subjects_list.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    subjects_list.add(snapshot.getValue().toString());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}