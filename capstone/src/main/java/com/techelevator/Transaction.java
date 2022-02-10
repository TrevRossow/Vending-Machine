package com.techelevator;

import java.math.BigDecimal;

public class Transaction {

    private BigDecimal balance= new BigDecimal("0.00");

    public Transaction(){

    }
    public void deposit(BigDecimal moneyIn){
        balance.add(moneyIn);

    }
    public void withdrawl(BigDecimal moneyOut){
        balance.subtract(moneyOut);


    }

    public BigDecimal getBalance() {
        return balance;
    }
}
