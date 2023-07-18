package com.mindhub.homebanking;

import com.mindhub.homebanking.enums.CardColor;
import com.mindhub.homebanking.enums.CardType;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.enums.TransactionType;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {

		SpringApplication.run(HomebankingApplication.class, args);

	}
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountsRepository accountsRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository, CardRepository cardRepository) {
		return (args) -> {

			Client client1 = new Client("Melba", "Morel", "melba@mindhub.com", passwordEncoder.encode("melba"));
			Client client2 = new Client("Mauri", "Echaniz", "echanizmauricio@gmail.com", passwordEncoder.encode("mauri"));
			Client admin = new Client("admin", "admin", "admin@admin", passwordEncoder.encode("admin"));
			clientRepository.save(client1);
			clientRepository.save(client2);
			clientRepository.save(admin);

//--------------------------------------------------------------------------------------------

			Account account1 = new Account("VIN-001", LocalDate.now(), 2000.0,false);
			Account account2 = new Account("VIN-002", LocalDate.now().plusDays(1), -16000.0,false);
			Account account3 = new Account("VIN-003", LocalDate.now().plusDays(1), 9000.0,false);
			Account account4 = new Account("VIN-004", LocalDate.now().plusDays(1), 5500.0, false);
			client1.addAccounts(account1);
			client1.addAccounts(account2);
			client2.addAccounts(account3);
			client2.addAccounts(account4);
			accountsRepository.save(account1);
			accountsRepository.save(account2);
			accountsRepository.save(account3);
			accountsRepository.save(account4);

//------------------------------------------------------------------------------------------------

			Transaction transaction1 = new Transaction(TransactionType.DEBIT, -4000.0, "BTC Buy", LocalDateTime.now(),false);
			Transaction transaction2 = new Transaction(TransactionType.CREDIT, 6000.0, "ETH Sell", LocalDateTime.now(),false);
			Transaction transaction3 = new Transaction(TransactionType.DEBIT, -20000.0, "Loan", LocalDateTime.now(),false);
			Transaction transaction4 = new Transaction(TransactionType.CREDIT, 4000.0, "USDT Earn", LocalDateTime.now(),false);
			Transaction transaction5 = new Transaction(TransactionType.DEBIT, -1000.0, "BTC Sell", LocalDateTime.now(),false);
			Transaction transaction6 = new Transaction(TransactionType.CREDIT, 10000.0, "USDT Earn", LocalDateTime.now(),false);
			Transaction transaction7 = new Transaction(TransactionType.DEBIT, -1500.0, "LTC Buy", LocalDateTime.now(),false);
			Transaction transaction8 = new Transaction(TransactionType.CREDIT, 7000.0, "USDT Earn", LocalDateTime.now(),false);


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

//-----------------------------------------------------------------------------------------

			Loan loan1 = new Loan("Mortgage", 500000.0, Set.of(12, 24, 36, 48, 60), 0.2);
			Loan loan2 = new Loan("Personal", 100000.0, Set.of(6, 12, 24), 0.3);
			Loan loan3 = new Loan("Automotive", 300000.0, Set.of(6, 12, 24, 36), 0.1);

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

			ClientLoan clientLoan3 = new ClientLoan(100000.0, 24);
			client2.addClientLoans(clientLoan3);
			loan1.addClientLoans(clientLoan3);
			clientLoanRepository.save(clientLoan3);

			ClientLoan clientLoan4 = new ClientLoan(200000.0, 36);
			client2.addClientLoans(clientLoan4);
			loan3.addClientLoans(clientLoan4);
			clientLoanRepository.save(clientLoan4);

//------------------------------------------------------------------------------------------------

			Card card1 = new Card("Melba Morel", CardType.DEBIT, CardColor.GOLD,"2047 4569 8025 9865",255,LocalDate.now(),LocalDate.now().plusYears(5), true);
			client1.addClientCards(card1);
			cardRepository.save(card1);

			Card card2 = new Card("Melba Morel", CardType.CREDIT, CardColor.TITANIUM," 4569 9865 8025 2047",758,LocalDate.now(),LocalDate.now().plusYears(5),true);
			client1.addClientCards(card2);
			cardRepository.save(card2);

			Card card3 = new Card("Mauricio Echaniz", CardType.CREDIT, CardColor.SILVER," 4569 8526 8749 6587",377,LocalDate.now(),LocalDate.now().minusYears(1),true);
			client2.addClientCards(card3);
			cardRepository.save(card3);
		};
	}
}
