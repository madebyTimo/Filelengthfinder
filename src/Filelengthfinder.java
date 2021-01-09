import java.io.File;
import java.io.FileFilter;
import java.util.Scanner;


/**
 * Fillengthfinder.
 * Finds recursively the Files with a minimum length in the given Directory.
 */
public class Filelengthfinder {

	boolean aUseFullPathName = false;
	boolean aIncludeFoldernames = false;

	/**
	 * main Class of the Filelengthfinder
	 * @param pArgs Array of commandline-Arguments
	 */
	public Filelengthfinder(final String[] pArgs) {
		String path = null;
		int length = -1;
		boolean isSetUseFullPathName = false;
		boolean isSetIncludeFoldernames = false;
		for (int i = 0; i < pArgs.length; i++) {
			if (pArgs[i].startsWith("--help") || pArgs[i].startsWith("-h")) {
				printHelpMessage();
			} else if (pArgs[i].startsWith("-dir=")) {
				path = pArgs[i].substring(5);
			} else if (pArgs[i].startsWith("-len=")) {
				try {
					length = Integer.parseInt(pArgs[i].substring(5));
				} catch (NumberFormatException e) {
					System.out.println("The input of \"-len=\" cannot be converted to a number");
				}
			} else if (pArgs[i].startsWith("-fph=")) {
				if(pArgs[i].substring(5).equals("yes")) {
					aUseFullPathName = true;
					isSetUseFullPathName = true;
				}else if(pArgs[i].substring(5).equals("no")) {
					aUseFullPathName = false;
					isSetUseFullPathName = true;
				}
			}else if (pArgs[i].startsWith("-fdn=")) {
				if(pArgs[i].substring(5).equals("yes")) {
					aIncludeFoldernames = true;
					isSetIncludeFoldernames = true;
				}else if(pArgs[i].substring(5).equals("no")) {
					aIncludeFoldernames = false;
					isSetIncludeFoldernames = true;
				}
			}
		}
		while (length == -1) {
			System.out.println("How long should the filename at least be?");
			try {
				length = Integer.parseInt(consoleScanner());
			} catch (NumberFormatException e) {
				System.out.println("The input cannot be converted to a number");
			}
		}
		while (!isSetUseFullPathName) {
			System.out.println("Include the full path? (yes/no)");
			String input = consoleScanner();
			if (input.equals("yes")) {
				aUseFullPathName = true;
				isSetUseFullPathName = true;
			} else if (input.equals("no")) {
				aUseFullPathName = false;
				isSetUseFullPathName = true;
			}
		}
		if (!aUseFullPathName) {
			while (!isSetIncludeFoldernames) {
				System.out.println("Also search for foldernames? (yes/no)");
				String input = consoleScanner();
				if (input.equals("yes")) {
					aIncludeFoldernames = true;
					isSetIncludeFoldernames = true;
				} else if (input.equals("no")) {
					aIncludeFoldernames = false;
					isSetIncludeFoldernames = true;
				}
			}
		} else {
			aIncludeFoldernames = true;
		}
		while (path == null || path.length() == 0) {
			System.out.println("On which Path should be searched?");
			path = consoleScanner();
		}

		try {
			File directory = new File(path);
			String filesFound = findFileWithLengthMin(length, directory);
			System.out.println();
			if (!filesFound.equals("")) {
				System.out.println("Following files found:" + filesFound);
			} else {
				System.out.println("no files found");
			}
		} catch (NullPointerException e) {
			System.out.println("The path " + path + " doesn't exist.");
		}
	}

	/**
	 * main Method of the {@link Filelengthfinder}
	 * @param args Array of commandline-Arguments
	 */
	public static void main(String[] args) {
		new Filelengthfinder(args);
	}

	/**
	 * prints the help message to the commandline
	 */
	private void printHelpMessage() {
		System.out.println("Filelengthfinder");
		System.out.println("Searches recursively on all Folders for files with a filename with the minumum length");
		System.out.println();
		System.out.println("Usage:");
		System.out.println("-dir= the path of the directory in which to search");
		System.out.println("-len= the minimum length of the filename");
		System.out.println("-fph= wether to use the full path as filename or not (yes/no)");		
		System.out.println("-fdn= wether to include foldernames to the searched filenames or not (yes/no). If -fph=true then it is automaticcaly \"yes\".");
		System.out.println();
		System.out.println("Example: java -jar Filenamelengthfinder.jar -dir=/media -len=145 -fph=no -fdn=no");
	}

	/**
	 * scans the commandline for the written line
	 * @return the written line
	 */
	private String consoleScanner() {
		Scanner scanner = new Scanner(System.in);
		String eingabe = scanner.nextLine();
//		scanner.close();
		return eingabe;
	}

	
	/**
	 * searches in the given directory recursively for files with at least the given length
	 * @param pLength the minimum length of the files
	 * @param pDirectory the directory in which is searched
	 * @return a String with a line with the path of the file per found file
	 */
	private String findFileWithLengthMin(final int pLength, final File pDirectory) {
		String filesWithLengthMin = "";
		FileFilter fileLengthFilter = new FileFilter() {
			public boolean accept(File pFile) {
				boolean directoryFilter = aIncludeFoldernames || !pFile.isDirectory();
				boolean lengthFilter = false;
				if (aUseFullPathName) {
					lengthFilter = pFile.getAbsolutePath().length() >= pLength;
				} else {
					lengthFilter = pFile.getName().length() >= pLength;
				}
				return (lengthFilter && directoryFilter);
			}
		};
		FileFilter directoryFilter = new FileFilter() {
			@Override
			public boolean accept(File pFile) {
				return pFile.isDirectory();
			}
		};
		for (int i = 0; i < pDirectory.listFiles(fileLengthFilter).length; i++) {
			filesWithLengthMin = filesWithLengthMin + "\n"
					+ (pDirectory.listFiles(fileLengthFilter)[i].getAbsolutePath());
		}
		for (int i = 0; i < pDirectory.listFiles(directoryFilter).length; i++) {
			filesWithLengthMin = filesWithLengthMin
					+ findFileWithLengthMin(pLength, pDirectory.listFiles(directoryFilter)[i]);
		}
		return filesWithLengthMin;
	}
}
