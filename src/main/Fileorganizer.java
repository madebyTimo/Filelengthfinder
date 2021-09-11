package main;

import java.util.ArrayList;

import FileFinder.Filelengthfinder;
import Tagger.MP3Tagger;

public class Fileorganizer {
	
	private CLI cli;

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
				new MP3Tagger(cli);
			} else if (selection != -1) {
				System.out.println("Invalid Input!");
				selection = Integer.MIN_VALUE;
			}
		} while (selection == Integer.MIN_VALUE);
	}
	

}
