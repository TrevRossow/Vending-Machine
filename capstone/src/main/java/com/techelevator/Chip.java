package com.techelevator;

import java.math.BigDecimal;

public class Chip extends Item {

    public static int chipsCounter;

    public Chip(String name, BigDecimal price, String slot) {
        super(name, price, slot);
        chipsCounter++;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String getSound() {
        return "Crunch Crunch, Yum!";
    }
}
