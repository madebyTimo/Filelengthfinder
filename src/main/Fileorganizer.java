package main;

import java.util.ArrayList;

import FileFinder.Filelengthfinder;

public class Fileorganizer {
	
	private CLI cli;

	public Fileorganizer(final String[] args) {
		this.cli = new CLI();
		mainMenu();
	}
	
	
	
	/**
	 * main Method of the {@link Filelengthfinder}
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
				//TODO Tagger
			} else if (selection != -1) {
				System.out.println("Invalid Input!");
			}
		} while (selection != -1);
	}
	

}
