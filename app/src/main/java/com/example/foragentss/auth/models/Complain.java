package com.example.foragentss.auth.models;

public class Complain {
    private int id;
    private int card_request_id;
    private String complain;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCard_request_id() {
        return card_request_id;
    }

    public void setCard_request_id(int card_request_id) {
        this.card_request_id = card_request_id;
    }

    public String getComplain() {
        return complain;
    }

    public void setComplain(String complain) {
        this.complain = complain;
    }
}
