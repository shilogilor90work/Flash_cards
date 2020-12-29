package com.example.flash_cards;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.flash_cards.SendNotificationPack.Token;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

public class Login extends AppCompatActivity {
    EditText email, password;
    TextView register;
    Button login;
    FirebaseAuth fbauth;
    ProgressBar progressBar;
    DatabaseReference root_database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Flash Cards");
        email = findViewById(R.id.email2);
        password = findViewById(R.id.password_user2);
        register = findViewById(R.id.register_link);
        login = findViewById(R.id.Login_button2);
        fbauth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar2);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_string = email.getText().toString();
                String password_string = password.getText().toString();
                if (TextUtils.isEmpty(email_string)) {
                    email.setError("Email is Required");
                    return;
                }
                if (TextUtils.isEmpty(password_string)) {
                    password.setError("Password is Required");
                    return;
                }
                if (password_string.length() < 6) {
                    password.setError("Password must be atleast 6 charecters");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                fbauth.signInWithEmailAndPassword(email_string, password_string).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Login.this, "success", Toast.LENGTH_SHORT).show();
                            root_database = FirebaseDatabase.getInstance().getReference().child("users");
                            String refreshToken= FirebaseInstanceId.getInstance().getToken();
                            Token token= new Token(refreshToken);
                            root_database.child(email_string.substring(0, email_string.indexOf("@"))).child("Token").setValue(token);
                            root_database.child(email_string.substring(0, email_string.indexOf("@"))).child("role").addListenerForSingleValueEvent(
                                    new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            Toast.makeText(Login.this, dataSnapshot.getValue().toString(), Toast.LENGTH_SHORT).show();
                                            if (dataSnapshot.getValue().toString().equals("teacher")) {
                                                Intent i = new Intent(Login.this,TeacherSubjects.class);
                                                i.putExtra("role", "teacher");
                                                startActivity(i);

                                            } else {
                                                Intent i = new Intent(Login.this,Subjects.class);
                                                i.putExtra("role", "student");
                                                startActivity(i);
                                            }
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                        }
                                    });
                        } else {
                            Toast.makeText(Login.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
    }
}