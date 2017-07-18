package patryk.songapp.enums;

/**
 * <p>
 * The attribute you want to search for in iTunes, relative to the specified
 * media type.
 * </p>
 * <p>
 * For example, if you want to search for an artist by name specify
 * {@code entity=allArtist&attribute=allArtistTerm}. In this example, if you
 * search for {@code term=maroon}, iTunes returns “Maroon 5” in the search
 * results, instead of all artists who have ever recorded a song with the word
 * “maroon” in the title.
 * </p>
 * <p>
 * iTunes default is all attributes associated with the specified media type.
 * </p>
 */
public enum Attribute {

	ACTOR_TERM("actorTerm"),
	ALBUM_TERM("albumTerm"),
	ALL_ARTIST_TERM("allArtistTerm"),
	ALL_TRACK_TERM("allTrackTerm"),
	ARTIST_TERM("artistTerm"),
	AUTHOR_TERM("authorTerm"),
	COMPOSER_TERM("composerTerm"),
	DESCRIPTION_TERM("descriptionTerm"),
	DIRECTOR_TERM("directorTerm"),
	FEATURE_FILM_TERM("featureFilmTerm"),
	GENRE_INDEX("genreIndex"),
	KEYWORDS_TERM("keywordsTerm"),
	LANGUAGE_TERM("languageTerm"),
	MIX_TERM("mixTerm"),
	MOVIE_ARTIST_TERM("movieArtistTerm"),
	MOVIE_TERM("movieTerm"),
	PRODUCER_TERM("producerTerm"),
	RATING_INDEX("ratingIndex"),
	RATING_TERM("ratingTerm"),
	RELEASE_YEAR_TERM("releaseYearTerm"),
	SHORT_FILM_TERM("shortFilmTerm"),
	SHOW_TERM("showTerm"),
	SOFTWARE_DEVELOPER("softwareDeveloper"),
	SONG_TERM("songTerm"),
	TITLE_TERM("titleTerm"),
	TV_EPISODE_TERM("tvEpisodeTerm"),
	TV_SEASON_TERM("tvSeasonTerm");

	private final String name;
	private Attribute(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}

}
