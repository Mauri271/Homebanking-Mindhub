package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.enums.CardColor;
import com.mindhub.homebanking.enums.CardType;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardServiceImplement implements CardService {


    @Autowired
    private CardRepository cardRepository;
    @Override
    public Card findCardByNumber(String number) {
        return cardRepository.findBycardNumber(number);
    }

    @Override
    public Card findById(Long id) {return cardRepository.findById(id).orElse(null);}

    @Override
    public void saveCard(Card card) {
    cardRepository.save(card);
    }

    @Override
    public boolean existsByOwnerAndCardTypeAndCardColorAndIsActive(Client client, CardType cardType, CardColor cardColor, Boolean isActive) {
        return cardRepository.existsByOwnerAndCardTypeAndCardColorAndIsActive(client, cardType,cardColor, isActive);
    }
}
