package wakeonlan.display;

import javax.microedition.lcdui.*;
import wakeonlan.Computer;
import wakeonlan.WakeOnLan;

/**
 * Form computer.
 *
 * @author Jakub Trmota | Forrest79
 */
public final class FormComputer extends Form implements CommandListener {
	/**
	 * Add computer.
	 */
	public static final short COMPUTER_ADD = 0;

	/**
	 * Modify computer.
	 */
	public static final short COMPUTER_MODIFY = 1;

	/**
	 * Quick wake computer.
	 */
	public static final short COMPUTER_QUICK_WAKE = 2;

	/**
	 * WakeOnLan midlet.
	 */
	private WakeOnLan wakeOnLan = null;

	/**
	 * Text field computer name.
	 */
	private TextField txtName = null;

	/**
	 * Text field computer IP address.
	 */
	private TextField txtIp = null;

	/**
	 * Text field computer MAC address.
	 */
	private TextField txtMac = null;

	/**
	 * String item MAC address info.
	 */
  private StringItem strMacInfo = null;

	/**
	 * Text field computer port.
	 */
	private TextField txtPort = null;

	/**
	 * Command wake.
	 */
	private Command cmdWake = null;

	/**
	 * Command add.
	 */
	private Command cmdAdd = null;

	/**
	 * Command save.
	 */
	private Command cmdSave = null;

	/**
	 * Command back.
	 */
	private Command cmdBack = null;

	/**
	 * Command exit.
	 */
	private Command cmdExit = null;

	/**
	 * Form computer initialization.
	 *
	 * @param wakeOnLan
	 */
	public FormComputer(WakeOnLan wakeOnLan) {
		super("");

		this.wakeOnLan = wakeOnLan;

		initialize();
	}

	/**
	 * Initialize components.
	 */
	public void initialize() {
		txtName = new TextField(wakeOnLan.translate("Název počítače") + ":", "", 20, TextField.ANY);
		txtIp = new TextField(wakeOnLan.translate("IP adresa") + ":", "", 20, TextField.ANY);
		txtMac = new TextField(wakeOnLan.translate("MAC adresa") + ":", "", 20, TextField.ANY);
		strMacInfo = new StringItem("", wakeOnLan.translate("MAC: text"));
		txtPort = new TextField(wakeOnLan.translate("Port") + ":", "", 4, TextField.NUMERIC);
		cmdWake = new Command(wakeOnLan.translate("Probudit počítač"), Command.OK, 0);
		cmdAdd = new Command(wakeOnLan.translate("Přidat počítač"), Command.OK, 0);
		cmdSave = new Command(wakeOnLan.translate("Uložit počítač"), Command.OK, 0);
		cmdBack = new Command(wakeOnLan.translate("Zpět"), Command.BACK, 1);
		cmdExit = new Command(wakeOnLan.translate("Konec"), Command.SCREEN, 2);
	}

	/**
	 * Reinitialize components.
	 */
	public void reinitialize() {
		deleteAll();

		removeCommand(cmdWake);
		removeCommand(cmdAdd);
		removeCommand(cmdSave);
		removeCommand(cmdBack);
		removeCommand(cmdExit);

		txtName.setString("");
		txtIp.setString("");
		txtMac.setString("");
		txtPort.setString("");

		initialize();
	}

	/**
	 * Prepare form to action.
	 *
	 * @param action
	 * @throws Exception
	 */
	public void action(short action) throws Exception {
		reinitialize();

		if (action == COMPUTER_ADD) {
			setTitle(wakeOnLan.translate("Přidat počítač"));

			append(txtName);
			append(txtIp);
			append(txtMac);
			append(strMacInfo);
			append(txtPort);

			addCommand(cmdAdd);
			addCommand(cmdBack);
			addCommand(cmdExit);
		} else if (action == COMPUTER_MODIFY) {
			setTitle(wakeOnLan.translate("Upravit počítač"));

			append(txtName);
			append(txtIp);
			append(txtMac);
			append(strMacInfo);
			append(txtPort);

			addCommand(cmdSave);
			addCommand(cmdBack);
			addCommand(cmdExit);
		} else if (action == COMPUTER_QUICK_WAKE) {
			setTitle(wakeOnLan.translate("Probudit počítač"));

			append(txtIp);
			append(txtMac);
			append(strMacInfo);
			append(txtPort);

			addCommand(cmdWake);
			addCommand(cmdBack);
			addCommand(cmdExit);
		} else {
			throw new Exception(wakeOnLan.translate("Špatná akce."));
		}
	}

	/**
	 * Set defaults to form.
	 *
	 * @param computer
	 */
	public void setDefaults(Computer computer) {
		txtName.setString(computer.getName());
		txtIp.setString(computer.getIp());
		txtMac.setString(computer.getMac());
		txtPort.setString(String.valueOf(computer.getPort()));
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
		} else if (c == cmdBack) {
			wakeOnLan.showComputers();
		} else if (c == cmdWake) {
			wakeOnLan.wakeComputer(txtIp.getString(), txtMac.getString(), txtPort.getString());
		} else if (c == cmdAdd) {
			wakeOnLan.addComputer(txtName.getString(), txtIp.getString(), txtMac.getString(), txtPort.getString());
		} else if (c == cmdSave) {
			wakeOnLan.modifyComputer(txtName.getString(), txtIp.getString(), txtMac.getString(), txtPort.getString());
		}
	}
}
