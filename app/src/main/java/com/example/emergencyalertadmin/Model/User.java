package com.example.emergencyalertadmin.Model;



public class User implements IUser {

    String email,category;

    public User(String email, String password) {
        this.email = email;
        this.category = password;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getCategory() {
        return category;
    }


}
