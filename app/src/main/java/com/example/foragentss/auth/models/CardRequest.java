package com.example.foragentss.auth.models;

public class CardRequest {
    private int id;
    private int card_type_id;
    private int amount;
    private int company_agent_id;
    private int index;
    private int payment_type_id;
    private int requester_id;
    private String status;
    public CardRequest() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCard_type_id() {
        return card_type_id;
    }

    public void setCard_type_id(int card_type_id) {
        this.card_type_id = card_type_id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getCompany_agent_id() {
        return company_agent_id;
    }

    public void setCompany_agent_id(int company_agent_id) {
        this.company_agent_id = company_agent_id;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getPayment_type_id() {
        return payment_type_id;
    }

    public void setPayment_type_id(int payment_type_id) {
        this.payment_type_id = payment_type_id;
    }

    public int getRequester_id() {
        return requester_id;
    }

    public void setRequester_id(int requester_id) {
        this.requester_id = requester_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
