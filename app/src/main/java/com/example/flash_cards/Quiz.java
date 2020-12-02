package com.example.flash_cards;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Quiz extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    DatabaseReference root_database;
    FirebaseUser user;
    ArrayList<String> answers;
    //ArrayAdapter<String> adapter;

    boolean once;
    String question;
    TextView textViewToChange;
    Button choice1;
    Button choice2;
    Button choice3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        setTitle("Quiz");

        firebaseAuth = FirebaseAuth.getInstance();
        root_database = FirebaseDatabase.getInstance().getReference().child("users");
        user = FirebaseAuth.getInstance().getCurrentUser();

//        answers_list =(ListView) findViewById(R.id.answers_list);
        answers = new ArrayList<>();
//        adapter = new ArrayAdapter<String>( this, android.R.layout.simple_list_item_1, answers);
//        answers_list.setAdapter(adapter);
//
        textViewToChange = (TextView) findViewById(R.id.question_id);
        choice1 = (Button)findViewById(R.id.choice1);
        choice2 = (Button)findViewById(R.id.choice2);
        choice3 = (Button)findViewById(R.id.choice3);

        root_database.child(user.getEmail().substring(0, user.getEmail().indexOf("@"))).child("subjects").child("math").addValueEventListener(new ValueEventListener(){
               @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                answers.clear();
                once = true;
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    if(once) {
                        question = snapshot.getKey()+" ?";
                        Log.d("unique!!!!!!!!",question);
                        textViewToChange.setText(question);
                        once = false;
                    }
                    answers.add(snapshot.getValue().toString());
                }
                choice1.setText(answers.get(0));
                choice2.setText(answers.get(1));
                choice3.setText(answers.get(2));
                   //adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("unique","in-onCancelled");

            }
        });

    }
}