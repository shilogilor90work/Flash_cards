package com.example.flash_cards;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NotEnoughDefinitions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_enough_definitions);
        setTitle("Sorry for the inconvenience");

        String role = getIntent().getStringExtra("role");
        BottomNavigationView bnv = findViewById(R.id.BottomNavigationView);
        bnv.setSelectedItemId(R.id.Subjects_item);

        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item){
                switch (item.getItemId()){
                    case R.id.Friends_item:
                        if (role.equals("student")) {
                            startActivity(new Intent(getApplicationContext(),Friends.class));
                            overridePendingTransition(0,0);
                            return true;
                        } else {
                            startActivity(new Intent(getApplicationContext(),TeachersStudents.class));
                            overridePendingTransition(0,0);
                            return true;
                        }
                    case R.id.Subjects_item:
                        if (role.equals("student")) {
                            startActivity(new Intent(getApplicationContext(),Subjects.class));
                            overridePendingTransition(0,0);
                            return true;
                        } else {
                            startActivity(new Intent(getApplicationContext(),TeacherSubjects.class));
                            overridePendingTransition(0,0);
                            return true;
                        }
                    case R.id.Contact_item:
                        Intent i = new Intent(getApplicationContext(),TeachersStudents.class);
                        i.putExtra("role", "student");
                        startActivity(i);
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

    }
}
