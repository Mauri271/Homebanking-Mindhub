package com.mindhub.homebanking.controllers;
import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.mindhub.homebanking.enums.CardColor;
import com.mindhub.homebanking.enums.CardType;
import java.time.LocalDate;
import java.util.Random;
import java.util.Set;
import static com.mindhub.homebanking.Utils.Utilities.getRandomCardNumber;
import static com.mindhub.homebanking.Utils.Utilities.getRandomcvvNumber;

@RestController
@RequestMapping
        ("/api")
public class CardController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private CardService cardService;



    @PostMapping(path = "/clients/current/cards")
    public ResponseEntity<Object> cardCreator(Authentication authentication, @RequestParam CardType cardType, @RequestParam CardColor cardColor) {

        Client client = (clientService.findClientByEmail(authentication.getName()));
        if (cardType == null || cardColor == null) {
            return new ResponseEntity<>("Missing data", HttpStatus.NO_CONTENT);
        }

            int randomcvvNumber;
            Random randomcvv = new Random();
            randomcvvNumber = getRandomcvvNumber(randomcvv);
            String randomCardNumber;
            do {
                Random random = new Random();
                randomCardNumber = getRandomCardNumber(random);
            } while (cardService.findCardByNumber(randomCardNumber) != null);


            Card card = new Card(client.getFirstName() + " " + client.getLastName(), cardType, cardColor, randomCardNumber, randomcvvNumber, LocalDate.now(), LocalDate.now().plusYears(5), true);

            if (cardService.existsByOwnerAndCardTypeAndCardColorAndIsActive(client, cardType, cardColor,true)) {
                return new ResponseEntity<>("You already own that card", HttpStatus.FORBIDDEN);
            } else {
                client.addClientCards(card);
                cardService.saveCard(card);
                return new ResponseEntity<>("Card successfully created",HttpStatus.CREATED);
            }

        }

        @PatchMapping("/clients/current/cards/{id}")
        public ResponseEntity<Object> deleteCard(Authentication authentication, @PathVariable Long id) {

            Client client = clientService.findClientByEmail(authentication.getName());
            Card card = cardService.findById(id);

            if (client == null) {
                return new ResponseEntity<>("Client not found", HttpStatus.FORBIDDEN);
            }

            if (card == null) {
                return new ResponseEntity<>("Wrong request", HttpStatus.FORBIDDEN);
            }

            if (!card.getId().equals(id)) {
                return new ResponseEntity<>("Wrong card", HttpStatus.FORBIDDEN);
            } else {
                card.setActive(false);
                cardService.saveCard(card);

            }

        return new ResponseEntity<>("deleted", HttpStatus.ACCEPTED);

        }


}

