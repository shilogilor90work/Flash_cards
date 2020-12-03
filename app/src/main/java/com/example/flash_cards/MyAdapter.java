package com.example.flash_cards;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyAdapter extends ArrayAdapter {
    DatabaseReference root_database;
    FirebaseUser user;
    public MyAdapter(Context context, ArrayList<String> records) {
        super(context, 0, records);
    }

    private void moveRecord(DatabaseReference fromPath, final DatabaseReference toPath) {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                toPath.setValue(dataSnapshot.getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isComplete()) {
                            Log.d("TAG", "Success!");
                        } else {
                            Log.d("TAG", "Copy failed!");
                        }
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        fromPath.addListenerForSingleValueEvent(valueEventListener);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        root_database = FirebaseDatabase.getInstance().getReference().child("users");
        user = FirebaseAuth.getInstance().getCurrentUser();

        String item = (String) getItem(position);
        if (convertView==null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_custom, null);
        }
        TextView list_txt = (TextView) convertView.findViewById(R.id.List_txt);
        Button list_btn = (Button) convertView.findViewById(R.id.List_btn);
        list_txt.setText(item);
        list_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveRecord(root_database.child( user.getEmail().substring(0, user.getEmail().indexOf("@"))).child("subjects"),root_database.child(list_txt.getText().toString().substring(0, list_txt.getText().toString().indexOf("@"))).child("subjects"));
                list_txt.setText("Your DB was shared with: " + list_txt.getText().toString());
                list_btn.setVisibility(View.GONE);
            }
        });
        return convertView;
    }
}
