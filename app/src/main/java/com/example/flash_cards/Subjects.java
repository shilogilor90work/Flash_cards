package com.example.flash_cards;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Subjects extends AppCompatActivity {

    Button add_btn;
    EditText subjects_txt;
    ListView subject_list;
    FirebaseAuth firebaseAuth;
    DatabaseReference root_database;
    ArrayList<String> subjectArrayList;
    FirebaseUser user;
    SubjectsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects);
        setTitle("Subjects");

        BottomNavigationView bnv = findViewById(R.id.BottomNavigationView);
        bnv.setSelectedItemId(R.id.Subjects_item);

        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item){
                Fragment selectedFragment = null;
                switch (item.getItemId()){
                    case R.id.Subjects_item:
                        return true;

                    case R.id.Friends_item:
                        startActivity(new Intent(getApplicationContext(),Friends.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.Contact_item:
                        return true;
                }
                return false;
            }
        });

        add_btn = (Button) findViewById(R.id.subjects_btn);
        subjects_txt = (EditText) findViewById(R.id.subjects);
        subject_list = (ListView) findViewById(R.id.subject_list);
        firebaseAuth = FirebaseAuth.getInstance();
        subjectArrayList = new ArrayList<String>();
        root_database = FirebaseDatabase.getInstance().getReference().child("users");
        user = FirebaseAuth.getInstance().getCurrentUser();
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                root_database.child(user.getEmail().substring(0, user.getEmail().indexOf("@"))).child("subjects").child(subjects_txt.getText().toString()).setValue("stam");
                Toast.makeText(Subjects.this, "added data", Toast.LENGTH_SHORT).show();
                subjectArrayList.add(subjects_txt.getText().toString());
                adapter.notifyDataSetChanged();
            }
        });
        root_database.child(user.getEmail().substring(0, user.getEmail().indexOf("@"))).child("subjects").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                            subjectArrayList.add(dsp.getKey());
                        }
                        subject_list.setAdapter(adapter);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
        adapter = new SubjectsAdapter(this, subjectArrayList);
    }
}