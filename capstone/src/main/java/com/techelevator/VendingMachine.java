package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class VendingMachine {

    private static Transaction transaction = new Transaction();
//    private static Map<String, Item> inventoryMap = new HashMap<>();
    private static Map <String, Item> inventoryMap = new HashMap<>();
//    private Map<String, Item> duplicateMap = inventoryMap;




    static Scanner scan = new Scanner(System.in);

//    public static Map<String, Item> getInventoryMap() {
      public Map<String, Item> getInventoryMap() {
        VendingMachine vm = new VendingMachine();
        vm.createItems();
        return inventoryMap;
    }


    public void createItems() {

        try (Scanner inputFile = new Scanner(new File("vendingmachine.csv"))) {

            while (inputFile.hasNextLine()) {
                String line = inputFile.nextLine();
                String[] characteristics = line.split("\\|");
                String slot = characteristics[0];
                String name = characteristics[1];
                BigDecimal price = new BigDecimal(characteristics[2]);
                String item = characteristics[3];


                if (item.equals("Chip")) {
                    Chip chip = new Chip(name, price, slot);
                    inventoryMap.put(slot, chip);

                } else if
                (item.equals("Drink")) {
                    Drink drink = new Drink(name, price, slot);
                    inventoryMap.put(slot, drink);

                } else if
                (item.equals("Candy")) {
                    Candy candy = new Candy(name, price, slot);
                    inventoryMap.put(slot, candy);

                } else if (item.equals("Gum")) {
                    Gum gum = new Gum(name, price, slot);
                    inventoryMap.put(slot, gum);
                }
            }


        } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
        }


    }

    private void feedMoney() {

        System.out.print("Enter money in Whole Dollars >>> ");
        String userInput = scan.nextLine();
        String userInputMoney = userInput + ".00";
        BigDecimal amountDeposited = new BigDecimal(userInputMoney);
        transaction.deposit(amountDeposited);
        System.out.println("Current balance is: $" + transaction.getBalance());

        //TODO - Log Transaction

    }

    private void selectProduct() {
        System.out.print("Enter item code >>> ");
        String userInput = scan.nextLine();



        //Takes one out of inventory
        Item product = inventoryMap.get(userInput);
        product.sellItem();

        //TODO - Print name of product received
        System.out.println("Num Available is " + product.getNumAvailable());

        //Subract price from balance
        transaction.withdrawl(product.getPrice());
        System.out.println("Current balance is " + transaction.getBalance());

        //Print off sound
        product.getSound();
        System.out.println(product.getSound());


        //TODO - Check there's money to buy item


        //TODO - Log transaction


        //TODO - Add to Sales Report

    }

    private void getChange() {
        BigDecimal changeBalance = transaction.getBalance();
        BigDecimal zero = new BigDecimal("0.00");
        int numOfQuarters = 0;
        int numOfDimes = 0;
        int numOfNickels = 0;
        BigDecimal quarter = new BigDecimal("0.25");
        BigDecimal dime = new BigDecimal("0.10");
        BigDecimal nickel = new BigDecimal("0.05");

        System.out.println("Your change is: $" + changeBalance);
        while ((changeBalance.compareTo(zero) > 0)) {

            if (changeBalance.compareTo(quarter) > -1) {
                numOfQuarters++;
                changeBalance = changeBalance.subtract(quarter);
            } else if (changeBalance.compareTo(dime) > -1) {
                numOfDimes++;
                changeBalance = changeBalance.subtract(dime);
            } else if (changeBalance.compareTo(nickel) > -1) {
                numOfNickels++;
                changeBalance = changeBalance.subtract(nickel);

            }
        }
        System.out.format("You are receiving %d quarters, %d dimes, %d nickels", numOfQuarters, numOfDimes, numOfNickels);
        System.out.println("");

        //TODO - Log Transaction

    }
    public void purchase() {
        try {
            System.out.println("Current Money Provided: $" + transaction.getBalance());
            System.out.println("");
            System.out.println("(1) Feed Money");
            System.out.println("(2) Select Product");
            System.out.println("(3) Finish Transaction");
            System.out.println("");

            System.out.print("Please choose an option >>> ");
            String userInput = scan.nextLine();

            if (userInput.substring(0, 1).equals("1")) {
                //vendingMachine.feedMoney();
                feedMoney();
            } else if (userInput.substring(0, 1).equals("2")) {
                //vendingMachine.selectProduct();
                selectProduct();
            } else if (userInput.substring(0, 1).equals("3")) {
                //vendingMachine.getChange();
                getChange();
            } else {
                throw new IllegalArgumentException();

            }

        } catch (IllegalArgumentException e) {
            System.out.println("Enter a valid choice!");

        }
    }

    public void displayMachineItems() {

        Set<String> inventoryKeys = inventoryMap.keySet();
        for (String inventoryKey : inventoryKeys) {
            String slot = inventoryMap.get(inventoryKey).getSlot();
            String name = inventoryMap.get(inventoryKey).getName();
            BigDecimal price = inventoryMap.get(inventoryKey).getPrice();
            int numAvailable = inventoryMap.get(inventoryKey).getNumAvailable();
            System.out.println(slot + "|" + name + "|" + price + "| Avail: " + numAvailable);
        }


    }
}





