package com.techelevator;

import com.techelevator.view.Menu;

import java.math.BigDecimal;
import java.util.Map;

public class Transaction {


    private BigDecimal balance= new BigDecimal("0.00");

    public Transaction(){

    }
    public void deposit(BigDecimal moneyIn){
        this.balance = balance.add(moneyIn);

    }
    public void withdraw(BigDecimal moneyOut){
        this.balance = balance.subtract(moneyOut);


    }

    public BigDecimal getBalance() {
        return this.balance;
    }


}
