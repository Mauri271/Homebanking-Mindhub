package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountsRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {

		SpringApplication.run(HomebankingApplication.class, args);

	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountsRepository accountsRepository, TransactionRepository transactionRepository) {
		return (args) -> {

			Client client1 = new Client("Melba", "Morel", "melba@mindhub.com");
			Client client2 = new Client("Mauri", "Echaniz", "echanizmauricio@gmail.com");
			clientRepository.save(client1);
			clientRepository.save(client2);




			Account account1 = new Account("VIN001", LocalDate.now(), 5000.0);
			Account account2 = new Account("VIN002", LocalDate.now().plusDays(1), 7500.0);
			Account account3 = new Account("VIN003", LocalDate.now().plusDays(1), 300.0);
			Account account4 = new Account("VIN004", LocalDate.now().plusDays(1), 6000.0);
			client1.addAccounts(account1);
			client1.addAccounts(account2);
			client2.addAccounts(account3);
			client2.addAccounts(account4);
			accountsRepository.save(account1);
			accountsRepository.save(account2);
			accountsRepository.save(account3);
			accountsRepository.save(account4);

			Transaction transaction1 = new Transaction(TransactionType.DEBIT, -4000.0, "BTC Buy", LocalDateTime.now(),account1);
			Transaction transaction2 = new Transaction(TransactionType.CREDIT, 6000.0, "ETH Sell", LocalDateTime.now(),account1);
			Transaction transaction3 = new Transaction(TransactionType.DEBIT, -20000.0, "Loan", LocalDateTime.now(),account2);
			Transaction transaction4 = new Transaction(TransactionType.CREDIT, 4000.0, "USDT Earn", LocalDateTime.now(),account2);
			Transaction transaction5 = new Transaction(TransactionType.DEBIT, -780.0, "BTC Sell", LocalDateTime.now(),account3);
			Transaction transaction6 = new Transaction(TransactionType.CREDIT, 10000.0, "USDT Earn", LocalDateTime.now(),account3);
			Transaction transaction7 = new Transaction(TransactionType.DEBIT, -1500.0, "LTC Buy", LocalDateTime.now(),account4);
			Transaction transaction8 = new Transaction(TransactionType.CREDIT, 7000.0, "USDT Earn", LocalDateTime.now(),account4);



			account1.addTransactions(transaction1);
			account1.addTransactions(transaction2);
			account2.addTransactions(transaction3);
			account2.addTransactions(transaction4);
			account3.addTransactions(transaction5);
			account3.addTransactions(transaction6);
			account4.addTransactions(transaction7);
			account4.addTransactions(transaction8);

			transactionRepository.save(transaction1);
			transactionRepository.save(transaction2);
			transactionRepository.save(transaction3);
			transactionRepository.save(transaction4);
			transactionRepository.save(transaction5);
			transactionRepository.save(transaction6);
			transactionRepository.save(transaction7);
			transactionRepository.save(transaction8);


		};
	}
}
