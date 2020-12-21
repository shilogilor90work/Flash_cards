package com.example.flash_cards;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
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

public class TeachersStudents extends AppCompatActivity {
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
        setContentView(R.layout.activity_teachers_students);

        BottomNavigationView bnv = findViewById(R.id.BottomNavigationView);
        bnv.setSelectedItemId(R.id.Subjects_item);

        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item){
                Fragment selectedFragment = null;
                switch (item.getItemId()){
                    case R.id.Friends_item:
                        return true;

                    case R.id.Subjects_item:
                        startActivity(new Intent(getApplicationContext(),TeacherSubjects.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.Contact_item:
                        Intent i = new Intent(getApplicationContext(),Contact.class);
                        i.putExtra("role", "teacher");
                        startActivity(i);
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });


        students_list = (ListView) findViewById(R.id.studnets_list);
        firebaseAuth = FirebaseAuth.getInstance();
        search_words = (EditText) findViewById(R.id.students_search);
        btn_search = (Button) findViewById(R.id.students_btn);
        students = new ArrayList<String>();
        root_database = FirebaseDatabase.getInstance().getReference().child("users");
        user = FirebaseAuth.getInstance().getCurrentUser();
        adapter = new MyAdapter(this, students);

        root_database.child(user.getEmail().substring(0, user.getEmail().indexOf("@"))).child("friends").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Result will be holded Here
                        for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                            students.add(String.valueOf(dsp.getValue()));
                        }
                        students_list.setAdapter(adapter);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
    }
}