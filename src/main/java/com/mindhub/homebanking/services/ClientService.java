package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;
import java.util.Set;


public interface ClientService {


    Set<ClientDTO> getAllClientsDTO();

    ClientDTO getOneCLientDTO(Long id);

    Client findClientByEmail(String email);

    void saveClient(Client client);
}
