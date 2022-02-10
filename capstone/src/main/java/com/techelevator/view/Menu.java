package com.techelevator.view;

import com.techelevator.Item;
import com.techelevator.VendingMachine;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.*;

public class Menu {

	public static void main(String[] args) {

		Menu m = new Menu();
		m.displayMachineItems();

	}

	private PrintWriter out;
	private Scanner in;

	public Menu() {

	}
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


		VendingMachine vm = new VendingMachine();
		Map<String, Item> inventoryMap = vm.getInventoryMap();
		Set<String> inventoryKeys = inventoryMap.keySet();

		for (String inventoryKey : inventoryKeys) {
			String slot = inventoryMap.get(inventoryKey).getSlot();
			String name = inventoryMap.get(inventoryKey).getName();
			BigDecimal price = inventoryMap.get(inventoryKey).getPrice();
			int numAvailable = inventoryMap.get(inventoryKey).getNumAvailable();
			System.out.println(slot + "|" + name + "|" + price	+ "| Avail: " + numAvailable );
		}




	}

}
