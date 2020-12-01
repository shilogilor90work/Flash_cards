package com.example.flash_cards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


public class Subjects extends AppCompatActivity {
    ListView subject_list;
    ArrayList<String>subjects;
    ArrayAdapter<String> adapter;
    ArrayList<String> Subjectlist;
    EditText search_words;
    Button btn_search;

    Button definitions;
    Button friends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects);
        setTitle("Subjects");
        subject_list=(ListView)findViewById(R.id.subject_list);
        subjects = new ArrayList<>();
        adapter = new ArrayAdapter<String>(Subjects.this, android.R.layout.simple_list_item_1, subjects);
        subject_list.setAdapter(adapter);
        search_words = (EditText) findViewById(R.id.subjects_search);
        btn_search = (Button) findViewById(R.id.subjects_btn);
        click_button();

        definitions = findViewById(R.id.Definitions2);

        definitions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Subjects.this,Definitions.class);
                startActivity(i);
            }
        });
        friends = findViewById(R.id.go_to_friends);

        friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Subjects.this,Friends.class);
                startActivity(i);
            }
        });


    }

    {

    }
    public void click_button() {
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subjects.add(search_words.getText().toString());
                adapter.notifyDataSetChanged();
            }
        });
    }


}