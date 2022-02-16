package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.*;

import static com.techelevator.VendingMachine.inventoryMap;

public class SalesReport {

    VendingMachine fakeMachine = new VendingMachine();


    public void printSalesReport() {
        Map<String, Item> duplicateMap = fakeMachine.createItems();
        Map<String, Integer> salesReportMap = new HashMap<>();
        Set<String> mapKeys = duplicateMap.keySet();
        List<String> itemsSold = new ArrayList<>();
        BigDecimal totalRevenue = new BigDecimal("0.00");

        try (Scanner scanner = new Scanner(new File("Log.txt"))) {

            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                String slot = "";

                for (int i = 0; i < line.length() - 2; i++) {
                    boolean lineIsItemSale = mapKeys.contains(line.substring(i, i + 2));

                    if (lineIsItemSale) {
                        slot += line.substring(i, i + 2);
                        String productName = duplicateMap.get(slot).getName();
                        itemsSold.add(productName);
                        BigDecimal itemPrice = duplicateMap.get(slot).getPrice();
                        totalRevenue = totalRevenue.add(itemPrice);
                    }

                }

            }

            for (String mapKey : mapKeys) {
                String productName = inventoryMap.get(mapKey).getName();
                if (Collections.frequency(itemsSold, productName) > 0) {
                    System.out.println(productName + " | " + Collections.frequency(itemsSold, productName));
                }
            }

            System.out.println("");
            System.out.println("TOTAL SALES: $" + totalRevenue);

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }


}
