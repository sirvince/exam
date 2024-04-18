package com.example.myapplication.repository.local.dao;

import android.provider.ContactsContract;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.myapplication.repository.local.entity.PersonEntity;

import java.util.List;

@Dao
public interface PersonDao {
    @Insert
    void insert(PersonEntity person);

    @Query("SELECT * FROM persons")
    LiveData<List<PersonEntity>> getAllPerson();
}
