//package com.mindhub.homebanking;
//
//import com.mindhub.homebanking.models.*;
//import com.mindhub.homebanking.repositories.*;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.*;
//
//import java.util.List;
//import java.util.Set;
//
//import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;
//
//@DataJpaTest
//
//@AutoConfigureTestDatabase(replace = NONE)
//public class RepositoriesTest  {
//
//    @Autowired
//    private AccountsRepository accountsRepository;
//
//    @Autowired
//    private CardRepository cardRepository;
//
//
//    @Autowired
//    private ClientRepository clientRepository;
//
//    @Autowired
//    private TransactionRepository transactionRepository;
//
//    @Autowired
//    private LoanRepository loanRepository;
//
//
//    @Test
//
//    public void existLoans(){
//
//        List<Loan> loans = loanRepository.findAll();
//
//        assertThat(loans,is(not(empty())));
//
//    }
//
//
//
//    @Test
//
//    public void existPersonalLoan(){
//
//        List<Loan> loans = loanRepository.findAll();
//
//        assertThat(loans, hasItem(hasProperty("name", is("Personal"))));
//
//    }
//
//    @Test
//
//    public void existClients(){
//
//        List<Client> clients = clientRepository.findAll();
//
//        assertThat(clients,is(not(empty())));
//
//    }
//
//    @Test
//
//    public void existAdminClient(){
//
//        List<Client> clients = clientRepository.findAll();
//
//        assertThat(clients, hasItem(hasProperty("email", is("admin@admin"))));
//
//    }
//
//    @Test
//
//    public void existAccounts(){
//
//        List<Account> accounts = accountsRepository.findAll();
//
//        assertThat(accounts,is(not(empty())));
//
//    }
//
//    @Test
//
//    public void allAccountsAreAssociatedToClient(){
//        List<Account> accounts = accountsRepository.findAll();
//
//        for (Account account: accounts){
//          Set<Account> clientsAccounts = account.getOwner().getAccounts();
//            assertThat(clientsAccounts,is(not(empty())));
//        }
//
//    }
//
//
//
//    @Test
//
//    public void existCards(){
//
//        List<Card> cards = cardRepository.findAll();
//
//        assertThat(cards,is(not(empty())));
//
//    }
//
//    @Test
//
//    public void eachClientHasMaximumSixCards() {
//
//        List<Card> cards = cardRepository.findAll();
//
//        for (Card card : cards) {
//            Set<Card> clientsCards = card.getOwner().getClientCards();
//            assertThat(clientsCards.size(), lessThanOrEqualTo(6));
//
//        }
//    }
//    @Test
//
//    public void existTransaction(){
//
//        List<Transaction> transactions = transactionRepository.findAll();
//
//        assertThat(transactions,is(not(empty())));
//
//    }
//}
//
//
