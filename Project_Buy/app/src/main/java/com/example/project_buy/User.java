package com.example.project_buy;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {

    public String phonenumber;
    public String address;

    public User() {
        this.phonenumber = "010-3192-9447";
        this.address = "seoul";
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String phonenumber, String address) {
        this.phonenumber = phonenumber;
        this.address = address;
    }

    public String getUserPhoneNumber() {
        return phonenumber;
    }

    public void setUserPhoneNumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "User{" +
                "phonenumber='" + phonenumber + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
