package com.example.foragentss.auth.response;

import com.example.foragentss.auth.models.AgentPartner;

import java.util.List;

public class AgentPartnerResponse {
    private List<AgentPartner> data;

    public List<AgentPartner> getData() {
        return data;
    }

    public void setData(List<AgentPartner> data) {
        this.data = data;
    }
}
