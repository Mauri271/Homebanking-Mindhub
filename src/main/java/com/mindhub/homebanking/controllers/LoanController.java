package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.dtos.LoanRequestDTO;
import com.mindhub.homebanking.enums.TransactionType;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.repositories.*;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@RestController
@RequestMapping
        ("/api")
public class LoanController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientLoanRepository clientLoanRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private LoanService loanService;

    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<Object> clientLoanCreation(Authentication authentication, @RequestBody LoanRequestDTO loanRequestDTO) {

        Client client = clientService.findClientByEmail(authentication.getName());
        Loan loan = loanService.findByName(loanRequestDTO.getLoanType());
        Account account = accountService.findByNumber(loanRequestDTO.getDestinationAccount());

        if (loanRequestDTO.getAmount() <= 0) {
            return new ResponseEntity<>("The amount can't be 0", HttpStatus.FORBIDDEN);
        }

        if (loanRequestDTO.getPayments() == null) {
            return new ResponseEntity<>("The amount of payments can´t be 0", HttpStatus.FORBIDDEN);
        }

        if (loan == null) {
            return new ResponseEntity<>("Loan type doesn't exist", HttpStatus.FORBIDDEN);
        }
        if (loanRequestDTO.getAmount() > loan.getMaxAmount()) {
            return new ResponseEntity<>("The amount can´t be greater than" + " " + loan.getMaxAmount(), HttpStatus.FORBIDDEN);
        }
        if (loanRequestDTO.getAmount() < 5000.0) {
            return new ResponseEntity<>("The amount can´t be smaller than" + " " + "$5000", HttpStatus.FORBIDDEN);
        }

        if (!loan.getPayments().contains(loanRequestDTO.getPayments())) {
            return new ResponseEntity<>(("Payments can´t be:" + " " + loanRequestDTO.getPayments()), HttpStatus.FORBIDDEN);
        }
        if (account == null) {
            return new ResponseEntity<>("The account doesn't exist", HttpStatus.FORBIDDEN);
        } else {
            ClientLoan requestedLoan = new ClientLoan(loanRequestDTO.getAmount() + loanRequestDTO.getAmount() * 0.2, loanRequestDTO.getPayments());
            Transaction loanTransaction = new Transaction(TransactionType.CREDIT, loanRequestDTO.getAmount(), loanRequestDTO.getLoanType() + " " + "loan approved", LocalDateTime.now());

            account.setBalance(account.getBalance() + loanRequestDTO.getAmount());
            client.addClientLoans(requestedLoan);
            client.addAccounts(account);
            loan.addClientLoans(requestedLoan);
            account.addTransactions(loanTransaction);

            clientLoanRepository.save(requestedLoan);
            transactionRepository.save(loanTransaction);
            accountService.saveAccount(account);

            return new ResponseEntity<>("Loan created", HttpStatus.CREATED);
        }
    }

    @RequestMapping("/loans")
    public Set<LoanDTO> getAll() {
        return  loanService.getAll();
    }
}
