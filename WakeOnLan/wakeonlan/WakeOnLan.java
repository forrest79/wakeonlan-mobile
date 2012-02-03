package wakeonlan;

import javax.microedition.lcdui.*;
import javax.microedition.midlet.MIDlet;
import javax.microedition.rms.RecordStoreException;
import wakeonlan.display.*;
import wakeonlan.locale.Locale;

public class WakeOnLan extends MIDlet {
	/**
	 * Application version.
	 */
	public static final String VERSION = "2.0.0";

	/**
	 * Application settings.
	 */
	private Settings settings = null;

	/**
	 * Computers list.
	 */
	private Computers computers = null;

	/**
	 * Application locale.
	 */
	private Locale locale = null;

	/**
	 * Wake computer class.
	 */
	private WakeComputer wakeComputer = null;

	/**
	 * Display.
	 */
	private Display display = null;

	/**
	 * Loading canvas.
	 */
	private CanvasLoading canvasLoading = null;

	/**
	 * Result canvas.
	 */
	private CanvasResult canvasResult = null;

	/**
	 * Computers list.
	 */
	private ListComputers listComputers = null;

	/**
	 * Computer form.
	 */
	private FormComputer formComputer = null;

	/**
	 * Alert remove computer.
	 */
	private AlertRemoveComputer alertRemoveComputer = null;

	/**
	 * Set password form.
	 */
	private FormSetPassword formSetPassword = null;

	/**
	 * Check password form.
	 */
	private FormCheckPassword formCheckPassword = null;

	/**
	 * Lang form.
	 */
	private FormLang formLang = null;

	/**
	 * About form.
	 */
	private FormAbout formAbout = null;

	/**
	 * Back form or canvas.
	 */
	private Displayable back = null;

	/**
	 * Alert on display.
	 */
	private Alert alert = null;

	/**
	 * Is midlet running.
	 */
	private boolean run = false;

	/**
	 * Start midlet and initilize.
	 */
	public void startApp() {
		try {
			if (!run) {
				display = Display.getDisplay(this);

				settings = new Settings(this);
				locale = new Locale(settings);

				canvasLoading = new CanvasLoading(this);
				canvasLoading.start();

				show(canvasLoading);

				canvasResult = new CanvasResult(this);
				listComputers = new ListComputers(this);
				formComputer = new FormComputer(this);
				alertRemoveComputer = new AlertRemoveComputer(this);
				formSetPassword = new FormSetPassword(this);
				formCheckPassword = new FormCheckPassword(this);
				formLang = new FormLang(this);
				formLang.setLangChoice(settings.getLocaleId());
				formAbout = new FormAbout(this);

				computers = new Computers(this, listComputers);
				wakeComputer = new WakeComputer(this, canvasResult);

				run = true;

				if (settings.getPassword().equals("")) {
					show(formCheckPassword);
				} else {
					if (settings.firstRun()) {
						show(formLang);
					} else {
						show(listComputers);
					}
				}

				canvasLoading.stop();
				canvasLoading = null;
			} else {
				settings.openRecords();
				computers.openRecords();
			}
		} catch (Exception e) {
			System.err.print(e);
			alert(translate("Chyba"), e.getMessage(), AlertType.ERROR);
		}
	}

	/**
	 * Pause midlet.
	 */
	public void pauseApp() {
		try {
			settings.closeRecords();
			computers.closeRecords();
		} catch(Exception e) {
			System.err.print(e);
		}
	}

	/**
	 * Exit midlet.
	 *
	 * @param unconditional
	 */
	public void destroyApp(boolean unconditional) {
		try {
			settings.closeRecords();
			computers.closeRecords();
		} catch(Exception e) {
			System.err.print(e);
		}

		notifyDestroyed();
	}

