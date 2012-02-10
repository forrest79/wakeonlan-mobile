package wakeonlan;

import javax.microedition.lcdui.*;
import javax.microedition.midlet.MIDlet;
import javax.microedition.rms.RecordStoreException;
import wakeonlan.display.*;
import wakeonlan.locale.Locale;

public final class WakeOnLan extends MIDlet {
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
	 * Selected computer index on computers list or -1
	 */
	private int selectedComputer = -1;

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
				computers.load();
				wakeComputer = new WakeComputer(this, canvasResult);

				run = true;

				if (!settings.getPassword().equals("")) {
					show(formCheckPassword);
				} else {
					if (settings.isFirstRun()) {
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

	/**
	 * Show form or canvas.
	 *
	 * @param displayable
	 */
	private void show(Displayable displayable) {
		if (displayable == listComputers || displayable == formComputer || displayable == formCheckPassword) {
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
	 * Wake computer.
	 *
	 * @param name
	 * @param ip
	 * @param mac
	 * @param port
	 */
	private void wakeComputer(String name, String ip, String mac, String port) {
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

	/**
	 * Quick wake computer.
	 *
	 * @param ip
	 * @param mac
	 * @param port
	 */
	public void wakeComputer(String ip, String mac, String port) {
		wakeComputer(ip, ip, mac, port);
	}

	/**
	 * Wake computer with index.
	 *
	 * @param computerIndex
	 */
	public void wakeComputer(int computerIndex) {
		Computer computer = computers.get(computerIndex);
		wakeComputer(computer.getName(), computer.getIp(), computer.getMac(), String.valueOf(computer.getPort()));
	}

	/**
	 * Add computer.
	 *
	 * @param name
	 * @param ip
	 * @param mac
	 * @param port
	 */
	public void addComputer(String name, String ip, String mac, String port) {
		try {
			int iPort = Integer.parseInt(port);

			if (computers.add(name, ip, mac, iPort)) {
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
	 * Modify computer.
	 *
	 * @param name
	 * @param ip
	 * @param mac
	 * @param port
	 */
	public void modifyComputer(String name, String ip, String mac, String port) {
		try {
			if (selectedComputer == -1) {
				throw new Exception(translate("Není vybrán počítač pro úpravu."));
			}

			int iPort = Integer.parseInt(port);

			if (computers.modify(selectedComputer, name, ip, mac, iPort)) {
				showComputers();
			} else {
				throw new Exception(translate("Nastala chyba při upravování počítače."));
			}
		} catch (NumberFormatException e) {
			alert(translate("Chyba"), translate("Port musí být číslo."), AlertType.ERROR);
		} catch (Exception e) {
			alert(translate("Chyba"), e.getMessage(), AlertType.ERROR);
		} finally {
			selectedComputer = -1;
		}
	}

	/**
	 * Remove computer.
	 */
	public void removeComputer() {
		try {
			if (selectedComputer == -1) {
				throw new Exception(translate("Není vybrán počítač pro vymazání."));
			}

			if (computers.remove(selectedComputer)) {
				showComputers();
			} else {
				throw new Exception(translate("Nastala chyba při vymazávání počítače."));
			}
		} catch (Exception e) {
			alert(translate("Chyba"), e.getMessage(), AlertType.ERROR);
		} finally {
			selectedComputer = -1;
		}
	}

	/**
	 * Set password and save to settings.
	 *
	 * @param password1
	 * @param password2
	 * @throws Exception
	 */
	public void setPassword(String password1, String password2) {
		try {
			settings.setPassword(password1, password2);
			showComputers();
		} catch (Exception e) {
			alert(translate("Nastavit heslo"), e.getMessage(), AlertType.WARNING);
		}
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
		selectedComputer = listIndex;

		try {
			formComputer.action(FormComputer.COMPUTER_MODIFY);
			formComputer.setDefaults(computers.get(selectedComputer));
			show(formComputer);
		} catch (Exception e) {
			alert(translate("Chyba"), e.getMessage(), AlertType.ERROR);
		}
	}

	/**
	 * Show remove computer.
	 */
	public void showRemoveComputer(int listIndex) {
		selectedComputer = listIndex;

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
		formSetPassword.clear();
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