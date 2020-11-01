package com.example.foragentss.auth.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User  implements Serializable {
    private int id;
    private String first_name;
    private String phone;
    private String user_name = "Ecard";
    private String password;
    private int role_id;
    private List<Role> role;
    private Company company;
    private Verification verification;
    private Connect connection;
    private ArrayList<CardPrice> card_price;
    private ArrayList<BankAccount> bank_account;

    public User(String first_name,String phone, String password, int role_id) {
        this.first_name = first_name;
        this.phone = phone;
        this.password = password;
        this.role_id = role_id;
    }

    public User() {
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public List<Role> getRole() {
        return role;
    }

    public void setRole(List<Role> role) {
        this.role = role;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Verification getVerification() {
        return verification;
    }

    public void setVerification(Verification verification) {
        this.verification = verification;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Connect getConnection() {
        return connection;
    }

    public void setConnection(Connect connection) {
        this.connection = connection;
    }

    public ArrayList<CardPrice> getCard_price() {
        return card_price;
    }

    public void setCard_price(ArrayList<CardPrice> card_price) {
        this.card_price = card_price;
    }

    public ArrayList<BankAccount> getBank_account() {
        return bank_account;
    }

    public void setBank_account(ArrayList<BankAccount> bank_account) {
        this.bank_account = bank_account;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