	/*
	// List main
	if(c == lstMain_cmdWakeOn) {
		canResult.addCommand(canResult_cmdStop);
		canResult.removeCommand(canResult_cmdOk);

		if(lstMain.getSelectedIndex() == 0) {
			frmQuick.setCommandListener(this);
			display.setCurrent(frmQuick);
		} else {
			canResult.setCommandListener(this);
			display.setCurrent(canResult);

			try {
				int selectedIndex = (lstMain.getSelectedIndex() - 1);

				wakeComputer = new WakeComputer(this, canResult, String.valueOf(computers.settName.elementAt(selectedIndex)), String.valueOf(computers.settIP.elementAt(selectedIndex)), String.valueOf(computers.settMAC.elementAt(selectedIndex)), String.valueOf(computers.settPort.elementAt(selectedIndex)));
				Thread threadWakeOn = new Thread(wakeComputer);
				threadWakeOn.start();
			} catch(Exception e) {}
		}
	} else if(c == lstMain_cmdAdd) {
		frmAdd_txtName.setString("");
		frmAdd_txtIP.setString("");
		frmAdd_txtMAC.setString("");
		frmAdd_txtPort.setString("");

		frmAdd.setCommandListener(this);
		display.setCurrent(frmAdd);
	} else if(c == lstMain_cmdModify) {
		if(lstMain.getSelectedIndex() == 0) {
			alert = new Alert(Texts.WAKEON_COMPUTER, Texts.SELECT_COMPUTER, null, AlertType.INFO);
			alert.setTimeout(2000);
			display.setCurrent(alert, lstMain);
		} else {
			int selectedIndex = (lstMain.getSelectedIndex() - 1);

			frmModify_txtName.setString(String.valueOf(computers.settName.elementAt(selectedIndex)));
			frmModify_txtIP.setString(String.valueOf(computers.settIP.elementAt(selectedIndex)));
			frmModify_txtMAC.setString(String.valueOf(computers.settMAC.elementAt(selectedIndex)));
			frmModify_txtPort.setString(String.valueOf(computers.settPort.elementAt(selectedIndex)));

			frmModify.setCommandListener(this);
			display.setCurrent(frmModify);
		}
	} else if(c == lstMain_cmdRemove) {
		if(lstMain.getSelectedIndex() == 0) {
			alert = new Alert(Texts.WAKEON_COMPUTER, Texts.SELECT_COMPUTER, null, AlertType.INFO);
			alert.setTimeout(2000);
			display.setCurrent(alert, lstMain);
		} else {
			frmRemove_strInfo.setText(Texts.REALY_REMOVE_COMPUTER + " " + String.valueOf(computers.settName.elementAt(lstMain.getSelectedIndex() - 1)) + "?");

			frmRemove.setCommandListener(this);
			display.setCurrent(frmRemove);
		}
	}

	// Form add
	if(c == frmAdd_cmdSave) {
		String strComputer = computers.saveComputer(frmAdd_txtName.getString(), frmAdd_txtIP.getString(), frmAdd_txtMAC.getString(), frmAdd_txtPort.getString(), -1);

		if(strComputer.compareTo("") == 0) {
			lstMain.setCommandListener(this);
			display.setCurrent(lstMain);
		} else {
			alert = new Alert(Texts.ADD_COMPUTER, strComputer, null, AlertType.ERROR);
			alert.setTimeout(Alert.FOREVER);
			display.setCurrent(alert, frmAdd);
		}
	} else if(c == frmAdd_cmdBack) {
		lstMain.setCommandListener(this);
		display.setCurrent(lstMain);
	}

	// Form modify
	if(c == frmModify_cmdSave) {
		String strComputer = computers.saveComputer(frmModify_txtName.getString(), frmModify_txtIP.getString(), frmModify_txtMAC.getString(), frmModify_txtPort.getString(), (lstMain.getSelectedIndex() - 1));

		if(strComputer.compareTo("") == 0) {
			lstMain.setCommandListener(this);
			display.setCurrent(lstMain);
		} else {
			alert = new Alert(Texts.MODIFY_COMPUTER, strComputer, null, AlertType.ERROR);
			alert.setTimeout(Alert.FOREVER);
			display.setCurrent(alert, frmModify);
		}
	} else if(c == frmModify_cmdBack) {
		lstMain.setCommandListener(this);
		display.setCurrent(lstMain);
	}

	// Form remove
	if(c == frmRemove_cmdYes) {
		computers.removeComputer(lstMain.getSelectedIndex() - 1);
		lstMain.setCommandListener(this);
		display.setCurrent(lstMain);
	} else if(c == frmRemove_cmdNo) {
		lstMain.setCommandListener(this);
		display.setCurrent(lstMain);
	}
	*/

	/**
	 * Show form or canvas.
	 *
	 * @param displayable
	 */
	private void show(Displayable displayable) {
		if (displayable == listComputers || displayable == canvasResult || displayable == formComputer) {
			back = displayable;
		}

		display.setCurrent(displayable);
		displayable.setCommandListener((CommandListener) displayable);
	}

	/**
	 * Return back.
	 */
	public void back() {
		if (back != null) {
			show(back);
		} else {
			show(listComputers);
		}
	}

