package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Random;
import java.util.Set;

import static com.mindhub.homebanking.Utils.Utilities.getRandomNum;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private TransactionRepository transactionRepository;




    @GetMapping("/accounts")
    public Set<AccountDTO> getAccounts() {
        return accountService.getAllAccounts();

    }

    @GetMapping("/accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id) {
        return accountService.getOneAccount(id);
    }


    @PostMapping(path = "/clients/current/accounts")
    public ResponseEntity<Object> accountCreator(Authentication authentication){

         String randomNum;

          do {
              Random random = new Random();
              randomNum = getRandomNum(random);
          } while (accountService.findByNumber(randomNum) != null);


        Client client = clientService.findClientByEmail(authentication.getName());
        Set<Account> newAccounts = (client.getAccounts());
        if (newAccounts.size() == 3) {
            return new ResponseEntity<>("Max amount of accounts reached", HttpStatus.FORBIDDEN);
        } else {
            Account account = new Account(randomNum, LocalDate.now(),0.0,false);
            client.addAccounts(account);
           accountService.saveAccount(account);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    @PatchMapping(path="/clients/current/accounts/{id}")
    public ResponseEntity <Object> deleteAccount(Authentication authentication, @PathVariable Long id){

        Client client = clientService.findClientByEmail(authentication.getName());
        Account account = accountService.findById(id);
        Set<Transaction> transactions = account.getTransactions();

        if (client == null) {
            return new ResponseEntity<>("Client not found", HttpStatus.FORBIDDEN);
        }

        if(account == null){
            return new ResponseEntity<>("Te equivocaste macho", HttpStatus.FORBIDDEN);
        }

        if(!account.getId().equals(id)){
            return new ResponseEntity<>("Wrong card", HttpStatus.FORBIDDEN);
        } else {

            account.setDeleted(true);

            transactions.forEach( c -> c.setHidden(true));

            transactionRepository.saveAll(transactions);


            accountService.saveAccount(account);
        }
        return new ResponseEntity<>("deleted", HttpStatus.ACCEPTED);
    }


}
