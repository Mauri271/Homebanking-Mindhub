package com.mindhub.homebanking.dtos;

public class TransferDTO {

    private double amount;
    private String description, originAccount, destinationAccount;
    public TransferDTO() {
    }

    public TransferDTO(double amount, String description, String originAccount, String destinationAccount) {
        this.amount = amount;
        this.description = description;
        this.originAccount = originAccount;
        this.destinationAccount = destinationAccount;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public String getOriginAccount() {
        return originAccount;
    }

    public String getDestinationAccount() {
        return destinationAccount;
    }
}

