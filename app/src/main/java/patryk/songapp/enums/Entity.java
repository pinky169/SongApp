package patryk.songapp.enums;

/**
 * <p>
 * The type of results you want returned, relative to the specified media type.
 * </p>
 * <p>
 * For example: {@code movieArtist} for a movie media type search.
 * </p>
 * <p>
 * iTunes default is the track entity associated with the specified media type.
 * </p>
 */
public enum Entity {

	ALBUM("album"), 
	ALL_ARTIST("allArtist"), 
	ALL_TRACK("allTrack"), 
	AUDIOBOOK("audiobook"), 
	AUDIOBOOK_AUTHOR("audiobookAuthor"), 
	EBOOK("ebook"), 
	IPAD_SOFTWARE("iPadSoftware"), 
	MAC_SOFTWARE("macSoftware"),
	MIX("mix"), 
	MOVIE("movie"), 
	MOVIE_ARTIST("movieArtist"), 
	MUSIC_ARTIST("musicArtist"), 
	MUSIC_TRACK("musicTrack"), 
	MUSIC_VIDEO("musicVideo"), 
	PODCAST("podcast"), 
	PODCAST_AUTHOR("podcastAuthor"), 
	SHORT_FILM("shortFilm"), 
	SHORT_FILM_ARTIST("shortFilmArtist"), 
	SOFTWARE("software"), 
	SONG("song"), 
	TV_EPISODE("tvEpisode"), 
	TV_SEASON("tvSeason");

	private final String name;
	private Entity(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}

}
