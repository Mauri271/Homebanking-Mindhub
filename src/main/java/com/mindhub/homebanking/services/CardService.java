package com.mindhub.homebanking.services;
import com.mindhub.homebanking.enums.CardColor;
import com.mindhub.homebanking.enums.CardType;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;

public interface CardService {

    Card findCardByNumber(String number);

    Card findById(Long id);

    void saveCard(Card card);

    boolean existsByOwnerAndCardTypeAndCardColorAndIsActive(Client client, CardType cardType, CardColor cardColor, Boolean isActive );
}
