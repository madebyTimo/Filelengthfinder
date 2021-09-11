package Tagger;

import java.io.File;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;


public class Mp3TagEditor {


	public Mp3TagEditor() {
		
	}

	public Mp3Tags readTags(File pSong) {
		return null;
	}

	
	/**
	 * Write the given MP3-Tags into the given MP3-file
	 * @param file the file to edit
	 * @param tags the tags to write
	 */
	public void writeTags(File file, Mp3Tags tags) {
		try {
			AudioFile audioFile = AudioFileIO.read(file);
			Tag tag = audioFile.getTag();
			tag.setField(FieldKey.ARTIST,tags.getArtist());
			tag.setField(FieldKey.TITLE, tags.getTitle());
			audioFile.commit();
		} catch (Exception e) {
			// TODO exception handling
			e.printStackTrace();
		}
	}
}
