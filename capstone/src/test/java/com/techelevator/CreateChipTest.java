package com.techelevator;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class CreateChipTest {

    @Test
    public void testCreateChip() {
        //Try
        Chip expectedChip = new Chip("Ruffles", new BigDecimal("1.00"), "F7");
        String testString = "F7|Ruffles|1.00|Chip";

        //Assign
        ForUnitTests forUnitTests = new ForUnitTests();
        Chip actualChip = forUnitTests.createChip(testString);

        //Assert
        Assert.assertEquals("Chip names don't match",expectedChip.getName(), actualChip.getName());
        Assert.assertEquals("Chip prices don't match",expectedChip.getPrice(), actualChip.getPrice());
        Assert.assertEquals("Chip slots don't match",expectedChip.getSlot(), actualChip.getSlot());

    }

}
