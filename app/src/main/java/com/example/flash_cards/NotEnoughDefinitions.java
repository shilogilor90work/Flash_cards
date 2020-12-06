package com.example.flash_cards;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class NotEnoughDefinitions extends AppCompatActivity {
    Button backToSubjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_enough_definitions);
        setTitle("Sorry for the inconvenience");

        backToSubjects = (Button)findViewById(R.id.backToSubjects_id);

        backToSubjects.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(NotEnoughDefinitions.this,Subjects.class);
                startActivity(i);
            }
        });
    }
}
