package Tagger;

import java.io.File;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import main.CLI;


/**
 * Class to do things with MP3Tags 
 * 
 */
public class Mp3Tagger {

	private final CLI cli;
	private File file;
	private boolean searchDirectoryRecursively;
	private final Mp3TagEditor tagEditor;

	/**
	 * creates an MP3 Tagger
	 * @param cli for interaction with CLI
	 */
	public Mp3Tagger(final CLI cli) {
		this.cli = cli;
		this.tagEditor = new Mp3TagEditor();
		menu();
	}

	/**
	 * show menu with options of the Mp3Tagger
	 */
	private void menu() {
		System.out.println("--> Mp3Tagger menu");
		ArrayList<String> menu = new ArrayList<String>();
		menu.add("edit Tags");
		menu.add("Filename to Tags");
		menu.add("Tags to Filename");
		int selection = 0;
		do {
			selection = cli.showSimpleMenu(menu);
			if (selection == 1) {
				editTags();
			} else if (selection == 2) {
				filenameToTags();
			} else if (selection == 3) {
				tagsToFilename();
			} else if (selection != -1) {
				System.out.println("Invalid Input!");
				selection = Integer.MIN_VALUE;
			}
		} while (selection == Integer.MIN_VALUE);
	}
	
	/**
	 * edit the tags of a file to user entered tags
	 */
	private void editTags() {
		selectPath();
		if (file.isDirectory()) {
			System.out.println("Tag editing is only supported for mp3-files. \"" + file.getAbsolutePath() + "\" is a directory.");
		}else {
			Mp3Tags tags = tagEditor.readTags(file);
			System.out.println("The actual tags are: ");
			System.out.println(" Artist: " + tags.getArtist());
			System.out.println("Title: " + tags.getTitle());
			System.out.println();
			System.out.println("Do you want to change the artist?");
			if(cli.showYesNoSelection()) {
				System.out.println("Please enter the artist: "); 
				tags.setArtist(cli.consoleScanner());				
			}
			System.out.println();
			System.out.println("Do you want to change the tilte?");
			if(cli.showYesNoSelection()) {
				System.out.println("Please enter the title: "); 
				tags.setTitle(cli.consoleScanner());				
			}
			System.out.println();
			tagEditor.writeTags(file, tags);
			System.out.println("Wrote new tags to \"" + file.getAbsolutePath() + "\".");
		}
	}

	/**
	 * edit the tags with information of the filename of given files.
	 */
	private void filenameToTags() {
		selectPath();
		if (file.isDirectory()) {
			System.out.println("Do you want to search recursively in the directory?");
			searchDirectoryRecursively = cli.showYesNoSelection();
			Queue<File> filesQueue = new LinkedList<>();
			for (File file : this.file.listFiles()) {
				filesQueue.add(file);
			}
			File actualFile;
			while(!filesQueue.isEmpty()) {
				actualFile = filesQueue.remove();
				if (!actualFile.isDirectory()) {
					if (actualFile.getName().endsWith(".mp3")) {
						filenameToTags(actualFile);
					} else {
						System.out.println("File \"" + actualFile.getAbsolutePath() + "\" is no MP3");
					}
				} else if (searchDirectoryRecursively) {
					for (File newFile : actualFile.listFiles()) {
						filesQueue.add(newFile);
					}
				}
			}
		} else {
			filenameToTags(this.file);
		}
	}

	/**
	 * edit the tags with information of the filename
	 * @param file the file to be edited
	 */
	private void filenameToTags(final File file) {
		if (file.getName().endsWith(".mp3")) {
			String fileName = file.getName().substring(0, file.getName().length() - 4);
			String[] tags = fileName.split(" - ");
			Mp3Tags mp3Tags = new Mp3Tags(tags[1], tags[0]);
			this.tagEditor.writeTags(file, mp3Tags);
			System.out.println("Edited tags of \"" + file.getAbsolutePath() + "\"");
		}
	}

	
	/**
	 * let the user select a path over CLI
	 */
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

	/**
	 * edit the filename with information of the tags at given files.
	 */
	private void tagsToFilename() {
		selectPath();
		if (file.isDirectory()) {
			System.out.println("Do you want to search recursively in the directory?");
			searchDirectoryRecursively = cli.showYesNoSelection();
			Queue<File> filesQueue = new LinkedList<>();
			for (File file : this.file.listFiles()) {
				filesQueue.add(file);
			}
			File actualFile;
			while(!filesQueue.isEmpty()) {
				actualFile = filesQueue.remove();
				if (!actualFile.isDirectory()) {
					if (actualFile.getName().endsWith(".mp3")) {
						tagsToFilename(actualFile);
					} else {
						System.out.println("File \"" + actualFile.getAbsolutePath() + "\" is no MP3");
					}
				} else if (searchDirectoryRecursively) {
					for (File newFile : actualFile.listFiles()) {
						filesQueue.add(newFile);
					}
				}
			}
		} else {
			tagsToFilename(this.file);
		}
	}

	/**
	 * edit the filename with information of the tags
	 * @param file the file to be edited
	 */
	private void tagsToFilename(final File file) {
		if (file.getName().endsWith(".mp3")) {
			Mp3Tags tags = tagEditor.readTags(file);
			String oldPath = file.getAbsolutePath();
			String newName = tags.getArtist() + " - " + tags.getTitle() + ".mp3";
			try {
				renameFile(file, newName);
				System.out.println("Edited Filename of \"" + oldPath + "\" to \"" + newName + "\"");
			} catch (FileAlreadyExistsException e) {
				System.out.println("ERROR: Editing Filename of \"" + oldPath + "\" to \"" + newName + "\" aborted. File already exists!");
			}
			
		}

	}

	/**
	 * Rename the file
	 * @param file file to be renamed
	 * @param newName new name of the file
	 * @throws FileAlreadyExistsException if file already exists
	 */
	private void renameFile(final File file, final String newName) throws FileAlreadyExistsException {
		File newFilePath = new File(file.getParent() + "/" + newName);
		if(newFilePath.exists()) {
			throw new FileAlreadyExistsException(newFilePath.getAbsolutePath());
		}
		file.renameTo(newFilePath);
	}

}
