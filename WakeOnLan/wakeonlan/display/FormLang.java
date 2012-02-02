package wakeonlan.display;

import javax.microedition.lcdui.*;
import wakeonlan.WakeOnLan;
import wakeonlan.locale.Locale;

/**
 * Form lang.
 *
 * @author Jakub Trmota | Forrest79
 */
public final class FormLang extends Form implements CommandListener {
	/**
	 * WakeOnLan midlet.
	 */
	private WakeOnLan wakeOnLan = null;

	/**
	 * Lang choice group.
	 */
	private ChoiceGroup chgLang = null;

	/**
	 * Command save.
	 */
	private Command cmdSave = null;

	/**
	 * Command back.
	 */
	private Command cmdBack = null;

	/**
	 * Initialize form lang.
	 *
	 * @param wakeOnLan
	 */
	public FormLang(WakeOnLan wakeOnLan) {
		super("");

		this.wakeOnLan = wakeOnLan;

		initialize();
	}

	/**
	 * Initialize components.
	 */
	public void initialize() {
		setTitle(wakeOnLan.translate("Nastavit jazyk"));

		chgLang = new ChoiceGroup(wakeOnLan.translate("Jazyk") + ":", Choice.EXCLUSIVE);
		chgLang.append(wakeOnLan.translate("česky"), null);
		chgLang.append(wakeOnLan.translate("anglicky"), null);
		cmdSave = new Command(wakeOnLan.translate("Uložit"), Command.SCREEN, 0);
		cmdBack = new Command(wakeOnLan.translate("Zpět"), Command.SCREEN, 1);

		append(chgLang);

		addCommand(cmdSave);
		addCommand(cmdBack);
	}

	/**
	 * Reinitialize components.
	 */
	public void reinitialize() {
		deleteAll();

		removeCommand(cmdSave);
		removeCommand(cmdBack);

		initialize();
	}

	/**
	 * Action listener.
	 *
	 * @param c
	 * @param d
	 */
	public void commandAction(Command c, Displayable d) {
		if (c == cmdSave) {
			wakeOnLan.setLocale(getLangChoice());
			wakeOnLan.back();
		} else if (c == cmdBack) {
			wakeOnLan.back();
		}
	}

	/**
	 * Set lang choice.
	 *
	 * @param lang
	 */
	public void setLangChoice(String lang) {
		if (lang.equals(Locale.CS)) {
			chgLang.setSelectedIndex(0, true);
		} else {
			chgLang.setSelectedIndex(1, true);
		}
	}

	/**
	 * Get lang id.
	 *
	 * @return lang id
	 */
	private String getLangChoice() {
		if (chgLang.getSelectedIndex() == 0) {
			return Locale.CS;
		} else {
			return Locale.EN;
		}
	}
}
