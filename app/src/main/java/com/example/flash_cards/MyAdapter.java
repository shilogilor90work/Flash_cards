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
import com.example.flash_cards.SendNotificationPack.*;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.ArrayList;

public class MyAdapter extends ArrayAdapter {
    DatabaseReference root_database;
    FirebaseUser user;
    private APIService apiService;
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
        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
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
                                list_txt.setText("Your DB was shared with: " + item);
                                Log.d("here", "here");
                                root_database.child(item.substring(0, item.indexOf("@"))).child("Token").child("token").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        String usertoken=dataSnapshot.getValue(String.class);
                                        Log.d("here", "test" + item.substring(0, item.indexOf("@")));
                                        Log.d("here", "test" + usertoken);
                                        sendNotifications(usertoken, "Update Detected: " , "Your Subjects Were Updated By: " + user.getEmail());
                                        Log.d("here", "2");
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                list_btn.setVisibility(View.GONE);
            }
        });
        return convertView;
    }
    public void sendNotifications(String usertoken, String title, String message) {
        Data data = new Data(title, message);
        NotificationSender sender = new NotificationSender(data, usertoken);
        apiService.sendNotifcation(sender).enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                Log.d("here", "start " + response.code());

                if (response.code() == 200) {
                    Log.d("here", "200" + sender.to);

                    if (response.body().success != 1) {
                        Log.d("here", "failed " + response.body());
                        Toast.makeText(getContext(), "Failed ", Toast.LENGTH_LONG);
                    }
                }
            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {

            }
        });
    }
}
