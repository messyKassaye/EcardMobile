package com.example.foragentss.auth.response;

import com.example.foragentss.auth.models.CardType;

import java.util.ArrayList;
import java.util.List;

public class CardTypeResponse {
    private List<CardType> cardTypes;

    public List<CardType> getCardTypes() {
        return cardTypes;
    }

    public void setCardTypes(List<CardType> cardTypes) {
        this.cardTypes = cardTypes;
    }
}
