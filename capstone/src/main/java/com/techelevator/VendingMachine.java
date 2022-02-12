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
    private static Map<String, Item> inventoryMap = new HashMap<>();


    static Scanner scan = new Scanner(System.in);

    public static Map<String, Item> getInventoryMap() {
        VendingMachine vm = new VendingMachine();
        vm.createItems();
        return inventoryMap;
    }

    public Chip createChip(String csvLine) {

        String[] characteristics = csvLine.split("\\|");
        String slot = characteristics[0];
        String name = characteristics[1];
        BigDecimal price = new BigDecimal(characteristics[2]);
        String item = characteristics[3];

        Chip chip = new Chip(name, price, slot);
        return chip;
    }

    public void addChipToInventory(String slot, Chip chip) {
        inventoryMap.put(slot, chip);
    }

    public Drink createDrink(String csvLine) {

        String[] characteristics = csvLine.split("\\|");
        String slot = characteristics[0];
        String name = characteristics[1];
        BigDecimal price = new BigDecimal(characteristics[2]);
        String item = characteristics[3];

        Drink drink = new Drink(name, price, slot);
        return drink;

    }

    public void addDrinkToInventory(String slot, Drink drink) {
        inventoryMap.put(slot, drink);
    }

    public Candy createCandy(String csvLine) {

        String[] characteristics = csvLine.split("\\|");
        String slot = characteristics[0];
        String name = characteristics[1];
        BigDecimal price = new BigDecimal(characteristics[2]);
        String item = characteristics[3];

        Candy candy = new Candy(name, price, slot);
        return candy;
    }

    public void addCandyToInventory(String slot, Candy candy) {
        inventoryMap.put(slot, candy);
    }

    public Gum createGum(String csvLine) {

        String[] characteristics = csvLine.split("\\|");
        String slot = characteristics[0];
        String name = characteristics[1];
        BigDecimal price = new BigDecimal(characteristics[2]);
        String item = characteristics[3];

        Gum gum = new Gum(name, price, slot);
        return gum;
    }

    public void addGumToInventory(String slot, Gum gum) {
        inventoryMap.put(slot, gum);
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
//                    createChip(line);
                    Chip chip = createChip(line);
                    addChipToInventory(slot, chip);

                } else if
                (item.equals("Drink")) {
//                    createDrink(line);

                    Drink drink = createDrink(line);
                    addDrinkToInventory(slot, drink);
                } else if
                (item.equals("Candy")) {
//                    createCandy(line);

                    Candy candy = createCandy(line);
                    addCandyToInventory(slot, candy);

                } else if (item.equals("Gum")) {
//                    createGum(line);

                    Gum gum = createGum(line);
                    addGumToInventory(slot, gum);

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

    public BigDecimal convertsUserInputToBigDecimal(String moneyIn) {
        String userInputMoney = moneyIn + ".00";
        BigDecimal amountDeposited = new BigDecimal(userInputMoney);
        return amountDeposited;

    }

    private void addsMoneyInputToBalance(BigDecimal amountDeposited) {
        BigDecimal startingBalance = transaction.getBalance();
        transaction.deposit(amountDeposited);
        System.out.println("Current balance is: $" + transaction.getBalance());

        try (PrintWriter logFile = new PrintWriter(new FileWriter("Log.txt", true))) {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a");
            String formattedDate = dateFormatter.format(LocalDateTime.now());
            logFile.println(formattedDate + " FEED MONEY: $" + startingBalance + " $" + transaction.getBalance());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }


    private String receiveProductCode() {
        System.out.println("");
        displayMachineItems();
        System.out.println("");
        System.out.print("Enter item code >>> ");
        String userInput = scan.nextLine();

        return userInput;
    }

    public boolean hasFundsAvailable(String userInput) {
        Item product = inventoryMap.get(userInput);
        if (product.getPrice().compareTo(transaction.getBalance()) > 0) {
            return false;
        } else {
            return true;
        }

    }

    public void givesExcpetionIfNotEnoughFunds(boolean hasFundsAvailable) {
        if (!hasFundsAvailable) {
            throw new IllegalArgumentException();
        }
    }

    public boolean isSoldOut(String userInput) {
        Item product = inventoryMap.get(userInput);
        if (product.getNumAvailable() > 0) {
            return false;
        } else  {
            return true;
        }
    }

    public void givesExceptionIfSoldOut(boolean isSoldOut) {
        if (isSoldOut) {
            throw new IllegalStateException();
        }
    }

    public void printsProductNameAndNumAvailable(String userInput) {
        Item product = inventoryMap.get(userInput);
        System.out.println(" ");
        System.out.println("You received " + product.getName());
        System.out.println("Num Available is " + product.getNumAvailable());
    }

    public void printSound(String userInput) {
        Item product = inventoryMap.get(userInput);
        System.out.println(product.getSound());
    }

    public void subtractBalanceAndLogTransaction(String userInput) {
        //Creates Product
        Item product = inventoryMap.get(userInput);

        //Creates Starting Balance
        BigDecimal startingBalance = transaction.getBalance();

        //Subtracts price from balance/Prints new balance
        transaction.withdraw(product.getPrice());
        System.out.println("Current balance is " + transaction.getBalance());


        //Logs transaction
        try (PrintWriter logFile = new PrintWriter(new FileWriter("Log.txt", true))) {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a");
            String formattedDate = dateFormatter.format(LocalDateTime.now());
            logFile.println(formattedDate + " " + product.getName() + " " + product.getSlot() + " $" + startingBalance + " $" + transaction.getBalance());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void sellProduct(String userInput) throws IllegalArgumentException {

        //Takes one out of inventory
        Item product = inventoryMap.get(userInput);
        BigDecimal startingBalance = transaction.getBalance();

        if (product.getPrice().compareTo(transaction.getBalance()) > 0) {
            throw new IllegalArgumentException("Insufficient Funds");

        }
        if (product.getNumAvailable() > 0) {
            product.sellItem();
        } else {
            throw new IllegalStateException();
        }

        System.out.println(" ");
        System.out.println("You received " + product.getName());
        System.out.println("Num Available is " + product.getNumAvailable());

        //Print off sound
        product.getSound();
        System.out.println(product.getSound());

        //Subract price from balance
        transaction.withdraw(product.getPrice());
        System.out.println("Current balance is " + transaction.getBalance());

        try (PrintWriter logFile = new PrintWriter(new FileWriter("Log.txt", true))) {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a");
            String formattedDate = dateFormatter.format(LocalDateTime.now());
            logFile.println(formattedDate + " " + product.getName() + " " + product.getSlot() + " $" + startingBalance + " $" + transaction.getBalance());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

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

        try (PrintWriter logFile = new PrintWriter(new FileWriter("Log.txt", true))) {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a");
            String formattedDate = dateFormatter.format(LocalDateTime.now());
            logFile.println(formattedDate + " GIVE CHANGE: $" + startingBalance + " $" + transaction.getBalance());

        } catch (IOException e) {
            System.out.println("Enter valid Filename");
        }
    }

//    private String getChange() {
//        BigDecimal startingBalance = transaction.getBalance();
//
//        BigDecimal zero = new BigDecimal("0.00");
//        int numOfQuarters = 0;
//        int numOfDimes = 0;
//        int numOfNickels = 0;
//        BigDecimal quarter = new BigDecimal("0.25");
//        BigDecimal dime = new BigDecimal("0.10");
//        BigDecimal nickel = new BigDecimal("0.05");
//
//        System.out.println("Your change is: $" + startingBalance);
//        BigDecimal changeBalance = startingBalance;
//
//        while ((changeBalance.compareTo(zero) > 0)) {
//
//
//            if (changeBalance.compareTo(quarter) > -1) {
//                numOfQuarters++;
//                changeBalance = changeBalance.subtract(quarter);
//            } else if (changeBalance.compareTo(dime) > -1) {
//                numOfDimes++;
//                changeBalance = changeBalance.subtract(dime);
//            } else if (changeBalance.compareTo(nickel) > -1) {
//                numOfNickels++;
//                changeBalance = changeBalance.subtract(nickel);
//
//            }
//        }
//        transaction.withdraw(startingBalance);
//
//        System.out.format("You are receiving %d quarters, %d dimes, %d nickels", numOfQuarters, numOfDimes, numOfNickels);
//        System.out.println("");
//
//        return "You are receiving " + numOfQuarters + ", " + numOfDimes + ", " + numOfNickels;
//
//        try (PrintWriter logFile = new PrintWriter(new FileWriter("Log.txt", true))) {
//            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a");
//            String formattedDate = dateFormatter.format(LocalDateTime.now());
//            logFile.println(formattedDate + " GIVE CHANGE: $" + startingBalance + " $" + transaction.getBalance());
//
//        } catch (IOException e) {
//            System.out.println("Enter valid Filename");
//        }
//    }

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
                BigDecimal converted = convertsUserInputToBigDecimal(moneyIn);
                addsMoneyInputToBalance(converted);
            } else if (userInput.substring(0, 1).equals("2")) {
                //vendingMachine.selectProduct();
                String productCode = receiveProductCode();
                givesExcpetionIfNotEnoughFunds(hasFundsAvailable(productCode));
                givesExceptionIfSoldOut(isSoldOut(productCode));
                Item product = inventoryMap.get(productCode);
                product.sellItem();
                printsProductNameAndNumAvailable(productCode);
                printSound(productCode);
                subtractBalanceAndLogTransaction(productCode);


//                sellProduct(productCode);

            } else if (userInput.substring(0, 1).equals("3")) {
                //vendingMachine.getChange();
                getChange();
            } else {
                throw new NumberFormatException();

            }

        } catch (NumberFormatException | NullPointerException | StringIndexOutOfBoundsException e) {
            System.out.println(" ");
            System.out.println("Enter a valid choice!");
            System.out.println(" ");

        } catch (IllegalArgumentException e) {
            System.out.println(" ");
            System.out.println("Insufficient Funds");
            System.out.println(" ");

        } catch (IllegalStateException e) {
            System.out.println("");
            System.out.println("SOLD OUT");
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





