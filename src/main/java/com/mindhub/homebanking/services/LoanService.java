package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.Loan;

import java.util.List;
import java.util.Set;

public interface LoanService {

    Loan findByName(String name);

    Set<LoanDTO> getAll();

    List<Loan> getAllLoans();

    void saveLoan(Loan loan);

}
