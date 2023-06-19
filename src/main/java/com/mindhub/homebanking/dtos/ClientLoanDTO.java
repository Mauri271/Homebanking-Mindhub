package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.ClientLoan;

public class ClientLoanDTO {

    private Long id;
    private Long loanId;
    private String name;
    private Double Amount;
    private Integer Payments;
    private Client owner;

    public ClientLoanDTO(ClientLoan clientLoan) {
        id = clientLoan.getId();
        loanId = clientLoan.getLoan().getId();
        name = clientLoan.getLoan().getName();
        Amount = clientLoan.getAmount();
        Payments = clientLoan.getPayments();
        owner = clientLoan.getOwner();
    }


    public Long getId() {
        return id;
    }

    public Long getLoanId() {
        return loanId;
    }

    public String getName() {
        return name;
    }

    public Double getAmount() {
        return Amount;
    }

    public Integer getPayments() {
        return Payments;
    }

    public Client getOwner() {
        return owner;
    }


}
