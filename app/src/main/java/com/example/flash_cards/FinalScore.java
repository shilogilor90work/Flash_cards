package com.example.flash_cards;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FinalScore extends AppCompatActivity {
    TextView finalScore;
    int final_sc;
    int num_que;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_score);
        setTitle("Final Score");

        BottomNavigationView bnv = findViewById(R.id.BottomNavigationView);
        bnv.setSelectedItemId(R.id.Subjects_item);

        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item){
                Fragment selectedFragment = null;
                switch (item.getItemId()){
                    case R.id.Friends_item:
                        startActivity(new Intent(getApplicationContext(),Friends.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.Subjects_item:
                        startActivity(new Intent(getApplicationContext(),Subjects.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.Contact_item:
                        startActivity(new Intent(getApplicationContext(),Contact.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        finalScore = (TextView) findViewById(R.id.final_score_id);
        final_sc = getIntent().getIntExtra("int_final_score",0);
        num_que = getIntent().getIntExtra("int_num_questions",0);
        finalScore.setText(final_sc+" from "+num_que +" questions");

    }
}
