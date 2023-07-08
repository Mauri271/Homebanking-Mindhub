package com.mindhub.homebanking.dtos;

public class LoanRequestDTO {

private Long Id;

private String loanType;
private Double amount;
private Integer payments;
private String  destinationAccount;



    public LoanRequestDTO() {
    }


    public Long getId() {
        return Id;
    }

    public String getLoanType() {
        return loanType;
    }

    public Double getAmount() {
        return amount;
    }

    public Integer getPayments() {
        return payments;
    }

    public String getDestinationAccount() {
        return destinationAccount;
    }
}
