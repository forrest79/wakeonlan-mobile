package wakeonlan.display;

import javax.microedition.lcdui.*;
import wakeonlan.WakeOnLan;

/**
 * Form search.
 *
 * @author Jakub Trmota | Forrest79
 */
public final class ListComputers extends List implements CommandListener {
	/**
	 * WakeOnLan midlet.
	 */
	private WakeOnLan wakeOnLan = null;

	/**
	 * Command wake.
	 */
	private Command cmdWake = null;

	/**
	 * Command add.
	 */
	private Command cmdAdd = null;

	/**
	 * Command modify.
	 */
	private Command cmdModify = null;

	/**
	 * Command remove.
	 */
	private Command cmdRemove = null;

	/**
	 * Command set password.
	 */
	private Command cmdSetPassword = null;

	/**
	 * Command lang.
	 */
	private Command cmdLang = null;

	/**
	 * Command about.
	 */
	private Command cmdAbout = null;

	/**
	 * Command exit.
	 */
	private Command cmdExit = null;

	/**
	 * Form search initialization.
	 *
	 * @param wakeOnLan
	 */
	public ListComputers(WakeOnLan wakeOnLan) {
		super("", Choice.IMPLICIT);

		this.wakeOnLan = wakeOnLan;

		setSelectCommand(cmdWake);

		initialize();
	}

	/**
	 * Initialize components.
	 */
	public void initialize() {
		setTitle(wakeOnLan.translate("Probudit počítač"));

		cmdWake = new Command(wakeOnLan.translate("Probuď"), Command.OK, 0);
		cmdAdd = new Command(wakeOnLan.translate("Přidat počítač"), Command.SCREEN, 1);
		cmdModify = new Command(wakeOnLan.translate("Upravit počítač"), Command.SCREEN, 2);
		cmdRemove = new Command(wakeOnLan.translate("Vymazat počítač"), Command.SCREEN, 3);
		cmdSetPassword = new Command(wakeOnLan.translate("Nastavit heslo"), Command.SCREEN, 4);
		cmdLang = new Command(wakeOnLan.translate("Nastavit jazyk"), Command.SCREEN, 5);
		cmdAbout = new Command(wakeOnLan.translate("O WakeOnLan"), Command.SCREEN, 6);
		cmdExit = new Command(wakeOnLan.translate("Konec"), Command.BACK, 7);

		addCommand(cmdWake);
		addCommand(cmdAdd);
		addCommand(cmdModify);
		addCommand(cmdRemove);
		addCommand(cmdSetPassword);
		addCommand(cmdLang);
		addCommand(cmdAbout);
		addCommand(cmdExit);

		insert(0, wakeOnLan.translate("[Rychle probudit]"), null);
	}

	/**
	 * Reinitialize components.
	 */
	public void reinitialize() {
		removeCommand(cmdWake);
		removeCommand(cmdAdd);
		removeCommand(cmdModify);
		removeCommand(cmdRemove);
		removeCommand(cmdSetPassword);
		removeCommand(cmdLang);
		removeCommand(cmdAbout);
		removeCommand(cmdExit);

		delete(0);

		initialize();
	}

	/**
	 * Action listener.
	 *
	 * @param c
	 * @param d
	 */
	public void commandAction(Command c, Displayable d) {
		if (c == cmdExit) {
			wakeOnLan.exit();
		} else if (c == cmdLang) {
			wakeOnLan.showLang();
		} else if (c == cmdAbout) {
			wakeOnLan.showAbout();
		} else if (c == cmdWake) {
			if (getSelectedIndex() == 0) {
				wakeOnLan.showQuickWakeComputer();
			} else if (getSelectedIndex() > 0) {
				wakeOnLan.wakeComputer(getSelectedIndex());
			}
		} else if (c == cmdAdd) {
			wakeOnLan.showAddComputer();
		} else if (c == cmdModify) {
			if (getSelectedIndex() > 0) {
				wakeOnLan.showModifyComputer(getSelectedIndex());
			}
		} else if (c == cmdRemove) {
			if (getSelectedIndex() > 0) {
				wakeOnLan.showRemoveComputer(getSelectedIndex());
			}
		} else if (c == cmdSetPassword) {
			wakeOnLan.showSetPassword();
		}
	}
}
