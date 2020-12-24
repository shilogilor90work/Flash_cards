package com.example.flash_cards.notificationsPack;

public class NotificationSender
{
    public Data data;
    public String to;//this holds the token to which we pass the message

    public NotificationSender(Data data, String to)
    {
        this.data = data;
        this.to = to;
    }

    public NotificationSender()
    {
    }
}
