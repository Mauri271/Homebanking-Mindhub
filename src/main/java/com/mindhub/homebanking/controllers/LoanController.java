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
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@RestController
@RequestMapping("/api")
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
        List<Loan> allLoans = loanService.getAllLoans();

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
        }


        else {

            loanRequestDTO.getLoanType().equals(allLoans.stream().map(l -> l.getName()));
                double interest = loanRequestDTO.getAmount() + loanRequestDTO.getAmount() * loan.getInterest();
                ClientLoan requestedLoan = new ClientLoan(interest, loanRequestDTO.getPayments());


            Transaction loanTransaction = new Transaction(TransactionType.CREDIT, loanRequestDTO.getAmount(), loanRequestDTO.getLoanType() + " " + "loan approved", LocalDateTime.now(), false);

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


    @PostMapping(path="/loans/adminLoans")
    public ResponseEntity<Object> createNewLoan(Authentication authentication, @RequestBody LoanDTO loanDTO){

        Client client = clientService.findClientByEmail(authentication.getName());
        Loan loan = new Loan(loanDTO.getName(), loanDTO.getMaxAmount(), loanDTO.getPayments(), loanDTO.getInterest());

        if(client == null){
            return new ResponseEntity<>("Vos no sos admin pibe", HttpStatus.FORBIDDEN);
        }

        if(loanDTO.getName().equals("") || loanDTO.getMaxAmount() <= 0 || loanDTO.getInterest() <= 0 || loanDTO.getPayments() == null){
            new ResponseEntity<>("ta todo mal", HttpStatus.FORBIDDEN);
        }

        if (loanDTO.getName().equals("")){
            return new ResponseEntity<>("Loan type can't be empty", HttpStatus.FORBIDDEN);
        }

        if(loanDTO.getMaxAmount() <= 0){
            return new ResponseEntity<>("Loan amount can't be 0 or less", HttpStatus.FORBIDDEN);

        }

        if (loanDTO.getInterest() <= 0){
            return new ResponseEntity<>("Loan interest can't be 0 or less", HttpStatus.FORBIDDEN);

        }
        else{
            loanService.saveLoan(loan);
            return new ResponseEntity<>("Loan created", HttpStatus.CREATED);

        }
    }

    @GetMapping("/loans")
    public Set<LoanDTO> getAll() {
        return  loanService.getAll();
    }
}
