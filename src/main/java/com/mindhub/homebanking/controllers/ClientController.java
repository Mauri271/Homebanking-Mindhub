package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountsRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.Random;
import java.util.Set;
import static java.util.stream.Collectors.toSet;

@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccountsRepository accountsRepository;

    @RequestMapping("/clients")
    public Set<ClientDTO> getAll() {
        return clientRepository.findAll()
                .stream()
                .map(ClientDTO::new)
                .collect(toSet());

    }

    @RequestMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id) {
        return new ClientDTO(clientRepository.findById(id).orElse(null));
    }


    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(path = "/clients", method = RequestMethod.POST)
    public ResponseEntity<Object> register(

            @RequestParam String firstName, @RequestParam String lastName,

            @RequestParam String email, @RequestParam String password) {


        if (firstName.isBlank() || lastName.isBlank() || email.isBlank() || password.isBlank()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if (clientRepository.findByEmail(email) != null) {
            return new ResponseEntity<>("Email already in use", HttpStatus.FORBIDDEN);
        } else {
            Client client = clientRepository.save(new Client(firstName, lastName, email, passwordEncoder.encode(password)));

            String randomNum;

            do {
                Random random = new Random();
                randomNum = "VIN-" + random.nextInt(90000000);
            } while (accountsRepository.findByNumber(randomNum) != null);

            Account account = new Account(randomNum, LocalDate.now(), 0.0);
            client.addAccounts(account);
            accountsRepository.save(account);
            return new ResponseEntity<>(HttpStatus.CREATED);

        }
        }


        @RequestMapping("/clients/current")

        public ClientDTO getAuthenticatedClient(Authentication authentication){

            return new ClientDTO(clientRepository.findByEmail(authentication.getName()));

        }

    }
