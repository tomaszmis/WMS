package com.wms.api.model;

public class RegistrationHelper {

    private String login;
    private String password;
    private String passwordConfirm;
    private String email;
    private String name;
    private String surname;
    private String whName;
    private String whDesc;

    public User getUser() {
        return new User(this.name, this.surname, this.login, this.email, this.password, this.passwordConfirm);
    }

    public Warehouse getWh() {
        return new Warehouse(this.whName, this.whDesc);
    }

    public String getLogin() {
        return this.login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return this.passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getWhName() {
        return this.whName;
    }

    public void setWhName(String name) {
        this.whName = name;
    }

    public String getWhDesc() {
        return this.whDesc;
    }

    public void setWhDesc(String desc) {
        this.whDesc = desc;
    }

}
