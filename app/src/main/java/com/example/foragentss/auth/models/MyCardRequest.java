package com.example.foragentss.auth.models;

import java.util.ArrayList;

public class MyCardRequest {
    private int id;
    private int amount;
    private String status;
    private ArrayList<User> user;
    private CardType card_type;
    private CardRequestPayment payment;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<User> getUser() {
        return user;
    }

    public void setUser(ArrayList<User> user) {
        this.user = user;
    }

    public CardType getCard_type() {
        return card_type;
    }

    public void setCard_type(CardType card_type) {
        this.card_type = card_type;
    }

    public CardRequestPayment getPayment() {
        return payment;
    }

    public void setPayment(CardRequestPayment payment) {
        this.payment = payment;
    }
}
