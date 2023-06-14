package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Set;
import static java.util.stream.Collectors.toSet;

@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;

    @RequestMapping("/clients")
        public Set<ClientDTO> getAll() {
        return clientRepository.findAll().stream().map(ClientDTO::new).collect(toSet());

    }

    @RequestMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id) {

        return clientRepository.findById(id).map(ClientDTO::new).orElse(null);
    }

}
