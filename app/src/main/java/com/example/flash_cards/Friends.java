package com.example.flash_cards;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Friends extends AppCompatActivity {
    ListView friends_list;
    EditText search_words;
    Button btn_search;
    ArrayList<String> friends;
    MyAdapter adapter;
    FirebaseAuth firebaseAuth;
    DatabaseReference root_database;
    FirebaseUser user;
    ArrayList<String> Userlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        friends_list = (ListView) findViewById(R.id.friends_list);
        firebaseAuth = FirebaseAuth.getInstance();
        search_words = (EditText) findViewById(R.id.friends_search);
        btn_search = (Button) findViewById(R.id.friends_btn);
        friends = new ArrayList<String>();
        root_database = FirebaseDatabase.getInstance().getReference().child("users");
        user = FirebaseAuth.getInstance().getCurrentUser();
        on_btn_click();

        root_database.child(user.getEmail().substring(0, user.getEmail().indexOf("@"))).child("friends").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Result will be holded Here
                        for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                            friends.add(String.valueOf(dsp.getValue()));
                        }
                        friends_list.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        adapter = new MyAdapter(this, friends);

    }

    /**
     * method is used for checking valid email id format.
     *
     * @param email
     * @return boolean true for valid false for invalid
     */
    public boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    void checkEmailExistsOrNot(){
        firebaseAuth.fetchSignInMethodsForEmail(search_words.getText().toString()).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
//                Log.d(TAG,""+task.getResult().getSignInMethods().size());
                if (task.getResult().getSignInMethods().size() == 0){
                    Toast.makeText(Friends.this, "That User does not exists", Toast.LENGTH_SHORT).show();
                }else {
                    root_database.child(user.getEmail().substring(0 ,user.getEmail().indexOf("@"))).child("friends").push().setValue(search_words.getText().toString());
                    friends.add(search_words.getText().toString());
                    Log.d("hithere", "f2");

                    adapter.notifyDataSetChanged();
                    Log.d("hithere", "f3");

                    Toast.makeText(Friends.this, "added to database", Toast.LENGTH_SHORT).show();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        });
    }
    public void on_btn_click() {
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmailValid(search_words.getText().toString())) {
                    checkEmailExistsOrNot();
                } else {
                    Toast.makeText(Friends.this, "Please type an email", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}