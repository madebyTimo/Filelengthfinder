package Tagger;

public class Mp3Tags {
	private String title;
	private String artist;
	
	
	public Mp3Tags(String pTitle, String pArtist) {
		title = pTitle;
		artist = pArtist;
	}
	

	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getArtist() {
		return artist;
	}


	public void setArtist(String artist) {
		this.artist = artist;
	}
	
}
