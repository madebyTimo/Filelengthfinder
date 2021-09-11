package main;

import java.util.ArrayList;

import FileFinder.Filelengthfinder;
import Tagger.Mp3Tagger;


/**
 * main class of the Fileorganizer.
 * Some tools to help handle files
 *
 */
public class Fileorganizer {
	
	private CLI cli;

	/**
	 * initial object of the Fileorganizer
	 * @param args (non-null) the command line arguments
	 */
	public Fileorganizer(final String[] args) {
		this.cli = new CLI();
		mainMenu();
	}
	
	
	
	/**
	 * main Method, creates a new object of the {@link Fileorganizer}
	 * @param args Array of commandline-Arguments
	 */
	public static void main(String[] args) {
		new Fileorganizer(args);
	}
	
	private void mainMenu() {
		System.out.println("--> main menu");
		ArrayList<String> mainMenu = new ArrayList<String>();
		mainMenu.add("Filelengthfinder");
		mainMenu.add("Tagger");
		int selection = 0;
		do {
			selection = cli.showSimpleMenu(mainMenu);
			if (selection == 1) {
				new Filelengthfinder(cli);;
			} else if (selection == 2) {
				new Mp3Tagger(cli);
			} else if (selection != -1) {
				System.out.println("Invalid Input!");
				selection = Integer.MIN_VALUE;
			}
		} while (selection == Integer.MIN_VALUE);
	}
	

	/**
	 * check the command line arguments and execute them
	 * @param args (non-null) the command line arguments
	 */
	private void checkCommandLineArguments(final String[] args) {
		for (int i = 0; i < args.length; i++) {
			if (args[i].startsWith("--help") || args[i].startsWith("-h")) {
				printHelpMessage();
			}
		}
	}


	/**
	 * print help-message for command line arguments
	 */
	private void printHelpMessage() {
		System.out.println("Command-line arguments aren't supported yet");
		
	}
}
