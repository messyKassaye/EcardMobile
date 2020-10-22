package com.example.foragentss.rooms.entity;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "agentPartner")
public class AgentAndPartner {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "agent_partner_id")
    private int agent_partner_id;

    @ColumnInfo(name = "full_name")
    private String full_name;

    @ColumnInfo(name = "phone")
    private String phone;

    @ColumnInfo(name = "email")
    private String email;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAgent_partner_id() {
        return agent_partner_id;
    }

    public void setAgent_partner_id(int agent_partner_id) {
        this.agent_partner_id = agent_partner_id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
