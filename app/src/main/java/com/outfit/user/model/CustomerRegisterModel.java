package com.outfit.user.model;

public class CustomerRegisterModel {
    public String firstNameTv, lastNameTv, emailTv, passTv;

    public CustomerRegisterModel() {
    }

    public CustomerRegisterModel(String firstNameTv, String lastNameTv, String emailTv, String passTv) {
        this.firstNameTv = firstNameTv;
        this.lastNameTv = lastNameTv;
        this.emailTv = emailTv;
        this.passTv = passTv;
    }

    public String getFirstNameTv() {
        return firstNameTv;
    }

    public void setFirstNameTv(String firstNameTv) {
        this.firstNameTv = firstNameTv;
    }

    public String getLastNameTv() {
        return lastNameTv;
    }

    public void setLastNameTv(String lastNameTv) {
        this.lastNameTv = lastNameTv;
    }

    public String getEmailTv() {
        return emailTv;
    }

    public void setEmailTv(String emailTv) {
        this.emailTv = emailTv;
    }

    public String getPassTv() {
        return passTv;
    }

    public void setPassTv(String passTv) {
        this.passTv = passTv;
    }

}
