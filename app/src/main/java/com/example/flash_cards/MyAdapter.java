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

import com.example.flash_cards.notificationsPack.APIService;
import com.example.flash_cards.notificationsPack.Client;
import com.example.flash_cards.notificationsPack.Data;
import com.example.flash_cards.notificationsPack.MyResponse;
import com.example.flash_cards.notificationsPack.NotificationSender;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAdapter extends ArrayAdapter {
    /////////////////////additional changes
    private APIService apiService;
    private String Who_Sent;
    private String receiver_token;
    Boolean Shared_succ=true;


    ////////////////////
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
                            //Shared_succ=true;

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
                root_database.child(user.getEmail().substring(0, user.getEmail().indexOf("@"))).child("subjects").addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                                    moveRecord(root_database.child( user.getEmail().substring(0, user.getEmail().indexOf("@"))).child("subjects").child(dsp.getKey().toString()),root_database.child(list_txt.getText().toString().substring(0, list_txt.getText().toString().indexOf("@"))).child("subjects").child(dsp.getKey().toString()));
                                }
                                list_txt.setText("Your DB was shared with: " + list_txt.getText().toString());


                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                list_btn.setVisibility(View.GONE);
                //additional information added here
                if(Shared_succ)
                {
                    apiService=Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
                    Who_Sent=root_database.child(user.getEmail().substring(0,user.getEmail().indexOf("@"))).toString();
                    root_database.child(list_txt.getText().toString().substring(0, list_txt.getText().toString().indexOf("@"))).child("token").addListenerForSingleValueEvent(
                            new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    //receiver_token=snapshot.getKey().toString();
                                    receiver_token=snapshot.getValue(String.class);
                                    sendNotifications(receiver_token,"fgascbqw");
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            }
                    );


                }
                ///////////////////////////////////

                /*root_database.child(list_txt.getText().toString().substring(0, list_txt.getText().toString().indexOf("@"))).child("token").addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                receiver_token=snapshot.getValue(String.class);
                                sendNotifications(receiver_token,"fffffw");

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        }
                );*/
                //////////////////////


            }

        });

        return convertView;

    }

    //additional change
    private void sendNotifications(String usertoken,String message) {
        Data data = new Data(message);
        NotificationSender sender = new NotificationSender(data, usertoken);
        apiService.sendNotification(sender).enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                if (response.code() == 200) {
                    if (response.body().success != 1) {
                        //Toast.makeText(SendNotif.this, "Failed ", Toast.LENGTH_LONG);
                        Log.d("TAG", "Success!");
                    }
                }
            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {

            }
        });
    }
}
//
