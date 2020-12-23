package com.example.flash_cards;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Contact extends AppCompatActivity {

    EditText etTo,etSubject,etMessage;
    Button btSend;
    String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        setTitle("Contact");
        role = getIntent().getStringExtra("role");
        BottomNavigationView bnv = findViewById(R.id.BottomNavigationView);
        bnv.setSelectedItemId(R.id.Subjects_item);
        Log.d("hithere", role);
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
                        return true;
                }
                return false;
            }
        });

        etTo = findViewById(R.id.et_to);
        etSubject = findViewById(R.id.et_subject);
        etMessage = findViewById(R.id.et_message);
        btSend = findViewById(R.id.bt_snd);

        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("mailto:" + etTo.getText().toString()));
                in.putExtra(Intent.EXTRA_SUBJECT,etSubject.getText().toString());
                in.putExtra(Intent.EXTRA_TEXT,etMessage.getText().toString());
                startActivity(in);
            }
        });
    }
}
