package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.TransferDTO;
import com.mindhub.homebanking.enums.TransactionType;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.repositories.AccountsRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Set;

@RestController
@RequestMapping
        ("/api")
public class TransactionController {
    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @Transactional
    @PostMapping(path = "/transactions")
    public ResponseEntity<Object> transactionMaker(Authentication authentication, @RequestBody TransferDTO transferDTO) {

        Client client = (clientService.findClientByEmail(authentication.getName()));
        Set<Account> clientAccount = (client.getAccounts());
        Account originAccount = (accountService.findByNumber(transferDTO.getOriginAccount()));
        Account destinationAccount = (accountService.findByNumber(transferDTO.getDestinationAccount()));
        double transactionAmount = (transferDTO.getAmount());
        String description = (transferDTO.getDescription());


        if (originAccount == null) {
            return new ResponseEntity<>("Wrong origin account, please try again", HttpStatus.FORBIDDEN);
        }

        if( destinationAccount == null){
            return new ResponseEntity<>("Wrong destination account, please try again", HttpStatus.FORBIDDEN);
        }

        if (transactionAmount <= 0.0) {
            return new ResponseEntity<>("The amount most be greater than 0", HttpStatus.FORBIDDEN);
        }

        if (description.isBlank()) {
            return new ResponseEntity<>("You most set a description for this transaction", HttpStatus.FORBIDDEN);
        }
        if(!clientAccount.stream().anyMatch(a -> a.getNumber().equals(originAccount.getNumber()) )){
            return new ResponseEntity<>("This account doesn't belong to you", HttpStatus.FORBIDDEN);
        }

        if (originAccount.getNumber().equals(destinationAccount.getNumber())) {
            return new ResponseEntity<>("You can't transfer money to the same account", HttpStatus.FORBIDDEN);
        }

        if (originAccount.getBalance() < transferDTO.getAmount()) {
            return new ResponseEntity<>("Insufficient funds", HttpStatus.FORBIDDEN);
        }

            Transaction originAccountTransaction = new Transaction(TransactionType.DEBIT, -transactionAmount, description, LocalDateTime.now(),false);
            Transaction destinationAccountTransaction = new Transaction(TransactionType.CREDIT, transactionAmount, description, LocalDateTime.now(),false);

            originAccount.addTransactions(originAccountTransaction);
            originAccount.setBalance(originAccount.getBalance() - transactionAmount);

            destinationAccount.addTransactions(destinationAccountTransaction);
            destinationAccount.setBalance(destinationAccount.getBalance() + transactionAmount);

            transactionService.saveTransaction(originAccountTransaction);
            transactionService.saveTransaction(destinationAccountTransaction);

            return new ResponseEntity<>("Transaction made", HttpStatus.CREATED);
        }

}
