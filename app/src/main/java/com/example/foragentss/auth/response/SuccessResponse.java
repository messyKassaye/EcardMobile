package com.example.foragentss.auth.response;

import com.example.foragentss.auth.models.CardRequest;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SuccessResponse {
    @SerializedName("status")
    @Expose
    private boolean status;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("index")
    @Expose
    private int index;

    @SerializedName("card_request")
    @Expose
    private CardRequest card_request;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public CardRequest getCard_request() {
        return card_request;
    }

    public void setCard_request(CardRequest card_request) {
        this.card_request = card_request;
    }
}
