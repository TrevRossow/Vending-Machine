package com.techelevator;

import java.math.BigDecimal;

public class Gum extends Item {

    public static int gumCounter;

    public Gum(String name, BigDecimal price, String slot) {
        super(name, price, slot);
        gumCounter++;
    }

    @Override
    public String getSound() {
        return "Chew Chew, Yum!";
    }
}
