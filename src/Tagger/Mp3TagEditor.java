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

	public void writeTags(File file, Mp3Tags tags) {
		try {
			AudioFile audioFile = AudioFileIO.read(file);
			Tag tag = audioFile.getTag();
			tag.setField(FieldKey.ARTIST,"Kings of Leon");
			audioFile.commit();
		} catch (Exception e) {
			// TODO exception handling
			e.printStackTrace();
		}
	}
}
