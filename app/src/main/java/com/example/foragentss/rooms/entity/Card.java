package com.example.foragentss.rooms.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cards")
public class Card {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "number")
    private String number;

    @ColumnInfo(name = "card_type_id")
    private int card_type_id;

    @ColumnInfo(name = "server_card_id")
    private int server_card_id;

    @ColumnInfo(name = "status",defaultValue = "not_soled")
    private String status;

    @ColumnInfo(name = "sellsDate",defaultValue = "null")
    private String sellsDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getCard_type_id() {
        return card_type_id;
    }

    public void setCard_type_id(int card_type_id) {
        this.card_type_id = card_type_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSellsDate() {
        return sellsDate;
    }

    public void setSellsDate(String sellsDate) {
        this.sellsDate = sellsDate;
    }

    public int getServer_card_id() {
        return server_card_id;
    }

    public void setServer_card_id(int server_card_id) {
        this.server_card_id = server_card_id;
    }
}
