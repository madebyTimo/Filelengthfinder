package Tagger;

import java.io.File;
import java.io.IOException;

import org.farng.mp3.MP3File;
import org.farng.mp3.TagException;
import org.farng.mp3.id3.ID3v1;

public class Mp3TagEditor {

	private MP3File song;
	
	public Mp3TagEditor(File pSong) {
		try {
			song = new MP3File(pSong);
		}catch (IOException | TagException e) {
			e.printStackTrace();
		}		
	}
	
	public Mp3Tags readTags() {
		ID3v1 tag = song.getID3v1Tag();
		String artist = tag.getArtist();
		String title = tag.getSongTitle();
		Mp3Tags tags = new Mp3Tags(title, artist);
		
		return tags;
	}
	
	public void writeTags(Mp3Tags tags) {
		ID3v1 tag = song.getID3v1Tag();
		tag.setSongTitle(tags.getTitle());
		tag.setArtist(tags.getArtist());
	}
}