	/**
	 * Alert on display.
	 *
	 * @param title
	 * @param text
	 * @param type
	 */
	public void alert(String title, String text, AlertType type) {
		alert = new Alert(title, text, null, type);
		alert.setTimeout(Alert.FOREVER);
		display.setCurrent(alert, back == null ? listComputers : back);
	}


	public void wakeComputer(String name, String ip, String mac, String port) {
		try {
			int iPort = Integer.parseInt(port);

			this.wakeComputer.wake(name, ip, mac, iPort);
			this.show(canvasResult);
		} catch (NumberFormatException e) {
			alert(translate("Chyba"), translate("Port musí být číslo."), AlertType.ERROR);
		} catch (Exception e) {
			alert(translate("Chyba"), e.getMessage(), AlertType.ERROR);
		}
	}

	public void wakeComputer(String ip, String mac, String port) {
		wakeComputer(ip, ip, mac, port);
	}

	public void wakeComputer(int computerIndex) {
		wakeComputer("", "", "", "");
	}

	public void addComputer(String name, String ip, String mac, String port) {
		try {
			int iPort = Integer.parseInt(port);

			if (computers.addComputer(name, ip, mac, iPort)) {
				showComputers();
			} else {
				throw new Exception(translate("Nastala chyba při přidávání počítače."));
			}
		} catch (NumberFormatException e) {
			alert(translate("Chyba"), translate("Port musí být číslo."), AlertType.ERROR);
		} catch (Exception e) {
			alert(translate("Chyba"), e.getMessage(), AlertType.ERROR);
		}
	}

	/**
	 * Set password and save to settings.
	 *
	 * @param password1
	 * @param password2
	 * @throws Exception
	 */
	public void setPassword(String password1, String password2) throws Exception {
		settings.setPassword(password1, password2);
	}

	/**
	 * Check password.
	 *
	 * @param password
	 * @return true is good password or false
	 */
	public boolean checkPassword(String password) {
		return password.equals(settings.getPassword());
	}

	/**
	 * Set new locale and save to settings.
	 *
	 * @param localeId
	 */
	public void setLocale(String localeId) {
		try {
			if (settings.setLocaleId(localeId)) {
				locale.setLocale(localeId);

				canvasResult.reinitialize();
				listComputers.reinitialize();
				formComputer.reinitialize();
				alertRemoveComputer.reinitialize();
				formSetPassword.reinitialize();
				formCheckPassword.reinitialize();
				formLang.reinitialize();
				formLang.setLangChoice(settings.getLocaleId());
				formAbout.reinitialize();
			}
		} catch (RecordStoreException e) {
			System.err.print(e);
			alert(translate("Chyba"), e.getMessage(), AlertType.ERROR);
		}
	}

	/**
	 * Show qucik wake computer.
	 */
	public void showQuickWakeComputer() {
		try {
			formComputer.action(FormComputer.COMPUTER_QUICK_WAKE);
			show(formComputer);
		} catch (Exception e) {
			alert(translate("Chyba"), e.getMessage(), AlertType.ERROR);
		}
	}

	/**
	 * Show add computer.
	 */
	public void showAddComputer() {
		try {
			formComputer.action(FormComputer.COMPUTER_ADD);
			show(formComputer);
		} catch (Exception e) {
			alert(translate("Chyba"), e.getMessage(), AlertType.ERROR);
		}
	}

	/**
	 * Show modify computer.
	 */
	public void showModifyComputer(int listIndex) {
		try {
			formComputer.action(FormComputer.COMPUTER_MODIFY);
			show(formComputer);
		} catch (Exception e) {
			alert(translate("Chyba"), e.getMessage(), AlertType.ERROR);
		}
	}

	/**
	 * Show remove computer.
	 */
	public void showRemoveComputer(int listIndex) {
		show(alertRemoveComputer);
	}

	/**
	 * Show computers list.
	 */
	public void showComputers() {
		show(listComputers);
	}

	/**
	 * Show set password form.
	 */
	public void showSetPassword() {
		show(formSetPassword);
	}

	/**
	 * Show lang form.
	 */
	public void showLang() {
		show(formLang);
	}

	/**
	 * Show about form.
	 */
	public void showAbout() {
		show(formAbout);
	}

	/**
	 * Exit midlet.
	 */
	public void exit() {
		destroyApp(true);
	}

	/**
	 * Translate word in locale.
	 *
	 * @param word
	 * @return
	 */
	public String translate(String word) {
		return locale.translate(word);
	}
}