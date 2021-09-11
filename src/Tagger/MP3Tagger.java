package Tagger;

import java.io.File;
import java.util.ArrayList;

import FileFinder.Filelengthfinder;
import main.CLI;

public class MP3Tagger {

	private final CLI cli;
	private File file;
	private boolean searchDirectoryRecursively;
	private final Mp3TagEditor tagEditor;

	/**
	 * 
	 * @param cli for interaction with CLI
	 */
	public MP3Tagger(final CLI cli) {
		this.cli = cli;
		menu();
		this.tagEditor = new Mp3TagEditor();
	}

	/**
	 * show menu with options of the MP3Tagger
	 */
	private void menu() {
		System.out.println("--> MP3Tagger menu");
		ArrayList<String> menu = new ArrayList<String>();
		menu.add("edit Tags");
		menu.add("Filename to Tags");
		menu.add("Tags to Filename");
		int selection = 0;
		do {
			selection = cli.showSimpleMenu(menu);
			if (selection == 1) {
				// TODO edit Tags
			} else if (selection == 2) {
				filenameToTags();
			} else if (selection == 3) {
				// TODO MP3Tags to Filename
			} else if (selection != -1) {
				System.out.println("Invalid Input!");
				selection = Integer.MIN_VALUE;
			}
		} while (selection != Integer.MIN_VALUE);
	}

	/**
	 * edit the tags with information of the filename of given files.
	 */
	private void filenameToTags() {
		selectPath();
		if (file.isDirectory()) {
			System.out.println("Do you want to search recursively in the directory?");
			searchDirectoryRecursively = cli.showYesNoSelection();
			for (File file : this.file.listFiles()) {
				if (file.getName().endsWith(".mp3")) {
					filenameToTags(file);
				} else {
					System.out.println("File \"" + file.getAbsolutePath() + "\" is no MP3");
				}
			}
		} else {
			filenameToTags(this.file);
		}
	}

	private void filenameToTags(final File file) {
		if (file.getName().endsWith(".mp3")) {
			String fileName = file.getName().substring(0, file.getName().length() - 5);
			String[] tags = fileName.split(" - ");
			Mp3Tags mp3Tags = new Mp3Tags(tags[1], tags[0]);
			this.tagEditor.writeTags(file, mp3Tags);
			System.out.println("Edited tags of \"" + file.getAbsolutePath() + "\"");
		}
	}

	private void selectPath() {
		String path = null;
		while (path == null || path.length() == 0) {
			System.out.println("Which file/directory should be edited?");
			path = this.cli.consoleScanner();
			if (path != null) {
				file = new File(path);
				if (!file.exists()) {
					System.out.println("The path " + path + " doesn't exist.");
				}
			}
		}
	}

	private void getTagsFromFilename() {

	}

	private void renameFile(final File file, final String newName) {

	}

}
