package com.example.orphanagemanagmentsystem.Model;

import androidx.annotation.NonNull;

public class Model_Donor {
    private String Name,Email,Phone,Address,Password,UID;


    public Model_Donor() {
    }

    public Model_Donor(@NonNull String name, String email, String phone, String address, String password, String UID) {
        Name = name;
        Email = email;
        Phone = phone;
        Address = address;
        Password = password;
        this.UID = UID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }
}
