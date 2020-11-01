package com.example.foragentss.auth.retrofit.interfaces;

import com.example.foragentss.auth.models.MyPartner;
import com.example.foragentss.auth.response.AgentPartnerResponse;
import com.example.foragentss.auth.response.SuccessResponse;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface MyPartnerAgentInterface {

    @GET("{path}")
    Call<AgentPartnerResponse> index(@Path("path")String path);

    @POST("{path}")
    Observable<SuccessResponse> store(@Path("path")String path, @Body MyPartner myPartner);

    @PUT("{path}/{id}")
    Observable<SuccessResponse> update(@Path("path")String path,@Path("id") int id, @Body MyPartner myPartner);



}
