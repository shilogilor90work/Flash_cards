package com.example.flash_cards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.io.*;
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
    Set<String> _subjects;
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
               if( !check_for_duplicates(subjects,search_words.getText().toString()))
               {

                   subjects.add(search_words.getText().toString());


                   adapter.notifyDataSetChanged();
               }
               else
               {
                   Toast.makeText(Subjects.this, "please re check for possible duplicate", Toast.LENGTH_SHORT).show();
               }
            }
        });
    }

    /**
     * used for checking possible duplicates for subjects
     * set has one instance of each element. we insert from list to set
     * if size is different meaning it has duplicate
     * algorithm 1 and Algorithm 1 is considered a duplicate
     * @param __subjects
     * @return true in case of non duplicate subjects
     */
    public boolean check_for_duplicates(ArrayList<String>__subjects,String str)
    {
        Set<String>s= new HashSet<String>();
        //_subjects.add(str);
        for(int i=0;i<__subjects.size();i++)
        {
         String temp=__subjects.get(i).toLowerCase();
         s.add(temp);
        }
        int temp=s.size();
        s.add(str.toLowerCase());
        if(s.size()==temp)
            return true;//duplicates exist
        return false;//non duplicate list of subjects

    }
    {

    }




}