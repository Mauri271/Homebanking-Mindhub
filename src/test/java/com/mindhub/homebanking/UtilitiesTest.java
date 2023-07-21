package com.mindhub.homebanking;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Random;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
public class UtilitiesTest {

    @Test
    public void checkAccountRandomNum(){
        Random random = new Random();
        int randomAccount =  random.nextInt(90000000);

        String accountNumber = "VIN-" + randomAccount;

        assertThat(String.valueOf(accountNumber).length(),is(12));
    }

    @Test
    public void checkCvv(){
        Random random = new Random();
        int randomCvv =  random.nextInt(999);


        assertThat(String.valueOf(randomCvv).length(),is(3));
    }

    @Test
    public void checkCardRandomNum(){
        Random random = new Random();
        int randomCardNumber =  random.nextInt(9999);

        String cardNumber = randomCardNumber + " " + randomCardNumber + " " + randomCardNumber + " " + randomCardNumber;

        assertThat(String.valueOf(cardNumber).length(),is(19));
    }
}
