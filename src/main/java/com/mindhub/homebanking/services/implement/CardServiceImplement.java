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
    public void saveCard(Card card) {
    cardRepository.save(card);
    }

    @Override
    public boolean existsByOwnerAndCardTypeAndCardColor(Client client, CardType cardType, CardColor cardColor) {
        return cardRepository.existsByOwnerAndCardTypeAndCardColor(client, cardType,cardColor);
    }
}
