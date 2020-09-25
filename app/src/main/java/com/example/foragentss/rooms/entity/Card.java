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

    @ColumnInfo(name = "type")
    private String type;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
}
