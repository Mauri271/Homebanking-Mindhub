package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;

import java.util.Set;

public interface AccountService {

   void saveAccount(Account account);

   Set<AccountDTO> getAllAccounts();

   AccountDTO getOneAccount(Long id);

   Account findById(Long id);

   Account findByNumber(String number);

}
