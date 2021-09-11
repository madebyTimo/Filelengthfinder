package Tagger;

import java.util.ArrayList;

import FileFinder.Filelengthfinder;
import main.CLI;

public class MP3Tagger {
	
	private CLI cli;
	
	
	/**
	 * 
	 * @param cli for interaction with CLI
	 */
	public MP3Tagger(final CLI cli) {
		menu();
	}
	
	
	/**
	 * show menu with options of the MP3Tagger
	 */
	private void menu() {
		System.out.println("--> MP3Tagger menu");
		ArrayList<String> menu = new ArrayList<String>();
		menu.add("edit Tags");
		menu.add("Filename to MP3Tags");
		menu.add("MP3Tags to Filename");
		int selection = 0;
		do {
			selection = cli.showSimpleMenu(menu);
			if (selection == 1) {
				//TODO edit Tags
			} else if (selection == 2) {
				//TODO Filename to MP3Tags
			} else if (selection == 3) {
				//TODO MP3Tags to Filename
			} else if (selection != -1) {
				System.out.println("Invalid Input!");
				selection = Integer.MIN_VALUE;
			}
		} while (selection != Integer.MIN_VALUE);
	}
	
}
