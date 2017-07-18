package patryk.songapp.enums;

/**
 * <p>
 * The language, English or Japanese, you want to use when returning search
 * results.
 * </p>
 */
public enum Lang {

	ENGLISH("en_us"),
	JAPANESE("ja_jp");
	
	private final String code;
	private Lang(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return this.code;
	}

}
