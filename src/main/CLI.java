package main;

import java.util.List;
import java.util.Scanner;

public class CLI {


	/**
	 * scans the commandline for the written line
	 * @return the written line
	 */
	public String consoleScanner() {
		Scanner scanner = new Scanner(System.in);
		String eingabe = scanner.nextLine();
//		scanner.close();
		return eingabe;
	}

	
	/**
	 * 
	 * Simple CLI menu to select one of the given options
	 * 
	 * @param options Options which can be selected by the user
	 * @return the number of the options selected by the user starting by 1. 0 for
	 *         non-numeral input. -1 for quit-command.
	 */
	public int showSimpleMenu(final List<String> options) {
		System.out.println();
		System.out.println("Please select one of the following options. Type \"q\" to exit menu.");
		System.out.println();
		int counter = 1;
		for (String option : options) {
			System.out.println(counter++ + ". " + option);
		}
		System.out.println();
		System.out.print("Your selection: ");
		String input = consoleScanner();
		System.out.println();
		if (input.equals("q")) {
			return -1;
		} else {
			try {
				return Integer.parseInt(input);
			} catch (NumberFormatException e) {
				return 0;
			}
		}

	}

}
