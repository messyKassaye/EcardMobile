package com.example.foragentss.auth.models;

public class CardRequest {
    private int id;
    private int card_type_id;
    private int amount;
    private int company_agent_id;
    private int index;
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
}
