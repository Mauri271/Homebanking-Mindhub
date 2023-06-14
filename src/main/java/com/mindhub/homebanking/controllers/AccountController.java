package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.repositories.AccountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Set;
import static java.util.stream.Collectors.toSet;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private AccountsRepository accountsRepository;
    @RequestMapping("/accounts")
    public Set<AccountDTO> getAccounts() {
        return accountsRepository.findAll().stream().map(AccountDTO::new).collect(toSet());

    }

    @RequestMapping("/accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id) {
        return accountsRepository.findById(id).map(AccountDTO::new).orElse(null);
    }
}
