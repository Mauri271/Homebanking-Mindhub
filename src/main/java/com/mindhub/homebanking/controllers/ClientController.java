package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.enums.AccountType;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientLoanService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Random;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountService accountService;


    @GetMapping("/clients")
    public Set<ClientDTO> getAll() {
    return clientService.getAllClientsDTO();

    }

    @GetMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id) {
       return clientService.getOneCLientDTO(id);
    }


    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping(path = "/clients")
    public ResponseEntity<Object> register(

            @RequestParam String firstName, @RequestParam String lastName,

            @RequestParam String email, @RequestParam String password) {


        if (firstName.isBlank() || lastName.isBlank() || email.isBlank() || password.isBlank()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if (clientService.findClientByEmail(email) != null) {
            return new ResponseEntity<>("Email already in use", HttpStatus.FORBIDDEN);
        } else {
            Client client = new Client(firstName, lastName, email, passwordEncoder.encode(password));
            clientService.saveClient(client);
            String randomNum;

            do {
                Random random = new Random();
                randomNum = "VIN-" + random.nextInt(90000000);
            } while (accountService.findByNumber(randomNum) != null);

            Account account = new Account(randomNum, LocalDate.now(), 0.0,false, AccountType.CHECKING);
            client.addAccounts(account);
            accountService.saveAccount(account);
            return new ResponseEntity<>(HttpStatus.CREATED);

        }
        }


        @GetMapping("/clients/current")

        public ClientDTO getAuthenticatedClient(Authentication authentication) {

        return new ClientDTO(clientService.findClientByEmail(authentication.getName()));

        }




    }
