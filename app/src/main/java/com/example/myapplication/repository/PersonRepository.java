package com.example.myapplication.repository;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.repository.local.AppDatabase;
import com.example.myapplication.repository.local.dao.PersonDao;
import com.example.myapplication.repository.local.entity.PersonEntity;
import com.example.myapplication.repository.model.ResponseModel;
import com.example.myapplication.repository.remote.ApiClient;
import com.example.myapplication.repository.remote.ApiService;
import com.example.myapplication.view.Person;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonRepository  {

    private ApiService apiService;
    private PersonDao personDao;
    private MutableLiveData<ResponseModel> apiResponseLiveData;
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    public PersonRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        personDao = database.personDao();
        apiService = ApiClient.getClient().create(ApiService.class);
        apiResponseLiveData = new MutableLiveData<>();
    }

    public LiveData<ResponseModel> getApiResponseLiveData() {
        return apiResponseLiveData;
    }
    public void register(Person person) {
        Call<ResponseModel> call = apiService.saveDetails(person);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.isSuccessful()) {
                    saveLocalDetails(person);
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

    private void saveLocalDetails(Person person) {
        PersonEntity persons = new PersonEntity();
        persons.setFullName(person.getFullName());
        persons.setEmailAddress(person.getEmailAddress());
        persons.setBirthdate(person.getBirthdate());
        persons.setContactNumber(person.getContactNumber());
        persons.setAge(person.getAge());
        persons.setGender(person.getGender());

        executor.submit(() -> {
            personDao.insert(persons);
        });
    }
}
