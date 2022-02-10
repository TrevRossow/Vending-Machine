package com.techelevator;

import java.math.BigDecimal;

public class Candy extends Item {
    public static int candyCounter;

    public Candy(String name, BigDecimal price, String slot) {
        super(name, price, slot);
        candyCounter++;
    }
}
