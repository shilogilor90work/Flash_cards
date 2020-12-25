package com.example.flash_cards.notificationsPack;


//each user holds a unique token id
public class Token {
    private String token;

    public Token(String token)
    {
        this.token = token;
    }

    public Token()
    {

    }

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }
}
