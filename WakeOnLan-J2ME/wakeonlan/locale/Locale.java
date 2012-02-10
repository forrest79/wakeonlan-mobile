package wakeonlan.locale;

import wakeonlan.Settings;

/**
 * Locale class.
 *
 * @author Jakub Trmota | Forrest79
 */
public final class Locale {
	/**
	 * CS lang.
	 */
	public static final String CS = "cs";

	/**
	 * EN lang.
	 */
	public static final String EN = "en";

	/**
	 * Class with translations.
	 */
	private BaseLocale locale = null;

	/**
	 * Initialize locale class and load locale id.
	 *
	 * @param settings
	 */
	public Locale(Settings settings) {
		setLocale(settings.getLocaleId());
	}

	/**
	 * Translate word.
	 *
	 * @param word
	 * @return
	 */
	public String translate(String word) {
		if (locale == null) {
			return "!" + word;
		}

		String translate = locale.translate(word);

		if (translate == null) {
			return "!" + word;
		}

		return translate;
	}

	/**
	 * Set locale.
	 *
	 * @param localeId
	 */
	public void setLocale(String localeId) {
		if (localeId.equals(CS)) {
			this.locale = new LocaleCs();
		} else {
			this.locale = new LocaleEn();
		}
	}
}
