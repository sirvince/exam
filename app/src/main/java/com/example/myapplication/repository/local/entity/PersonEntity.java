package com.example.myapplication.repository.local.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "persons")
public class PersonEntity {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String fullName;
    private String emailAddress;
    private String contactNumber;
    private String birthdate;
    private int age;
    private String gender;

    public PersonEntity() {
    }

    public PersonEntity(long id, String fullName, String emailAddress, String contactNumber, String birthdate, int age, String gender) {
        this.id = id;
        this.fullName = fullName;
        this.emailAddress = emailAddress;
        this.contactNumber = contactNumber;
        this.birthdate = birthdate;
        this.age = age;
        this.gender = gender;
    }


    public long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
