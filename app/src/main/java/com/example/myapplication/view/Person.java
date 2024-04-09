package com.example.myapplication.view;

public class Person {
    private String fullName;
    private String emailAddress;
    private String contactNumber;
    private String birthdate;
    private int age;
    private String gender;

    public Person() {
    }

    public Person(String fullName, String emailAddress, String contactNumber, String birthdate, int age, String gender) {
        this.fullName = fullName;
        this.emailAddress = emailAddress;
        this.contactNumber = contactNumber;
        this.birthdate = birthdate;
        this.age = age;
        this.gender = gender;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
