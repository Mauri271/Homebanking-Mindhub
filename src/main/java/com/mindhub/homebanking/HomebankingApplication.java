package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.enums.TransactionType;
import com.mindhub.homebanking.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {

		SpringApplication.run(HomebankingApplication.class, args);

	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountsRepository accountsRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository) {
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

			Transaction transaction1 = new Transaction(TransactionType.DEBIT, -4000.0, "BTC Buy", LocalDateTime.now(), account1);
			Transaction transaction2 = new Transaction(TransactionType.CREDIT, 6000.0, "ETH Sell", LocalDateTime.now(), account1);
			Transaction transaction3 = new Transaction(TransactionType.DEBIT, -20000.0, "Loan", LocalDateTime.now(), account2);
			Transaction transaction4 = new Transaction(TransactionType.CREDIT, 4000.0, "USDT Earn", LocalDateTime.now(), account2);
			Transaction transaction5 = new Transaction(TransactionType.DEBIT, -780.0, "BTC Sell", LocalDateTime.now(), account3);
			Transaction transaction6 = new Transaction(TransactionType.CREDIT, 10000.0, "USDT Earn", LocalDateTime.now(), account3);
			Transaction transaction7 = new Transaction(TransactionType.DEBIT, -1500.0, "LTC Buy", LocalDateTime.now(), account4);
			Transaction transaction8 = new Transaction(TransactionType.CREDIT, 7000.0, "USDT Earn", LocalDateTime.now(), account4);


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

			Loan loan1 = new Loan("Mortgage", 500000.0, Set.of(12, 24, 36, 48, 60));
			Loan loan2 = new Loan("Personal", 100000.0, Set.of(6, 12, 24));
			Loan loan3 = new Loan("Automotive", 300000.0, Set.of(6, 12, 24, 36));

			loanRepository.save(loan1);
			loanRepository.save(loan2);
			loanRepository.save(loan3);

			ClientLoan clientLoan1 = new ClientLoan(400000.0, 60);
			client1.addClientLoans(clientLoan1);
			loan1.addClientLoans(clientLoan1);
			clientLoanRepository.save(clientLoan1);


			ClientLoan clientLoan2 = new ClientLoan(50000.0, 12);
			client1.addClientLoans((clientLoan2));
			loan2.addClientLoans(clientLoan2);
			clientLoanRepository.save(clientLoan2);



			ClientLoan clientLoan3 = new ClientLoan(100000.0,24);
			client2.addClientLoans(clientLoan3);
			loan1.addClientLoans(clientLoan3);
			clientLoanRepository.save(clientLoan3);

			ClientLoan clientLoan4 = new ClientLoan(200000.0, 36);
			client2.addClientLoans(clientLoan4);
			loan3.addClientLoans(clientLoan4);
			clientLoanRepository.save(clientLoan4);





		};
	}
}
