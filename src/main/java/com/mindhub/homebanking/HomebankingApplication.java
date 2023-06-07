package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Accounts;
import com.mindhub.homebanking.repositories.AccountsRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.models.Client;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


import java.time.LocalDate;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {

		SpringApplication.run(HomebankingApplication.class, args);

	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountsRepository accountsRepository) {
		return (args) -> {

			Client client1 = new Client("Melba", "Morel","melba@mindhub.com");
			clientRepository.save(client1);
			Client client2 = new Client("Mauri", "Echaniz","echanizmauricio@gmail.com");
			clientRepository.save(client2);


			Accounts account1 = new Accounts("VIN001", LocalDate.now(),5000.0, client1);
			Accounts account2 = new Accounts("VIN002", LocalDate.now().plusDays(1),7500.0,client1);

			Accounts account3 = new Accounts("VIN003", LocalDate.now().plusDays(1),300.0,client2);
			Accounts account4 = new Accounts("VIN004", LocalDate.now().plusDays(1),6000.0,client2);


			accountsRepository.save(account1);
			accountsRepository.save(account2);

			accountsRepository.save(account3);
			accountsRepository.save(account4);


		};
	}
}
