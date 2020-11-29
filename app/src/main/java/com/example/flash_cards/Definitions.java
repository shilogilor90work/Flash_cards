package com.example.flash_cards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Definitions extends AppCompatActivity {

    Button test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_definitions);
        setTitle("Definitions");

        test = findViewById(R.id.Test2);

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Definitions.this,Quiz.class);
                startActivity(i);
            }
        });
    }
}