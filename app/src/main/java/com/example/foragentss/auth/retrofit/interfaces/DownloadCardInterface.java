package com.example.foragentss.auth.retrofit.interfaces;

import com.example.foragentss.auth.models.DownloadData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface DownloadCardInterface {

    @GET("retailers/download_card/{card_type_id}/{amount}")
    Call<DownloadData> show(@Path("card_type_id")int card_type_id,@Path("amount")int amoun);

}
