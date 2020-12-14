package com.example.flash_cards;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TeacherDefinitions extends AppCompatActivity {

//    Button test;
    Button add_btn;
    EditText definition;
    EditText definition_value;
    ListView definition_list;
    FirebaseAuth firebaseAuth;
    DatabaseReference root_database;
    ArrayList<String> definitions;
    FirebaseUser user;
    DefinitionsAdapter adapter;
    String subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_activity_definitions);
        Log.d("hithere", "test");
        setTitle("Definitions");
        add_btn = (Button) findViewById(R.id.teacher_add_btn);
//        test = findViewById(R.id.teacher_Test2);
        definition = (EditText) findViewById(R.id.teacher_definition);
        definition_value = (EditText) findViewById(R.id.teacher_Definition_value);
        definition_list = (ListView) findViewById(R.id.teacher_definition_list);
        firebaseAuth = FirebaseAuth.getInstance();
        definitions = new ArrayList<String>();
        root_database = FirebaseDatabase.getInstance().getReference().child("users");
        user = FirebaseAuth.getInstance().getCurrentUser();
        subject=getIntent().getStringExtra("subject");
        Log.d("hithere", definition.getText().toString());
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (definition.getText().toString().length() > 0 ) {
                    root_database.child(user.getEmail().substring(0, user.getEmail().indexOf("@"))).child("subjects").child(subject).child(definition.getText().toString()).setValue(definition_value.getText().toString());
                    Toast.makeText(TeacherDefinitions.this, "added data", Toast.LENGTH_SHORT).show();
                    definitions.add(definition.getText().toString() + "|split|" + definition_value.getText().toString()+ "|*subject*|" + subject);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(TeacherDefinitions.this, "Definition Must Be Not Null", Toast.LENGTH_SHORT).show();
                }
            }
        });
        root_database.child(user.getEmail().substring(0, user.getEmail().indexOf("@"))).child("subjects").child(subject).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                            definitions.add(dsp.getKey() + "|split|" + dsp.getValue().toString()+ "|*subject*|" + subject);
                        }
                        definition_list.setAdapter(adapter);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
        adapter = new DefinitionsAdapter(this, definitions);

//        test.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(TeacherDefinitions.this,TeacherQuiz.class);
//                i.putExtra("subject",subject);
//                startActivity(i);
//            }
//        });
    }
}