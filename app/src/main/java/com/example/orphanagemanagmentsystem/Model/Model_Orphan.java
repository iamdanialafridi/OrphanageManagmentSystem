package com.example.orphanagemanagmentsystem.Model;

import androidx.annotation.NonNull;

public class Model_Orphan {
    private String Key,Name,DOB,Age,Gender,Address,Image;


    public Model_Orphan() {
    }

    public Model_Orphan(@NonNull String key, String name, String DOB, String age, String gender, String address, String image) {
        Key = key;
        Name = name;
        this.DOB = DOB;
        Age = age;
        Gender = gender;
        Address = address;
        Image = image;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
