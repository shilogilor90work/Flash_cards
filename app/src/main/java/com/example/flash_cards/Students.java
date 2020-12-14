package com.example.flash_cards;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Students extends AppCompatActivity {
    ListView students_list;
    EditText search_words;
    Button btn_search;
    ArrayList<String> students;
    MyAdapter adapter;
    FirebaseAuth firebaseAuth;
    DatabaseReference root_database;
    FirebaseUser user;
    String subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);
        students_list = (ListView) findViewById(R.id.students_list);
        firebaseAuth = FirebaseAuth.getInstance();
        students = new ArrayList<String>();
        root_database = FirebaseDatabase.getInstance().getReference().child("users");
        user = FirebaseAuth.getInstance().getCurrentUser();
        subject=getIntent().getStringExtra("subject");

        root_database.child(user.getEmail().substring(0, user.getEmail().indexOf("@"))).child("subjects").child(subject).child("students").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                            students.add(dsp.getKey() + "|split|" + dsp.getValue().toString());
                        }
                        students_list.setAdapter(adapter);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
        adapter = new MyAdapter(this, students);
    }

}