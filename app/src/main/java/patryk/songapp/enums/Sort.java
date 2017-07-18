package patryk.songapp.enums;

import java.util.Locale;

/**
 * <p>
 * Sort order for results in an iTunes response.
 * </p>
 */
public enum Sort {

	POPULAR, RECENT;

	private final String string;

	private Sort() {
		this.string = name().toLowerCase(Locale.ENGLISH);
	}

	public String toString() {
		return this.string;
	}

}
