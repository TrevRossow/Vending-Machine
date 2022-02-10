package com.techelevator;

import com.techelevator.view.Menu;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class VendingMachine {

    private Transaction transaction = new Transaction();


//    public static void main(String[] args) {
//
//        VendingMachine vm = new VendingMachine();
//        vm.createItems();
//        System.out.println(duplicateMap.get("A1").getSlot());
//    }

    public static Map<String, Item> inventoryMap = new HashMap<>();


    Scanner scan = new Scanner(System.in);

    public static Map<String, Item> getInventoryMap() {
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

    public void feedMoney() {

        System.out.print("Enter money in Whole Dollars >>> ");
        String userInput = scan.nextLine();
        String userInputMoney = userInput + ".00";
        BigDecimal amountDeposited = new BigDecimal(userInputMoney);
        transaction.deposit(amountDeposited);
        System.out.println("Current balance is: $" + transaction.getBalance());

        //TODO - Log Transaction

    }

    public void selectProduct() {
        System.out.print("Enter item code >>>");
        String userInput = scan.nextLine();


        //Takes one out of inventory
        Item product = inventoryMap.get(userInput);
        product.sellItem();
        System.out.println("Num Available is " + product.getNumAvailable());

        //Subract price from balance
        transaction.withdrawl(product.getPrice());
        System.out.println("Current balance is " + transaction.getBalance());

        //Print off sound
        product.getSound();
        System.out.println(product.getSound());

        //TODO - Check there's to buy item

        //TODO - Log transaction



        //TODO - Add to Sales Report

    }


}


