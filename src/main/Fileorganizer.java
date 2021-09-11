package main;

import FileFinder.Filelengthfinder;

public class Fileorganizer {

	public Fileorganizer(final String[] args) {
		new Filelengthfinder(args);
	}
	
	
	
	/**
	 * main Method of the {@link Filelengthfinder}
	 * @param args Array of commandline-Arguments
	 */
	public static void main(String[] args) {
		new Fileorganizer(args);
	}
	
	

}
