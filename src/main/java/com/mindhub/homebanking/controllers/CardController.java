package com.mindhub.homebanking.controllers;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.services.ClientService;
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

@RestController
@RequestMapping
        ("/api")
public class CardController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private CardService cardService;



    @RequestMapping(path = "/clients/current/cards", method = RequestMethod.POST)
    public ResponseEntity<Object> cardCreator(Authentication authentication, @RequestParam CardType cardType, @RequestParam CardColor cardColor) {

        Client client = (clientService.findClientByEmail(authentication.getName()));
        if (cardType == null || cardColor == null) {
            return new ResponseEntity<>("Missing data", HttpStatus.NO_CONTENT);
        }

            int randomcvvNumber;
            Random randomcvv = new Random();
            randomcvvNumber = randomcvv.nextInt(999);
            String randomCardNumber;
            do {
                Random random = new Random();
                randomCardNumber = random.nextInt(9999) + " " + random.nextInt(9999) + " " + random.nextInt(9999) + " " + random.nextInt(9999);
            } while (cardService.findCardByNumber(randomCardNumber) != null);


            Card card = new Card(client.getFirstName() + " " + client.getLastName(), cardType, cardColor, randomCardNumber, randomcvvNumber, LocalDate.now(), LocalDate.now().plusYears(5));

            if (cardService.existsByOwnerAndCardTypeAndCardColor(client, cardType, cardColor)) {
                return new ResponseEntity<>("You already own that card", HttpStatus.FORBIDDEN);
            } else {
                client.addClientCards(card);
                cardService.saveCard(card);
                return new ResponseEntity<>("Card successfully created",HttpStatus.CREATED);
            }



        }


    }

