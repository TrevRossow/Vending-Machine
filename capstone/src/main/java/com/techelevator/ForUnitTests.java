package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ForUnitTests {

    Map<String, Item> fakeMap = new HashMap<>();

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
                    fakeMap.put(slot, chip);

                } else if
                (item.equals("Drink")) {
                    Drink drink = new Drink(name, price, slot);
                    fakeMap.put(slot, drink);

                } else if
                (item.equals("Candy")) {
                    Candy candy = new Candy(name, price, slot);
                    fakeMap.put(slot, candy);

                } else if (item.equals("Gum")) {
                    Gum gum = new Gum(name, price, slot);
                    fakeMap.put(slot, gum);
                }
            }


        } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
        }


    }


    public Chip createChip(String csvLine) {
        String[] characteristics = csvLine.split("\\|");
        String slot = characteristics[0];
        String name = characteristics[1];
        BigDecimal price = new BigDecimal(characteristics[2]);
        String item = characteristics[3];


        if (item.equals("Chip")) {
            Chip chip = new Chip(name, price, slot);
            fakeMap.put(slot, chip);
            return chip;
        } else {
            return null;
        }

    }
}


//    CREATE ITEMS AS ONE METHOD
//
//        public static Map<String, Item> getInventoryMap() {
//        VendingMachine vm = new VendingMachine();
//        vm.createItems();
//        return inventoryMap;
//    }
////
////        private void createItems() {
////
////        try (Scanner inputFile = new Scanner(new File("vendingmachine.csv"))) {
////
////            while (inputFile.hasNextLine()) {
////                String line = inputFile.nextLine();
////                String[] characteristics = line.split("\\|");
////                String slot = characteristics[0];
////                String name = characteristics[1];
////                BigDecimal price = new BigDecimal(characteristics[2]);
////                String item = characteristics[3];
////
////
////                if (item.equals("Chip")) {
////                    Chip chip = new Chip(name, price, slot);
////                    inventoryMap.put(slot, chip);
////
////                } else if
////                (item.equals("Drink")) {
////                    Drink drink = new Drink(name, price, slot);
////                    inventoryMap.put(slot, drink);
////
////                } else if
////                (item.equals("Candy")) {
////                    Candy candy = new Candy(name, price, slot);
////                    inventoryMap.put(slot, candy);
////
////                } else if (item.equals("Gum")) {
////                    Gum gum = new Gum(name, price, slot);
////                    inventoryMap.put(slot, gum);
////                }
////            }
////
////
////        } catch (FileNotFoundException e) {
////            System.out.println("File Not Found");
////        }
////
////
////    }
//}

//  public void createDrinks() {
//        try (Scanner inputFile = new Scanner(new File("vendingmachine.csv"))) {
//
//            while (inputFile.hasNextLine()) {
//                String line = inputFile.nextLine();
//                String[] characteristics = line.split("\\|");
//                String slot = characteristics[0];
//                String name = characteristics[1];
//                BigDecimal price = new BigDecimal(characteristics[2]);
//                String item = characteristics[3];
//
//                if (item.equals("Drink")) {
//                    Drink drink = new Drink(name, price, slot);
//                    inventoryMap.put(slot, drink);
//
//                }
//
//            }
//        } catch (FileNotFoundException e) {
//            System.out.println("File Not Found");
//        }
//    }

