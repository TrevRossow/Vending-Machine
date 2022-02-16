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

    public static Transaction transaction = new Transaction();
    public static Map<String, Item> inventoryMap = new HashMap<>();


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


    protected Map<String, Item> createItems() {

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

        return inventoryMap;
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

    public Item getProductFromUserInput(String userInput) {
        Item product = inventoryMap.get(userInput);
        return product;
    }

    public boolean hasFundsAvailable(Item product) {
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

    public boolean isSoldOut(Item product) {
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

    public void printsProductNameAndNumAvailable(Item product) {
        System.out.println(" ");
        System.out.println("You received " + product.getName());
        System.out.println("Num Available is " + product.getNumAvailable());
    }

    public void printSound(Item product) {
        System.out.println(product.getSound());
    }

    public void subtractBalanceAndLogTransaction(Item product) {
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



    protected String getChange() {
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

        System.out.format("You are receiving %d quarter(s), %d dime(s), %d nickel(s)", numOfQuarters, numOfDimes, numOfNickels);
        System.out.println("");

        try (PrintWriter logFile = new PrintWriter(new FileWriter("Log.txt", true))) {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a");
            String formattedDate = dateFormatter.format(LocalDateTime.now());
            logFile.println(formattedDate + " GIVE CHANGE: $" + startingBalance + " $" + transaction.getBalance());

        } catch (IOException e) {
            System.out.println("Enter valid Filename");
        }

        return "You are receiving " + numOfQuarters + " quarter(s), " + numOfDimes + " dime(s) and " +numOfNickels + " nickel(s)";
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
                BigDecimal converted = convertsUserInputToBigDecimal(moneyIn);
                addsMoneyInputToBalance(converted);
            } else if (userInput.substring(0, 1).equals("2")) {
                //vendingMachine.selectProduct();
                String productCode = receiveProductCode();
                Item product = getProductFromUserInput(productCode);
                givesExcpetionIfNotEnoughFunds(hasFundsAvailable(product));
                givesExceptionIfSoldOut(isSoldOut(product));
//                Item testProduct = inventoryMap.get(productCode);
                product.sellItem();
                printsProductNameAndNumAvailable(product);
                printSound(product);
                subtractBalanceAndLogTransaction(product);


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





