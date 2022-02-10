package com.techelevator.view;

import com.techelevator.Item;
import com.techelevator.Transaction;
import com.techelevator.VendingMachine;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.*;

public class Menu {
    private Transaction transaction = new Transaction();
    private VendingMachine vendingMachine = new VendingMachine();
    VendingMachine vm = new VendingMachine();
    public Map<String, Item> duplicateMap = vm.getInventoryMap();

    private PrintWriter out;
    private Scanner in;


    public Menu(InputStream input, OutputStream output) {
        this.out = new PrintWriter(output);
        this.in = new Scanner(input);
    }

    public Object getChoiceFromOptions(Object[] options) {
        Object choice = null;
        while (choice == null) {
            displayMenuOptions(options);
            choice = getChoiceFromUserInput(options);
        }
        return choice;
    }

    private Object getChoiceFromUserInput(Object[] options) {
        Object choice = null;
        String userInput = in.nextLine();
        try {
            int selectedOption = Integer.valueOf(userInput);
            if (selectedOption > 0 && selectedOption <= options.length) {
                choice = options[selectedOption - 1];
            }
        } catch (NumberFormatException e) {
            // eat the exception, an error message will be displayed below since choice will be null
        }
        if (choice == null) {
            out.println(System.lineSeparator() + "*** " + userInput + " is not a valid option ***" + System.lineSeparator());
        }
        return choice;
    }

    public void purchase() {
        try {
            System.out.println("Current Money Provided: $" + transaction.getBalance());
            System.out.println("");
            System.out.println("(1) Feed Money");
            System.out.println("(2) Select Product");
            System.out.println("(3) Finish Transaction");
            System.out.println("");

            System.out.println("Please choose and option >>> ");
            String userInput = in.nextLine();

            if (userInput.substring(0, 1).equals("1")) {
                vendingMachine.feedMoney();
            } else if (userInput.substring(0, 1).equals("2")) {
                vendingMachine.selectProduct();
            } else if (userInput.substring(0, 1).equals("3")) {
                vendingMachine.getChange();
            } else {
                throw new IllegalArgumentException();

            }

        } catch (IllegalArgumentException e) {
            System.out.println("Enter a valid choice!");

        }
    }


    private void displayMenuOptions(Object[] options) {
        out.println();
        for (int i = 0; i < options.length; i++) {
            int optionNum = i + 1;
            out.println(optionNum + ") " + options[i]);
        }
        out.print(System.lineSeparator() + "Please choose an option >>> ");
        out.flush();
    }

    public void displayMachineItems() {

        Set<String> inventoryKeys = duplicateMap.keySet();
        for (String inventoryKey : inventoryKeys) {
            String slot = duplicateMap.get(inventoryKey).getSlot();
            String name = duplicateMap.get(inventoryKey).getName();
            BigDecimal price = duplicateMap.get(inventoryKey).getPrice();
            int numAvailable = duplicateMap.get(inventoryKey).getNumAvailable();
            System.out.println(slot + "|" + name + "|" + price + "| Avail: " + numAvailable);
        }


    }


}
