package com.example.flash_cards.notificationsPack;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;



public interface APIService {
    @Headers
    (
        {
            "Content-Type:application/json",
            "Authorization:key=AAAAJtCsuyo:APA91bGGbInoyUlyb1UIDQ9hXR-QdKzzr4O6k3dApwchqxdrBFpPpUT_roOVqv12HOC8sfuxx1mvFeGlP9Lcn4ihHMNkw02QvsoCaIuxRUKydm8cE7VzjrQ3yBUZ7sB7xB6Hgyz_uTTv"
        }


    )
    @POST("fcm/send")
    Call<MyResponse>sendNotification(@Body NotificationSender body);
}
