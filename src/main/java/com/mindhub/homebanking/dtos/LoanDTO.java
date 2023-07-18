package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Loan;

import java.util.HashSet;
import java.util.Set;


public class LoanDTO {
    private String name;
    private Double maxAmount;

    private Double interest;
    private Set<Integer> payments = new HashSet<>();


    public LoanDTO(Loan loan) {
        this.name = loan.getName();
        this.maxAmount = loan.getMaxAmount();
        this.payments = loan.getPayments();
        this.interest = loan.getInterest();
    }

    public String getName() {
        return name;
    }

    public Double getMaxAmount() {
        return maxAmount;
    }

    public Set<Integer> getPayments() {
        return payments;
    }

    public Double getInterest() { return interest; }
}
