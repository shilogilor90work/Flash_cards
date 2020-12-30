package com.example.flash_cards.SendNotificationPack;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdReceiver;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        String token = FirebaseInstanceId.getInstance().getToken();
        if(firebaseUser!=null){
            updateToken(token);
        }
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        FirebaseDatabase.getInstance().getReference().child("users").child("Token").child(user.getEmail().substring(0, user.getEmail().indexOf("@"))).setValue(mToken);
    }

    private void updateToken(String token){
        Token token1= new Token(token);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase.getInstance().getReference().child("users").child("Token").child(user.getEmail().substring(0, user.getEmail().indexOf("@"))).setValue(token1);
    }
}
