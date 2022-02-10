package com.techelevator;

import java.math.BigDecimal;

public class Drink extends Item {
    public static int beverageCounter;

    public Drink(String name, BigDecimal price, String slot) {
        super(name, price, slot);
        beverageCounter++;
    }

    @Override
    public String getSound() {
        return "Glug Glug, Yum!";
    }
}
