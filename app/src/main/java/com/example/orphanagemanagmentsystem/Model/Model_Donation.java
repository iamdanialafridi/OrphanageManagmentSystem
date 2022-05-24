package com.example.orphanagemanagmentsystem.Model;

import androidx.annotation.NonNull;

public class Model_Donation {
    private String Key,OrphanId,UID,Name,Email,Phone,CardType,CardNumber,Amount,Date;

    public Model_Donation() {
    }

    public Model_Donation(@NonNull String key, String UID,String orphanId, String name, String email, String phone, String cardType, String cardNumber, String amount, String date) {
        Key = key;
        this.UID = UID;
        this.OrphanId=orphanId;
        Name = name;
        Email = email;
        Phone = phone;
        CardType = cardType;
        CardNumber = cardNumber;
        Amount = amount;
        Date = date;
    }

    public String getOrphanId() {
        return OrphanId;
    }

    public void setOrphanId(String orphanId) {
        OrphanId = orphanId;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
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

    public String getCardType() {
        return CardType;
    }

    public void setCardType(String cardType) {
        CardType = cardType;
    }

    public String getCardNumber() {
        return CardNumber;
    }

    public void setCardNumber(String cardNumber) {
        CardNumber = cardNumber;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
