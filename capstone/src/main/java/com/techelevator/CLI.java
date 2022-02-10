package com.techelevator;

import com.techelevator.view.Menu;

public class CLI {

    private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
    private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
    private static final String[] MAIN_MENU_OPTIONS = {MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE};

    //TODO - Add option for Sales Report

    private Menu menu;

    VendingMachine test = new VendingMachine();


    public CLI(Menu menu) {
        this.menu = menu;
    }

    public void run() {
        while (true) {
            String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

            if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
                test.displayMachineItems();
            } else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
                //menu.purchase
                test.purchase();

            }
        }
    }

    public static void main(String[] args) {
        Menu menu = new Menu(System.in, System.out);
        CLI cli = new CLI(menu);
        cli.run();
    }
}
