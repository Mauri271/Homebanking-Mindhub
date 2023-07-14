package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.repositories.AccountsRepository;
import com.mindhub.homebanking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Set;
import static java.util.stream.Collectors.toSet;

@Service
public class AccountServiceImplement implements AccountService {

@Autowired
    private AccountsRepository accountsRepository;
    @Override
    public void saveAccount(Account account) {
        accountsRepository.save(account);
    }

    @Override
    public Set<AccountDTO> getAllAccounts() {
        return accountsRepository.findAll().stream().map(AccountDTO::new).collect(toSet());
    }

    @Override
    public AccountDTO getOneAccount(Long id) {
        return accountsRepository.findById(id).map(AccountDTO::new).orElse(null);
    }

    @Override
    public Account findByNumber(String number) {
       return accountsRepository.findByNumber(number);
    }
}
