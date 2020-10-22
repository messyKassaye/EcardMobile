package com.example.foragentss.auth.models;

import java.io.Serializable;

public class BankAccount implements Serializable {
    private int id;
    private int bank_id;
    private String holder_full_name;
    private String account_number;
    private Bank bank;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHolder_full_name() {
        return holder_full_name;
    }

    public void setHolder_full_name(String holder_full_name) {
        this.holder_full_name = holder_full_name;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public int getBank_id() {
        return bank_id;
    }

    public void setBank_id(int bank_id) {
        this.bank_id = bank_id;
    }
}
