package com.example.foragentss.auth.response;

import com.example.foragentss.auth.models.MeData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MeResponse {

    @SerializedName("data")
    @Expose
   private MeData data;

    public MeData getData() {
        return data;
    }

    public void setData(MeData data) {
        this.data = data;
    }
}
