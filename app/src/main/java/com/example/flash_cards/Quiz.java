package com.example.flash_cards;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Quiz extends AppCompatActivity {
    ListView questions_list;
    FirebaseAuth firebaseAuth;
    ArrayList<String> questions;
    ArrayAdapter<String> adapter;
    DatabaseReference root_database;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        setTitle("Quiz");

        firebaseAuth = FirebaseAuth.getInstance();
        root_database = FirebaseDatabase.getInstance().getReference().child("users");
        user = FirebaseAuth.getInstance().getCurrentUser();

        questions_list = findViewById(R.id.questions_list);
        questions = new ArrayList<>();
        adapter = new ArrayAdapter<String>( this, android.R.layout.simple_list_item_1, questions);
        questions_list.setAdapter(adapter);

        root_database.child(user.getEmail().substring(0, user.getEmail().indexOf("@"))).child("subjects").child("math").addValueEventListener(new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                questions.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    questions.add(snapshot.getValue().toString());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}