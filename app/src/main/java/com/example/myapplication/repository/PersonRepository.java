package com.example.myapplication.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.repository.model.ResponseModel;
import com.example.myapplication.repository.remote.ApiClient;
import com.example.myapplication.repository.remote.ApiService;
import com.example.myapplication.view.Person;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonRepository {

    private ApiService apiService;
    private MutableLiveData<ResponseModel> apiResponseLiveData;

    public PersonRepository() {
        apiService = ApiClient.getClient().create(ApiService.class);
        apiResponseLiveData = new MutableLiveData<>();
    }

    public LiveData<ResponseModel> getApiResponseLiveData() {
        return apiResponseLiveData;
    }
    public void register(Person person) {
        Call<ResponseModel> call = apiService.getApiResponse();
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.isSuccessful()) {
                    ResponseModel responseModel = new ResponseModel();
                    responseModel.setMessage(response.body().getMessage());
                    responseModel.setCode(response.code());
                    apiResponseLiveData.postValue(responseModel); //
                }else{
                    ResponseModel responseModel = new ResponseModel();
                    responseModel.setCode(response.code());
                    responseModel.setMessage(response.errorBody().toString());
                    apiResponseLiveData.postValue(responseModel); //
                }
            }
            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                ResponseModel responseModel = new ResponseModel();
                responseModel.setCode(500);
                responseModel.setMessage( t.getMessage());
                apiResponseLiveData.postValue(responseModel); //
            }

        });
    }
}
