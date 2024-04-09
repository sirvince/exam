package com.example.myapplication.repository.remote;

import com.example.myapplication.repository.model.ResponseModel;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("/v3/3aa344a8-4948-46f3-b865-8678b9fbb522")
    Call<ResponseModel> getApiResponse();
}
