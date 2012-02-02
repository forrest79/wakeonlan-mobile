package wakeonlan.display;

import javax.microedition.lcdui.*;
import wakeonlan.WakeOnLan;

/**
 * Form about dictionary.
 *
 * @author Jakub Trmota | Forrest79
 */
public final class FormAbout extends Form implements CommandListener {
	/**
	 * WakeOnLan midlet.
	 */
	private WakeOnLan wakeOnLan = null;

	/**
	 * String item about.
	 */
  private StringItem strAbout = null;

	/**
	 * Command button back.
	 */
	private Command cmdBack = null;

	/**
	 * Initialization form about.
	 *
	 * @param wakeOnLan
	 */
	public FormAbout(WakeOnLan wakeOnLan) {
		super("");

		this.wakeOnLan = wakeOnLan;

		initialize();
	}

	/**
	 * Initialize components.
	 */
	public void initialize() {
		setTitle(wakeOnLan.translate("O WakeOnLan"));

		strAbout = new StringItem(wakeOnLan.translate("Verze WakeOnLan") + ": " + WakeOnLan.VERSION + "\n", wakeOnLan.translate("O WakeOnLan: text"));
		cmdBack = new Command(wakeOnLan.translate("Zpět"), Command.SCREEN, 0);

		append(strAbout);
		addCommand(cmdBack);
	}

	/**
	 * Reinitialize components.
	 */
	public void reinitialize() {
		deleteAll();

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
		if (c == cmdBack) {
			wakeOnLan.back();
		}
	}
}
