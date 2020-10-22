package com.example.foragentss.auth.models;

import java.io.Serializable;

public class Connect implements Serializable {
    private int id;
    private int company_user_id;
    private int agent_partner_retailer_id;
    private String status;
    public int getCompany_user_id() {
        return company_user_id;
    }

    public void setCompany_user_id(int company_user_id) {
        this.company_user_id = company_user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAgent_partner_retailer_id() {
        return agent_partner_retailer_id;
    }

    public void setAgent_partner_retailer_id(int agent_partner_retailer_id) {
        this.agent_partner_retailer_id = agent_partner_retailer_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
