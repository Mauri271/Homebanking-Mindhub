package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountsRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Random;
import java.util.Set;
import static java.util.stream.Collectors.toSet;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private AccountsRepository accountsRepository;



    @Autowired
    private ClientRepository clientRepository;




    @RequestMapping("/accounts")
    public Set<AccountDTO> getAccounts() {
        return accountsRepository.findAll().stream().map(AccountDTO::new).collect(toSet());

    }

    @RequestMapping("/accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id) {
        return accountsRepository.findById(id).map(AccountDTO::new).orElse(null);
    }


    @RequestMapping(path = "clients/current/accounts", method = RequestMethod.POST)
    public ResponseEntity<Object> accountCreator(Authentication authentication){

         String randomNum;

          do {
              Random random = new Random();
              randomNum = "VIN-" + random.nextInt(90000000) ;
          } while (accountsRepository.findByNumber(randomNum) != null);


        Client client = clientRepository.findByEmail(authentication.getName());
        Set<Account> newAccounts = (client.getAccounts());
        if (newAccounts.size() == 3) {
            return new ResponseEntity<>("Max amount of accounts reached", HttpStatus.FORBIDDEN);
        } else {
            Account account = new Account(randomNum, LocalDate.now(),0.0);
            client.addAccounts(account);
            accountsRepository.save(account);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }
}
