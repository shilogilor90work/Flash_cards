package com.example.flash_cards;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {


    EditText email, password;
    TextView register;
    Button login;
    FirebaseAuth fbauth;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login");


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
                }
                progressBar.setVisibility(View.VISIBLE);

                fbauth.signInWithEmailAndPassword(email_string, password_string).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Login.this, "success", Toast.LENGTH_SHORT).show();
                            // go to activity
//                            startActivity(new Intent(getApplicationContext(), Home_page.class));
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