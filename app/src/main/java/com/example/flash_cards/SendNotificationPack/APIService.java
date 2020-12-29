package com.example.flash_cards.SendNotificationPack;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAJtCsuyo:APA91bGGbInoyUlyb1UIDQ9hXR-QdKzzr4O6k3dApwchqxdrBFpPpUT_roOVqv12HOC8sfuxx1mvFeGlP9Lcn4ihHMNkw02QvsoCaIuxRUKydm8cE7VzjrQ3yBUZ7sB7xB6Hgyz_uTTv" // Your server key refer to video for finding your server key
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotifcation(@Body NotificationSender body);
}

