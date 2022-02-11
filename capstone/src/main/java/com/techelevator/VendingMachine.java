package com.techelevator;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class VendingMachine {

    private static Transaction transaction = new Transaction();
    //    private static Map<String, Item> inventoryMap = new HashMap<>();
    private static Map<String, Item> inventoryMap = new HashMap<>();
//    private Map<String, Item> duplicateMap = inventoryMap;


    static Scanner scan = new Scanner(System.in);

    //    public static Map<String, Item> getInventoryMap() {
    public Map<String, Item> getInventoryMap() {
        VendingMachine vm = new VendingMachine();
        vm.createItems();
        return inventoryMap;
    }


    private void createItems() {

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

    private String receiveUserMoney() {

        System.out.print("Enter money in Whole Dollars >>> ");
        String userInput = scan.nextLine();
        return userInput;

    }

    private void addMoneyToBalance(String moneyIn) {
        BigDecimal startingBalance = transaction.getBalance();

        String userInputMoney = moneyIn + ".00";
        BigDecimal amountDeposited = new BigDecimal(userInputMoney);
        transaction.deposit(amountDeposited);
        System.out.println("Current balance is: $" + transaction.getBalance());

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a");
        String formattedDate = dateFormatter.format(LocalDateTime.now());
        System.out.println(formattedDate + " FEED MONEY: $" + startingBalance + " $" + transaction.getBalance());

    }

    private String receiveProductCode() {
        System.out.print("Enter item code >>> ");
        String userInput = scan.nextLine();

        return userInput;
    }

    public void sellProduct(String userInput) throws IllegalArgumentException {

        //Takes one out of inventory
        Item product = inventoryMap.get(userInput);
        BigDecimal startingBalance = transaction.getBalance();

        if (product.getPrice().compareTo(transaction.getBalance()) > 0) {
            throw new IllegalArgumentException("Insufficient Funds");

        }

        product.sellItem();

        System.out.println(" ");
        System.out.println("You received " + product.getName());
        System.out.println("Num Available is " + product.getNumAvailable());

        //Subract price from balance
        transaction.withdraw(product.getPrice());
        System.out.println("Current balance is " + transaction.getBalance());

        //Print off sound
        product.getSound();
        System.out.println(product.getSound());

        //TODO - Log transaction

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a");
        String formattedDate = dateFormatter.format(LocalDateTime.now());
        System.out.println(formattedDate + " " + product.getName() + " " + startingBalance + " $" + transaction.getBalance());

        //TODO - Add to Sales Report
    }

    private void getChange() {
        BigDecimal startingBalance = transaction.getBalance();

        BigDecimal zero = new BigDecimal("0.00");
        int numOfQuarters = 0;
        int numOfDimes = 0;
        int numOfNickels = 0;
        BigDecimal quarter = new BigDecimal("0.25");
        BigDecimal dime = new BigDecimal("0.10");
        BigDecimal nickel = new BigDecimal("0.05");

        System.out.println("Your change is: $" + startingBalance);
        BigDecimal changeBalance = startingBalance;

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
        transaction.withdraw(startingBalance);

        System.out.format("You are receiving %d quarters, %d dimes, %d nickels", numOfQuarters, numOfDimes, numOfNickels);
        System.out.println("");

        //TODO - Log Transaction
        try (PrintWriter logFile = new PrintWriter(new FileWriter("logFile.txt", true))) {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a");
            String formattedDate = dateFormatter.format(LocalDateTime.now());
            System.out.println(formattedDate + " GIVE CHANGE: $" + startingBalance + " $" + transaction.getBalance());

        } catch (IOException e) {
            System.out.println("Enter valid Filename");
        }
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
                String moneyIn = receiveUserMoney();
                addMoneyToBalance(moneyIn);
            } else if (userInput.substring(0, 1).equals("2")) {
                //vendingMachine.selectProduct();
                String productCode = receiveProductCode();
                sellProduct(productCode);
            } else if (userInput.substring(0, 1).equals("3")) {
                //vendingMachine.getChange();
                getChange();
            } else {
                throw new NumberFormatException();

            }

        } catch (NumberFormatException | NullPointerException e) {
            System.out.println(" ");
            System.out.println("Enter a valid choice!");
            System.out.println(" ");

        } catch (IllegalArgumentException e) {
            System.out.println(" ");
            System.out.println("Insufficient Funds");
            System.out.println(" ");

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





