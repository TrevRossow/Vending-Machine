package com.techelevator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static com.techelevator.VendingMachine.inventoryMap;
import static com.techelevator.VendingMachine.transaction;


public class VendingMachineTest {

    VendingMachine vendingMachine = new VendingMachine();


    @Before
    public void setup() {
        transaction.deposit(new BigDecimal("4.90"));

    }


    @Test
    public void test_create_chip_given_csv_line() {
        //Try
        Chip expectedChip = new Chip("Ruffles", new BigDecimal("1.00"), "F7");
        String testString = "F7|Ruffles|1.00|Chip";

        //Assign

        Chip actualChip = vendingMachine.createChip(testString);

        //Assert
        Assert.assertEquals("Chip names don't match", expectedChip.getName(), actualChip.getName());
        Assert.assertEquals("Chip prices don't match", expectedChip.getPrice(), actualChip.getPrice());
        Assert.assertEquals("Chip slots don't match", expectedChip.getSlot(), actualChip.getSlot());

    }

    @Test
    public void test_create_drink_given_csv_line() {
        //Try
        Drink expectedDrink = new Drink("RC", new BigDecimal("1.20"), "F8");
        String testString = "F8|RC|1.20|Drink";

        //Assign

        Drink actualDrink = vendingMachine.createDrink(testString);

        //Assert
        Assert.assertEquals("Drink names don't match", expectedDrink.getName(), actualDrink.getName());
        Assert.assertEquals("Drink prices don't match", expectedDrink.getPrice(), actualDrink.getPrice());
        Assert.assertEquals("Drink slots don't match", expectedDrink.getSlot(), actualDrink.getSlot());

    }

    @Test
    public void test_create_candy_given_csv_line() {
        //Try
        Candy expectedCandy = new Candy("Wonka Bar", new BigDecimal("1.30"), "F9");
        String testString = "F9|Wonka Bar|1.30|Candy";

        //Assign

        Candy actualCandy = vendingMachine.createCandy(testString);

        //Assert
        Assert.assertEquals("Candy names don't match", expectedCandy.getName(), actualCandy.getName());
        Assert.assertEquals("Candy prices don't match", expectedCandy.getPrice(), actualCandy.getPrice());
        Assert.assertEquals("Candy slots don't match", expectedCandy.getSlot(), actualCandy.getSlot());

    }

    @Test
    public void test_create_gum_given_csv_line() {
        //Try
        Gum expectedGum = new Gum("Fruity Stripes", new BigDecimal("1.25"), "F4");
        String testString = "F4|Fruity Stripes|1.25|Gum";

        //Assign

        Gum actualGum = vendingMachine.createGum(testString);

        //Assert
        Assert.assertEquals("Gum names don't match", expectedGum.getName(), actualGum.getName());
        Assert.assertEquals("Gum prices don't match", expectedGum.getPrice(), actualGum.getPrice());
        Assert.assertEquals("Gum slots don't match", expectedGum.getSlot(), actualGum.getSlot());

    }

    @Test
    public void converts_money_feed_to_big_decimal() {
        //Assign
        String userInput = "1";
        BigDecimal actualMoney = vendingMachine.convertsUserInputToBigDecimal(userInput);
        BigDecimal expectedMoney = new BigDecimal("1.00");
        Assert.assertEquals("Couldn't be converted", expectedMoney, actualMoney);

    }

    @Test
    public void passes_if_has_funds_available() {
        //Try
        Chip chip = vendingMachine.createChip("F7|Ruffles|1.00|Chip");

        //Assign
        boolean hasEnoughMoney = vendingMachine.hasFundsAvailable(chip);

        //Assert
        Assert.assertTrue("Returns false given balance of $4.90 and price of $1.00", hasEnoughMoney);

    }

    @Test
    public void passes_if_not_enough_funds_available() {
        //Try
        Chip chip = vendingMachine.createChip("F7|Ruffles|5.00|Chip");

        //Assign
        boolean hasEnoughMoney = vendingMachine.hasFundsAvailable(chip);

        //Assert
        Assert.assertFalse("Returns true given balance of $4.90 and price of $5.00", hasEnoughMoney);

    }


    @Test
    public void passes_if_item_is_in_stock() {
        //Try
        Chip chip = vendingMachine.createChip("F7|Ruffles|1.00|Chip");

        //Assign
        boolean isSoldOut = vendingMachine.isSoldOut(chip);

        //Assert
        Assert.assertFalse("Returns true when item is in stock", isSoldOut);

    }

    @Test
    public void passes_if_item_is_out_of_stock() {
        //Try
        Chip chip = vendingMachine.createChip("F7|Ruffles|1.00|Chip");
        chip.sellItem();
        chip.sellItem();
        chip.sellItem();
        chip.sellItem();
        chip.sellItem();

        //Assign
        boolean isSoldOut = vendingMachine.isSoldOut(chip);

        //Assert
        Assert.assertTrue("Returns false when item is out of stock", isSoldOut);

    }

    @Test
    public void returns_item_given_slot_from_user() {
        //Try
        Chip expectedChip = vendingMachine.createChip("F7|Ruffles|1.00|Chip");
        inventoryMap.put("F7", expectedChip);

        //Assign
        Item actualChip = vendingMachine.getProductFromUserInput("F7");

        //Assert
        Assert.assertEquals("Chip names don't match", expectedChip.getName(), actualChip.getName());
        Assert.assertEquals("Chip prices don't match", expectedChip.getPrice(), actualChip.getPrice());
        Assert.assertEquals("Chip slots don't match", expectedChip.getSlot(), actualChip.getSlot());

    }

    @Test
    public void get_change_prints_correct_string_and_gives_correct_change() {
        //Try
        String expectedString = "You are receiving 19 quarter(s), 1 dime(s) and 1 nickel(s)";

        //Assign
        String actualString = vendingMachine.getChange();

        //Assert
        Assert.assertEquals("Didn't print correct change given $4.90 balance", expectedString, actualString);
    }

}