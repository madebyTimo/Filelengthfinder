import java.io.File;
import java.io.FileFilter;
import java.util.Scanner;


/**
 * Fillengthfinder.
 * Finds recursively the Files with a minimum length in the given Directory.
 * 
 *
 */
public class Filelengthfinder {

	boolean aUseFullPathName = false;
	boolean aIncludeFoldernames = false;

	/**
	 * main Class of the Filelengthfinder
	 * @param pArgs Array of Configuration-Arguments
	 */
	public Filelengthfinder(final String[] pArgs) {
		String path = null;
		int length = -1;
		for (int i = 0; i < pArgs.length; i++) {
			if (pArgs[i].startsWith("--help") || pArgs[i].startsWith("-")) {
				printHelpMessage();
			} else if (pArgs[i].startsWith("-d=")) {
				path = pArgs[i].substring(3);
			} else if (pArgs[i].startsWith("-l=")) {
				length = Integer.parseInt(pArgs[i].substring(3));
			}
		}
		while (length == -1) {
			System.out.println("How long should the filename at least be?");
			length = Integer.parseInt(consoleScanner());
		}
		boolean correctInput = false;
		while (!correctInput) {
			System.out.println("Include the full path? (yes/no)");
			String input = consoleScanner();
			if (input.equals("yes")) {
				aUseFullPathName = true;
				correctInput = true;
			} else if (input.equals("no")) {
				aUseFullPathName = false;
				correctInput = true;
			}
		}
		correctInput = false;
		if (!aUseFullPathName) {
			while (!correctInput) {
				System.out.println("Also search for foldernames? (yes/no)");
				String input = consoleScanner();
				if (input.equals("yes")) {
					aIncludeFoldernames = true;
					correctInput = true;
				} else if (input.equals("no")) {
					aIncludeFoldernames = false;
					correctInput = true;
				}
			}
			correctInput = false;
		} else {
			aIncludeFoldernames = true;
		}
		while (path == null || path.length() == 0) {
			System.out.println("On which Path should be searched?");
			path = consoleScanner();
		}

		File directory = new File(path);
		String filesFound = findFileWithLengthMin(length, directory);
		System.out.println();
		if (!filesFound.equals("")) {
			System.out.println("Following files found:" + filesFound);
		} else {
			System.out.println("no files found");
		}
	}

	/**
	 * main Method of the {@link Filelengthfinder}
	 * @param args Array of Console-Arguments
	 */
	public static void main(String[] args) {
		new Filelengthfinder(args);
	}

	private void printHelpMessage() {
		System.out.println("Usage:");
		System.out.println("-d=DIRECTORY \n -l=LENGTH");
		System.out.println();
		System.out.println("Searches recursively on all Folders for files with filename with the minumum length");
	}

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
