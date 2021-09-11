package Tagger;

import java.io.File;

public class Mp3TagEditor {

	private File song;
	
	public Mp3TagEditor(File pSong) {
		song = pSong;
	}
	
	public void readTags() {
		if(!song.isDirectory()) {
			
		}
	}
}
