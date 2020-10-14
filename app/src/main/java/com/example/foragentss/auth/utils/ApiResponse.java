package com.example.foragentss.auth.utils;

import com.example.foragentss.auth.response.SuccessResponse;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

import static com.example.foragentss.auth.utils.Status.ERROR;
import static com.example.foragentss.auth.utils.Status.LOADING;
import static com.example.foragentss.auth.utils.Status.SUCCESS;


/**
 * Created by ${Saquib} on 03-05-2018.
 */

public class ApiResponse {

  public final Status status;

  @Nullable
  public final SuccessResponse data;

  @Nullable
  public final Throwable error;

  private ApiResponse(Status status, @Nullable SuccessResponse data, @Nullable Throwable error) {
    this.status = status;
    this.data = data;
    this.error = error;
  }

  public static ApiResponse loading() {
    return new ApiResponse(LOADING, null, null);
  }

  public static ApiResponse success(@NonNull SuccessResponse data) {
    return new ApiResponse(SUCCESS, data, null);
  }

  public static ApiResponse error(@NonNull Throwable error) {
    return new ApiResponse(ERROR, null, error);
  }

}