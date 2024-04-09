package com.example.myapplication.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.repository.PersonRepository;
import com.example.myapplication.repository.model.ResponseModel;
import com.example.myapplication.view.Person;

public class PersonRegistrationViewModel extends ViewModel {

    private PersonRepository repository;
    private LiveData<ResponseModel> apiResponseLiveData;

    public PersonRegistrationViewModel() {
        repository = new PersonRepository();
        this.apiResponseLiveData = repository.getApiResponseLiveData();
    }

    public LiveData<ResponseModel> getApiResponseLiveData() {
        return apiResponseLiveData;
    }

    public void registerDetails(Person person) {
        repository.register(person);
    }




}
