package com.example.foragentss.auth.retrofit.interfaces;

import com.example.foragentss.auth.models.BankAccount;
import com.example.foragentss.auth.response.SuccessResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface BankAccountInterface {

    @POST("bank_account")
    Observable<SuccessResponse> store(@Body BankAccount bankAccount);

    @FormUrlEncoded
    @PATCH("bank_account/{id}")
    Observable<SuccessResponse> update(@Path("id") int id, @Field("account_number") String account_number,
                                       @Field("holder_full_name") String holder_full_name);
}
