package wakeonlan;

import javax.microedition.lcdui.*;
import javax.microedition.midlet.MIDlet;
import javax.microedition.rms.RecordStoreException;
import wakeonlan.display.*;
import wakeonlan.locale.Locale;

public class WakeOnLan extends MIDlet implements CommandListener {

	public static final String VERSION = "2.0.0";

	private Settings settings = null;

	private Locale locale = null;

	private WakeComputer wakeComputer = null;

	private Computers computers = null;

	private Display display = null;

	private CanvasLoading canvasLoading = null;

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
	/*
	// Form remove
  private StringItem frmRemove_strInfo;
	private Command frmRemove_cmdYes = null;
	private Command frmRemove_cmdNo = null;

			// Form remove
			frmRemove = new Form(Texts.REMOVE_COMPUTER);
				frmRemove_strInfo = new StringItem("", "");
				frmRemove_cmdYes = new Command(Texts.YES, Command.SCREEN, 0);
				frmRemove_cmdNo = new Command(Texts.NO, Command.SCREEN, 1);
				frmRemove.append(frmRemove_strInfo);
				frmRemove.addCommand(frmRemove_cmdYes);
				frmRemove.addCommand(frmRemove_cmdNo);
				frmRemove.addCommand(cmdEnd);
			loadingDone(77);
*/
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

	public void commandAction(Command c, Displayable d) {
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
		} else if(c == lstMain_cmdSetPassword) {
			frmSetPassword_txtPassword1.setString(computers.settPassword);
			frmSetPassword_txtPassword2.setString(computers.settPassword);

			frmSetPassword.setCommandListener(this);
			display.setCurrent(frmSetPassword);
		}

		// Canvas result
		if(c == canResult_cmdOk) {
			lstMain.setCommandListener(this);
			display.setCurrent(lstMain);
		} else if(c == canResult_cmdStop) {
			if(wakeComputer != null) {
				wakeComputer.interrupt();
				wakeComputer = null;
			}

			lstMain.setCommandListener(this);
			display.setCurrent(lstMain);
		}

		// Form quick
		if(c == frmQuick_cmdWakeOn) {
			try {
				canResult.setCommandListener(this);
				display.setCurrent(canResult);

				wakeComputer = new WakeComputer(this, canResult, Texts.QUICK_WAKEON, frmQuick_txtIP.getString(), frmQuick_txtMAC.getString(), frmQuick_txtPort.getString());
				Thread threadWakeOn = new Thread(wakeComputer);
				threadWakeOn.start();
			} catch(Exception e) {}
		} else if(c == frmQuick_cmdBack) {
			lstMain.setCommandListener(this);
			display.setCurrent(lstMain);
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
	}

	/**
	 * Show form or canvas.
	 *
	 * @param displayable
	 */
	private void show(Displayable displayable) {
		if (displayable == listComputers || displayable == canvasResult) {
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
	public void showModifyComputer() {
		try {
			formComputer.action(FormComputer.COMPUTER_MODIFY);
			show(formComputer);
		} catch (Exception e) {
			alert(translate("Chyba"), e.getMessage(), AlertType.ERROR);
		}
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