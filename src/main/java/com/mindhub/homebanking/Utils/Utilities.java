package com.mindhub.homebanking.Utils;

import java.util.Random;
public final class Utilities {

    public static String getRandomNum(Random random) {
        return "VIN-" + random.nextInt(90000000);
    }

    public static int getRandomcvvNumber(Random randomcvv) {
        return randomcvv.nextInt(999);
    }

    public static String getRandomCardNumber(Random random) {
        return random.nextInt(9999) + " " + random.nextInt(9999) + " " + random.nextInt(9999) + " " + random.nextInt(9999);
    }


}
