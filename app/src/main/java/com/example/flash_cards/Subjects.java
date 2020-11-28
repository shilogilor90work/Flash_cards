package com.example.flash_cards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class Subjects extends AppCompatActivity {

    Button definitions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects);
        setTitle("Subjects");

        definitions = findViewById(R.id.Definitions2);

        definitions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Subjects.this,Definitions.class);
                startActivity(i);
            }
        });
    }


}