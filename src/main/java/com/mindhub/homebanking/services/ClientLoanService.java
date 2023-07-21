package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.ClientLoan;

public interface ClientLoanService {

    ClientLoan findById(Long id);

    void save(ClientLoan clientLoan);
}
