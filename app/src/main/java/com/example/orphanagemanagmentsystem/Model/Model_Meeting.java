package com.example.orphanagemanagmentsystem.Model;

import androidx.annotation.NonNull;

public class Model_Meeting {
private  String UID,Key,Name,Age,Gender,Message,Status;

    public Model_Meeting() {
    }

    public Model_Meeting(@NonNull String UID, String key, String name, String age, String gender, String message, String status) {
        this.UID = UID;
        Key = key;
        Name = name;
        Age = age;
        Gender = gender;
        Message = message;
        Status = status;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
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

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
