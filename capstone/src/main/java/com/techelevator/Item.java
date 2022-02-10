package com.techelevator;

import java.math.BigDecimal;

public class Item {
    //instance variables
    private String name;
    private BigDecimal price;
    private String slot;
    private int numAvailable;

    //contructors
    public Item() {


    }

    public Item(String name, BigDecimal price, String slot) {
        this.name=name;
        this.price=price;
        this.slot=slot;
        this.numAvailable=5;

    }
    //methods



    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getSlot() {
        return slot;
    }

    public int getNumAvailable() {
        return numAvailable;
    }
}
