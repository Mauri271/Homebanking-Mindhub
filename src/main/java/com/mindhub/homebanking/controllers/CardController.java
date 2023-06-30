package com.mindhub.homebanking.controllers;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.mindhub.homebanking.enums.CardColor;
import com.mindhub.homebanking.enums.CardType;

import java.time.LocalDate;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping
        ("/api")
public class CardController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CardRepository cardRepository;



    @RequestMapping
            (path = "/clients/current/cards", method = RequestMethod.POST)
    public ResponseEntity<Object> cardCreator(Authentication authentication, @RequestParam CardType cardType, @RequestParam CardColor cardColor) {

        Client client = (clientRepository.findByEmail(authentication.getName()));
        if (cardType == null || cardColor == null) {
            return new ResponseEntity<>("Missing data", HttpStatus.NO_CONTENT);
        }

        Set<Card> clientCards = client.getClientCards();
        Set<CardType> cardTypes = clientCards.stream()
                .map(Card::getCardType)
                .collect(Collectors.toSet());

        if(cardTypes.size() == 3){
            return new ResponseEntity<>("Max amount of cards reached", HttpStatus.FORBIDDEN);

        }else {

            int randomcvvNumber;
            Random randomcvv = new Random();
            randomcvvNumber =  randomcvv.nextInt(999) ;

            String randomCardNumber;
            do {
                Random random = new Random();
                randomCardNumber =  random.nextInt(9999) + " " + random.nextInt(9999) + " " + random.nextInt(9999) + " " + random.nextInt(9999);
            } while (cardRepository.findBycardNumber(randomCardNumber) != null);

            Card card = new Card(client.getFirstName() +" "+ client.getLastName(), cardType, cardColor, randomCardNumber, randomcvvNumber, LocalDate.now(), LocalDate.now().plusYears(5));

            if (cardTypes.contains(card.getCardType()) && clientCards.stream().anyMatch(c -> c.getCardType() == card.getCardType() && c.getCardColor() == card.getCardColor())) {
                return new ResponseEntity<>("Maximum number of colors reached for this card type", HttpStatus.FORBIDDEN);
            } else {
                client.addClientCards(card);
                cardRepository.save(card);
                return new ResponseEntity<>("Card successfully created",HttpStatus.CREATED);
            }




        }


    }
}
