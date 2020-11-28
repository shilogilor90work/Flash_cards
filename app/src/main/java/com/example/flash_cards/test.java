package com.example.flash_cards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class test extends AppCompatActivity {
    EditText subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        setTitle("Test");
//        subject = (EditText) findViewById(R.id.id2);
//        setContentView(R.layout.activity_test);
//        String value = getIntent().getStringExtra("subject");
//        subject.setText("helloSinger");
    }
}