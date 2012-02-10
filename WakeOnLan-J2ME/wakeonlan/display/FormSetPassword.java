package wakeonlan.display;

import javax.microedition.lcdui.*;
import wakeonlan.WakeOnLan;

/**
 * Form set password.
 *
 * @author Jakub Trmota | Forrest79
 */
public final class FormSetPassword extends Form implements CommandListener {
	/**
	 * WakeOnLan midlet.
	 */
	private WakeOnLan wakeOnLan = null;

	/**
	 * Text field password.
	 */
	private TextField txtPassword1 = null;

	/**
	 * Text field repeat password.
	 */
	private TextField txtPassword2 = null;

	/**
	 * Info string.
	 */
  private StringItem strInfo;

	/**
	 * Command save.
	 */
	private Command cmdSave = null;

	/**
	 * Command show back.
	 */
	private Command cmdBack = null;

	/**
	 * Form set password initialization.
	 *
	 * @param wakeOnLan
	 */
	public FormSetPassword(WakeOnLan wakeOnLan) {
		super("");

		this.wakeOnLan = wakeOnLan;

		initialize();
	}

	/**
	 * Initialize components.
	 */
	public void initialize() {
		setTitle(wakeOnLan.translate("Nastavit heslo"));

		txtPassword1 = new TextField(wakeOnLan.translate("Heslo") + ":", "", 20, TextField.PASSWORD);
		txtPassword2 = new TextField(wakeOnLan.translate("Heslo znovu") + ":", "", 20, TextField.PASSWORD);
		strInfo = new StringItem("", wakeOnLan.translate("Nechce prázdné pro deaktivování ochrany heslem."));
		cmdSave = new Command(wakeOnLan.translate("Uložit"), Command.OK, 0);
		cmdBack = new Command(wakeOnLan.translate("Zpět"), Command.BACK, 1);

		append(txtPassword1);
		append(txtPassword2);
		append(strInfo);

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
	 * Clear form.
	 */
	public void clear() {
		txtPassword1.setString("");
		txtPassword2.setString("");
	}

	/**
	 * Action listener.
	 *
	 * @param c
	 * @param d
	 */
	public void commandAction(Command c, Displayable d) {
		if (c == cmdSave) {
			wakeOnLan.setPassword(txtPassword1.getString(), txtPassword2.getString());
		} else if (c == cmdBack) {
			wakeOnLan.showComputers();
		}
	}
}
