package com.example.flash_cards;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class FinalScore extends AppCompatActivity {
    Button backToSubjects;
    TextView finalScore;
    int final_sc;
    int num_que;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_score);
        setTitle("Final Score");

        backToSubjects = (Button)findViewById(R.id.backToSubjects_id2);
        finalScore = (TextView) findViewById(R.id.final_score_id);
        final_sc = getIntent().getIntExtra("int_final_score",0);
        num_que = getIntent().getIntExtra("int_num_questions",0);
        finalScore.setText(final_sc+" from "+num_que +" questions");
        backToSubjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FinalScore.this,Subjects.class);
                startActivity(i);
            }
        });
    }
}
