package com.example.myapplication.viewmodel;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.repository.PersonRepository;
import com.example.myapplication.repository.local.AppDatabase;
import com.example.myapplication.repository.model.ResponseModel;
import com.example.myapplication.view.Person;

public class PersonRegistrationViewModel extends AndroidViewModel {

    private PersonRepository repository;
    private LiveData<ResponseModel> apiResponseLiveData;

    public PersonRegistrationViewModel(Application application) {
        super(application);
        repository = new PersonRepository(application);
        this.apiResponseLiveData = repository.getApiResponseLiveData();
    }

    public LiveData<ResponseModel> getApiResponseLiveData() {
        return apiResponseLiveData;
    }

    public void registerDetails(Person person) {
        repository.register(person);
    }




}