//    public void createCandy() {
//        try (Scanner inputFile = new Scanner(new File("vendingmachine.csv"))) {
//
//            while (inputFile.hasNextLine()) {
//                String line = inputFile.nextLine();
//                String[] characteristics = line.split("\\|");
//                String slot = characteristics[0];
//                String name = characteristics[1];
//                BigDecimal price = new BigDecimal(characteristics[2]);
//                String item = characteristics[3];
//
//                if (item.equals("Candy")) {
//                    Candy candy = new Candy(name, price, slot);
//                    inventoryMap.put(slot, candy);
//
//                }
//
//            }
//        } catch (FileNotFoundException e) {
//            System.out.println("File Not Found");
//        }
//    }
//
//    public void createGum() {
//        try (Scanner inputFile = new Scanner(new File("vendingmachine.csv"))) {
//
//            while (inputFile.hasNextLine()) {
//                String line = inputFile.nextLine();
//                String[] characteristics = line.split("\\|");
//                String slot = characteristics[0];
//                String name = characteristics[1];
//                BigDecimal price = new BigDecimal(characteristics[2]);
//                String item = characteristics[3];
//
//                if (item.equals("Gum")) {
//                    Gum gum = new Gum(name, price, slot);
//                    inventoryMap.put(slot, gum);
//
//                }
//
//            }
//        } catch (FileNotFoundException e) {
//            System.out.println("File Not Found");
//        }
//    }

//    public static Map<String, Item> getInventoryMap() {
//        VendingMachine vm = new VendingMachine();
//        vm.createChips();
//        vm.createCandy();
//        vm.createDrinks();
//        vm.createGum();
//        return inventoryMap;
//    }

//
//    public void createChips() {
//        try (Scanner inputFile = new Scanner(new File("vendingmachine.csv"))) {
//
//            while (inputFile.hasNextLine()) {
//                String line = inputFile.nextLine();
//                String[] characteristics = line.split("\\|");
//                String slot = characteristics[0];
//                String name = characteristics[1];
//                BigDecimal price = new BigDecimal(characteristics[2]);
//                String item = characteristics[3];
//
//                if (item.equals("Chip")) {
//                    Chip chip = new Chip(name, price, slot);
//                    inventoryMap.put(slot, chip);
//
//                }
//
//            }
//        } catch (FileNotFoundException e) {
//            System.out.println("File Not Found");
//        }
//    }


//    private void addMoneyToBalance(String moneyIn) {
//        BigDecimal startingBalance = transaction.getBalance();
//
//        String userInputMoney = moneyIn + ".00";
//        BigDecimal amountDeposited = new BigDecimal(userInputMoney);
//        transaction.deposit(amountDeposited);
//        System.out.println("Current balance is: $" + transaction.getBalance());
//
//        try (PrintWriter logFile = new PrintWriter(new FileWriter("Log.txt", true))) {
//            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a");
//            String formattedDate = dateFormatter.format(LocalDateTime.now());
//            logFile.println(formattedDate + " FEED MONEY: $" + startingBalance + " $" + transaction.getBalance());
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        }
//
//    }

//    public Gum createGum(String csvLine) {
//
//        String[] characteristics = csvLine.split("\\|");
//        String slot = characteristics[0];
//        String name = characteristics[1];
//        BigDecimal price = new BigDecimal(characteristics[2]);
//        String item = characteristics[3];
//
//        Gum gum = new Gum(name, price, slot);
//        inventoryMap.put(slot, gum);
//        return gum;
//    }

//    public Candy createCandy(String csvLine) {
//
//        String[] characteristics = csvLine.split("\\|");
//        String slot = characteristics[0];
//        String name = characteristics[1];
//        BigDecimal price = new BigDecimal(characteristics[2]);
//        String item = characteristics[3];
//
//        Candy candy = new Candy(name, price, slot);
//        inventoryMap.put(slot, candy);
//        return candy;
//
//
//    }

//    public Drink createDrink(String csvLine) {
//
//        String[] characteristics = csvLine.split("\\|");
//        String slot = characteristics[0];
//        String name = characteristics[1];
//        BigDecimal price = new BigDecimal(characteristics[2]);
//        String item = characteristics[3];
//
//        Drink drink = new Drink(name, price, slot);
//        inventoryMap.put(slot, drink);
//        return drink;
//
//    }

//    public Chip createChip(String csvLine) {
//
//        String[] characteristics = csvLine.split("\\|");
//        String slot = characteristics[0];
//        String name = characteristics[1];
//        BigDecimal price = new BigDecimal(characteristics[2]);
//        String item = characteristics[3];
//
//        Chip chip = new Chip(name, price, slot);
//        inventoryMap.put(slot, chip);
//
//        return chip;
//    }