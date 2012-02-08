package wakeonlan;

import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreNotOpenException;
import wakeonlan.locale.Locale;

/**
 * Settings class.
 *
 * @author Jakub Trmota | Forrest79
 */
public final class Settings {
	/**
	 * Record lang id.
	 */
	private static final int LANG_RECORD_ID = 1;

	/**
	 * Record password id.
	 */
	private static final int PASSWORD_RECORD_ID = 2;

	/**
	 * WakeOnLan midlet.
	 */
	private WakeOnLan wakeOnLan = null;

	/**
	 * Records.
	 */
	private RecordStore records = null;

	/**
	 * Actual locale id.
	 */
	private String localeId = "";

	/**
	 * Actual password.
	 */
	private String password = "";

	/**
	 * Indicate first run of application.
	 */
	private boolean firstRun = false;

	/**
	 * Initialize settings.
	 *
	 * @param wakeOnLan
	 * @throws RecordStoreException
	 */
	public Settings(WakeOnLan wakeOnLan) throws RecordStoreException {
		this.wakeOnLan = wakeOnLan;

		openRecords();

		if (records.getNumRecords() == 0) {
			firstRun = true;

			// Set locale
			if (System.getProperty("microedition.locale").startsWith("cs")) {
				records.addRecord(Locale.CS.getBytes(), 0, Locale.CS.getBytes().length);
			} else {
				records.addRecord(Locale.EN.getBytes(), 0, Locale.EN.getBytes().length);
			}

			// Set password
			records.addRecord(password.getBytes(), 0, password.length());
		}

		// Load settings
		byte[] byteLocaleId = new byte[records.getRecordSize(LANG_RECORD_ID)];
		records.getRecord(LANG_RECORD_ID, byteLocaleId, 0);

		localeId = new String(byteLocaleId);

		byte[] bytePassword = new byte[records.getRecordSize(PASSWORD_RECORD_ID)];
		records.getRecord(PASSWORD_RECORD_ID, bytePassword, 0);

		password = new String(bytePassword);
	}

	/**
	 * Set locale id.
	 *
	 * @param localeId
	 * @return true if locale is changed
	 */
	public boolean setLocaleId(String localeId) throws RecordStoreException {
		if (this.localeId.equals(localeId)) {
			return false;
		}

		this.localeId = localeId;

		records.setRecord(LANG_RECORD_ID, this.localeId.getBytes(), 0, this.localeId.getBytes().length);

		return true;
	}

	/**
	 * Get locale id.
	 *
	 * @return locale id
	 */
	public String getLocaleId() {
		return localeId;
	}

	/**
	 * Set password.
	 *
	 * @param password
	 * @return true if locale is changed
	 */
	public void setPassword(String password1, String password2) throws Exception, RecordStoreException {
		if (!password1.equals(password2)) {
			throw new Exception(wakeOnLan.translate("Hesla se musí shodovat."));
		}

		if (this.password.equals(password1)) {
			return;
		}

		this.password = password1;

		records.setRecord(PASSWORD_RECORD_ID, this.password.getBytes(), 0, this.password.getBytes().length);
	}

	/**
	 * Get password.
	 *
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Indicate first run of application.
	 *
	 * @return
	 */
	public boolean isFirstRun() {
		return firstRun;
	}

	/**
	 * Open records.
	 *
	 * @throws RecordStoreException
	 */
	public void openRecords() throws RecordStoreException {
		records = RecordStore.openRecordStore("settings", true);
	}

	/**
	 * Close records.
	 *
	 * @throws RecordStoreNotOpenException
	 * @throws RecordStoreException
	 */
	public void closeRecords() throws RecordStoreNotOpenException, RecordStoreException {
		records.closeRecordStore();
	}
}
