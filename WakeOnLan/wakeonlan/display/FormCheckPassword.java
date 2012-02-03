package wakeonlan.display;

import javax.microedition.lcdui.*;
import wakeonlan.WakeOnLan;

/**
 * Form search.
 *
 * @author Jakub Trmota | Forrest79
 */
public final class FormCheckPassword extends Form implements CommandListener {
	/**
	 * WakeOnLan midlet.
	 */
	private WakeOnLan wakeOnLan = null;

	/**
	 * Text field password.
	 */
	private TextField txtPassword = null;

	/**
	 * Command save.
	 */
	private Command cmdCheck = null;

	/**
	 * Command show back.
	 */
	private Command cmdExit = null;

	/**
	 * Form search initialization.
	 *
	 * @param wakeOnLan
	 */
	public FormCheckPassword(WakeOnLan wakeOnLan) {
		super("");

		this.wakeOnLan = wakeOnLan;

		initialize();
	}

	/**
	 * Initialize components.
	 */
	public void initialize() {
		setTitle(wakeOnLan.translate("Zkontrolovat heslo"));

		txtPassword = new TextField(wakeOnLan.translate("Heslo") + ":", "", 20, TextField.PASSWORD);
		cmdCheck = new Command(wakeOnLan.translate("Zkontrolovat"), Command.OK, 0);
		cmdExit = new Command(wakeOnLan.translate("Konec"), Command.BACK, 1);

		append(txtPassword);

		addCommand(cmdCheck);
		addCommand(cmdExit);
	}

	/**
	 * Reinitialize components.
	 */
	public void reinitialize() {
		deleteAll();

		removeCommand(cmdCheck);
		removeCommand(cmdExit);

		initialize();
	}

	/**
	 * Action listener.
	 *
	 * @param c
	 * @param d
	 */
	public void commandAction(Command c, Displayable d) {
		if (c == cmdCheck) {
			if (wakeOnLan.checkPassword(txtPassword.getString())) {
				wakeOnLan.showComputers();
			} else {
				wakeOnLan.alert(wakeOnLan.translate("Zkontrolovat heslo"), wakeOnLan.translate("Špatně zadané heslo."), AlertType.WARNING);
			}
		} else if (c == cmdExit) {
			wakeOnLan.exit();
		}
	}
}
