package Tagger;

import java.io.File;
import java.io.IOException;

import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;
import org.jaudiotagger.tag.id3.ID3v1Tag;


public class Mp3TagEditor {


	public Mp3TagEditor() {
		
	}

	public Mp3Tags readTags(File pSong) {
		Mp3Tags tags = null;
		try {
			MP3File song = new MP3File(pSong);
			if(song.hasID3v1Tag()) {
				ID3v1Tag tag = song.getID3v1Tag();
				tags = new Mp3Tags(tag.getFirstTitle(), tag.getFirstArtist());
			}else if(song.hasID3v2Tag()) {
				AbstractID3v2Tag tag = song.getID3v2Tag();
				tags = new Mp3Tags(tag.getFirst(FieldKey.TITLE), tag.getFirst(FieldKey.ARTIST));
			}
			
		} catch (IOException | TagException | ReadOnlyFileException | CannotReadException
				| InvalidAudioFrameException e) {
			e.printStackTrace();
		}
		return tags;
	}

	public void writeTags(File pSong, Mp3Tags tags) {

	}
}
