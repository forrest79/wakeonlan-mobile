package wakeonlan.display;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import wakeonlan.WakeOnLan;

/**
 * Alert remove computer.
 *
 * @author Jakub Trmota | Forrest79
 */
public final class AlertRemoveComputer extends Alert implements CommandListener {
	/**
	 * WakeOnLan midlet.
	 */
	private WakeOnLan wakeOnLan = null;

	/**
	 * Command button yes.
	 */
	private Command cmdYes = null;

	/**
	 * Command button no.
	 */
	private Command cmdNo = null;

	/**
	 * Initialization alert remove computer.
	 *
	 * @param wakeOnLan
	 */
	public AlertRemoveComputer(WakeOnLan wakeOnLan) {
		super("");

		this.wakeOnLan = wakeOnLan;

		initialize();
	}

	/**
	 * Initialize components.
	 */
	public void initialize() {
		setTitle(wakeOnLan.translate("Vymazat počítač") + "?");
		setString(wakeOnLan.translate("Opravdu chcete vymazat tento počítač") + "?");

		cmdYes = new Command(wakeOnLan.translate("Ano"), Command.OK, 0);
		cmdNo = new Command(wakeOnLan.translate("Ne"), Command.BACK, 0);

		addCommand(cmdYes);
		addCommand(cmdNo);
	}

	/**
	 * Reinitialize components.
	 */
	public void reinitialize() {
		removeCommand(cmdYes);
		removeCommand(cmdNo);

		initialize();
	}

	/**
	 * Action listener.
	 *
	 * @param c
	 * @param d
	 */
	public void commandAction(Command c, Displayable d) {
		if (c == cmdYes) {
			wakeOnLan.removeComputer();
		} else {
			wakeOnLan.showComputers();
		}
	}
}
