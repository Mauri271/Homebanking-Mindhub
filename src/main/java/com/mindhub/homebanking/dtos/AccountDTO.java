package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Accounts;
import java.time.LocalDate;

public class AccountDTO {

    private long id;
    private String number;
    private LocalDate date;
    private double balance;

    public AccountDTO() { }
    public AccountDTO(Accounts account) {
        this.id = account.getOwner().getId();
        this.number = account.getNumber();
        this.date = account.getDate();
        this.balance = account.getBalance();
    }



    public long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getBalance() {
        return balance;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
