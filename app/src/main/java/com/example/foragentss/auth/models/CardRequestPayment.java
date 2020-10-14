package com.example.foragentss.auth.models;

public class CardRequestPayment {
    private int card_request_id;
    private int bank_id;
    private String reference_number;
    private int index;

    public int getCard_request_id() {
        return card_request_id;
    }

    public void setCard_request_id(int card_request_id) {
        this.card_request_id = card_request_id;
    }

    public int getBank_id() {
        return bank_id;
    }

    public void setBank_id(int bank_id) {
        this.bank_id = bank_id;
    }

    public String getReference_number() {
        return reference_number;
    }

    public void setReference_number(String reference_number) {
        this.reference_number = reference_number;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
