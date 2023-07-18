package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.enums.CardColor;
import com.mindhub.homebanking.enums.CardType;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CardRepository extends JpaRepository<Card,Long > {

    Card findBycardNumber(String cardNumber);

//    boolean existsByOwnerAndCardTypeAndCardColor(Client client, CardType cardType, CardColor cardColor);

    boolean existsByOwnerAndCardTypeAndCardColorAndIsActive(Client client, CardType cardType, CardColor cardColor, Boolean isActive);
}
