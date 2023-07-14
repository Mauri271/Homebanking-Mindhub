package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.repositories.LoanRepository;
import com.mindhub.homebanking.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Service
public class LoanServiceImplement implements LoanService {

    @Autowired
    private LoanRepository loanRepository;
    @Override
    public Loan findByName(String name) {
        return loanRepository.findByName(name);
    }


    @Override
    public Set<LoanDTO> getAll() {
        return loanRepository.findAll()
                .stream()
                .map(LoanDTO::new)
                .collect(toSet());
    }
}
