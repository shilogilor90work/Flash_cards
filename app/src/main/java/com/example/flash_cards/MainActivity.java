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

public class MainActivity extends AppCompatActivity {

    EditText full_name, email, password;
    Button register;
    TextView login;
    FirebaseAuth fbauth;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Register");

        full_name = findViewById(R.id.full_name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password_user);
        register = findViewById(R.id.register_button);
        login = findViewById(R.id.login_button);
        fbauth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String full_name_string = full_name.getText().toString();
                String email_string = email.getText().toString();
                String password_string = password.getText().toString();

                if(TextUtils.isEmpty(full_name_string)){
                    full_name.setError("Name is Required");
                    return;
                }
                if(TextUtils.isEmpty(email_string)){
                    email.setError("Email is Required");
                    return;
                }
                if(TextUtils.isEmpty(password_string)){
                    password.setError("Password is Required");
                    return;
                }
                if(email_string.length() < 6){
                    password.setError("Password must be atleast 6 charecters");
                }
                progressBar.setVisibility(View.VISIBLE);

                fbauth.createUserWithEmailAndPassword(email_string, password_string).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "Created",Toast.LENGTH_SHORT).show();
                            // go to activity.
//                            startActivity(new Intent(getApplicationContext(), Home_page.class));
                        } else {
                            Toast.makeText(MainActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });
    }
}