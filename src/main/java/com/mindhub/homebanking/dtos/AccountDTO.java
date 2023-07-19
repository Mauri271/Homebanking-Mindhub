package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.enums.AccountType;
import com.mindhub.homebanking.models.Account;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDTO {

    private Long id;
    private String number;
    private LocalDate date;
    private double balance;
    private Set<TransactionDTO> transaction;
    private Boolean deleted;
    private AccountType accountType;



    public AccountDTO(Account account) {
        this.id = account.getId();
        this.number = account.getNumber();
        this.date = account.getDate();
        this.balance = account.getBalance();
        this.transaction = account.getTransactions().stream().map(TransactionDTO::new).collect(Collectors.toSet());
        this.deleted = account.getDeleted();
        this.accountType = account.getAccountType();
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getBalance() {
        return balance;
    }

    public String getNumber() {
        return number;
    }

    public Set<TransactionDTO> getTransaction() {
        return transaction;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public AccountType getAccountType() {
        return accountType;
    }
}
