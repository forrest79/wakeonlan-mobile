/*
	Class
	WakeOnMobil - 1.0.0 - 10.5.2007
	Jakub Trmota, jakub.trmota@forrest79.net
*/
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import java.io.*;

public class WakeOnMobile extends MIDlet implements CommandListener {
	// Constants
	private static final String VERSION = "1.0.0";
	//private static final String URL = "http://forrest79/forrest79_net/version";
	private static final String URL = "http://forrest79.net/version";
	//public TextLangCZ Texts = new TextLangCZ();
	public TextLangEN Texts = new TextLangEN();

	// Classes
	private Settings settings = null;
	public WakeOn wakeOn = null;

	// Display
	private Display disp = null;

	// Forms, lists and canvases
	private Canvas canLoading = null;
	private Form frmCheckPassword = null;
	public List lstMain = null;
	public Canvas canResult = null;
	private Form frmQuick = null;
	private Form frmAdd = null;
	private Form frmModify = null;
	private Form frmRemove = null;
	private Form frmSetPassword = null;
	private Form frmInfo = null;
	private Form frmVersion = null;

	// All
	private boolean run = false;
	private Command cmdEnd = null;
	private Alert alert = null;

	// Canvas loading
	public int loadingPercent = 0;

	// Form check password
	private TextField frmCheckPassword_txtPassword = null;
	private Command frmCheckPassword_cmdNext = null;

	// List main
	private Command lstMain_cmdWakeOn = null;
	private Command lstMain_cmdAdd = null;
	private Command lstMain_cmdModify = null;
	private Command lstMain_cmdRemove = null;
	private Command lstMain_cmdSetPassword = null;
	private Command lstMain_cmdInfo = null;

	// Canvas result
	public Command canResult_cmdOk = null;
	public Command canResult_cmdStop = null;

	// Form quick
	private TextField frmQuick_txtIP = null;
	private TextField frmQuick_txtMAC = null;
	private TextField frmQuick_txtPort = null;
	private Command frmQuick_cmdWakeOn = null;
	private Command frmQuick_cmdBack = null;

	// Form add
	private TextField frmAdd_txtName = null;
	private TextField frmAdd_txtIP = null;
	private TextField frmAdd_txtMAC = null;
	private TextField frmAdd_txtPort = null;
	private Command frmAdd_cmdSave = null;
	private Command frmAdd_cmdBack = null;

	// Form modify
	private TextField frmModify_txtName = null;
	private TextField frmModify_txtIP = null;
	private TextField frmModify_txtMAC = null;
	private TextField frmModify_txtPort = null;
	private Command frmModify_cmdSave = null;
	private Command frmModify_cmdBack = null;

	// Form remove
    private StringItem frmRemove_strInfo;
	private Command frmRemove_cmdYes = null;
	private Command frmRemove_cmdNo = null;

	// Form set password
	private TextField frmSetPassword_txtPassword1 = null;
	private TextField frmSetPassword_txtPassword2 = null;
    private StringItem frmSetPassword_strInfo;
	private Command frmSetPassword_cmdSave = null;
	private Command frmSetPassword_cmdBack = null;

	// Form info
    private StringItem frmInfo_strText;
	private Command frmInfo_cmdBack = null;
	private Command frmInfo_cmdVersion = null;

	// Form version
    private StringItem frmVersion_strText;
	private Command frmVersion_cmdBack = null;

	public WakeOnMobile() {
		// NOTHING TO DO HERE
	}

	public void startApp() {
		if(!run) {
			// Display
			disp = Display.getDisplay(this);

			// Canvas loading
			canLoading = new Loading(this);
			disp.setCurrent(canLoading);
			loadingDone(0);

			// All
			cmdEnd = new Command(Texts.END, Command.SCREEN, 10);
			loadingDone(7);

			// Canvas loading 2
			canLoading.addCommand(cmdEnd);
			loadingDone(14);

			// Set classes
			settings = new Settings(this);
			loadingDone(21);

			// Load settings
			settings.openDBSettings();
			settings.loadSettings();
			loadingDone(28);

			// Form check password
			frmCheckPassword = new Form(Texts.ENTER_PASSWORD);
				frmCheckPassword_txtPassword = new TextField(Texts.PASSWORD + ":", "", 20, TextField.PASSWORD);
				frmCheckPassword_cmdNext = new Command(Texts.NEXT, Command.SCREEN, 0);
				frmCheckPassword.append(frmCheckPassword_txtPassword);
				frmCheckPassword.addCommand(frmCheckPassword_cmdNext);
				frmCheckPassword.addCommand(cmdEnd);
			loadingDone(35);

			// List main
			lstMain = new List(Texts.WAKEONMOBILE, Choice.IMPLICIT);
				lstMain.append(Texts.QUICK_WAKEON, null);

				for(int i = 0; i < settings.settName.size(); i++)
					lstMain.append(String.valueOf(settings.settName.elementAt(i)), null);

				lstMain_cmdWakeOn = new Command(Texts.WAKEON, Command.SCREEN, 0);
				lstMain_cmdAdd = new Command(Texts.ADD_COMPUTER, Command.SCREEN, 1);
				lstMain_cmdModify = new Command(Texts.MODIFY_COMPUTER, Command.SCREEN, 2);
				lstMain_cmdRemove = new Command(Texts.REMOVE_COMPUTER, Command.SCREEN, 3);
				lstMain_cmdSetPassword = new Command(Texts.SET_PASSWORD, Command.SCREEN, 4);
				lstMain_cmdInfo = new Command(Texts.INFO, Command.SCREEN, 5);
				lstMain.addCommand(lstMain_cmdWakeOn);
				lstMain.addCommand(lstMain_cmdAdd);
				lstMain.addCommand(lstMain_cmdModify);
				lstMain.addCommand(lstMain_cmdRemove);
				lstMain.addCommand(lstMain_cmdSetPassword);
				lstMain.addCommand(lstMain_cmdInfo);
				lstMain.addCommand(cmdEnd);
			loadingDone(42);

			// Canvas wake on
			canResult = new Result(this);
				canResult_cmdOk = new Command(Texts.OK, Command.SCREEN, 0);
				canResult_cmdStop = new Command(Texts.STOP, Command.SCREEN, 1);
				canResult.addCommand(cmdEnd);
			loadingDone(49);

			// Form quick
			frmQuick = new Form(Texts.QUICK_WAKEON);
				frmQuick_txtIP = new TextField(Texts.IP + ":", "", 20, TextField.ANY);
				frmQuick_txtMAC = new TextField(Texts.MAC + ":", "", 20, TextField.ANY);
				frmQuick_txtPort = new TextField(Texts.PORT + ":", "", 4, TextField.ANY);
				frmQuick_cmdWakeOn = new Command(Texts.WAKEON, Command.SCREEN, 0);
				frmQuick_cmdBack = new Command(Texts.BACK, Command.SCREEN, 1);
				frmQuick.append(frmQuick_txtIP);
				frmQuick.append(frmQuick_txtMAC);
				frmQuick.append(frmQuick_txtPort);
				frmQuick.addCommand(frmQuick_cmdWakeOn);
				frmQuick.addCommand(frmQuick_cmdBack);
				frmQuick.addCommand(cmdEnd);
			loadingDone(56);

			// Form add
			frmAdd = new Form(Texts.ADD_COMPUTER);
				frmAdd_txtName = new TextField(Texts.NAME + ":", "", 20, TextField.ANY);
				frmAdd_txtIP = new TextField(Texts.IP + ":", "", 20, TextField.ANY);
				frmAdd_txtMAC = new TextField(Texts.MAC + ":", "", 20, TextField.ANY);
				frmAdd_txtPort = new TextField(Texts.PORT + ":", "", 4, TextField.ANY);
				frmAdd_cmdSave = new Command(Texts.SAVE, Command.SCREEN, 0);
				frmAdd_cmdBack = new Command(Texts.BACK, Command.SCREEN, 1);
				frmAdd.append(frmAdd_txtName);
				frmAdd.append(frmAdd_txtIP);
				frmAdd.append(frmAdd_txtMAC);
				frmAdd.append(frmAdd_txtPort);
				frmAdd.addCommand(frmAdd_cmdSave);
				frmAdd.addCommand(frmAdd_cmdBack);
				frmAdd.addCommand(cmdEnd);
			loadingDone(63);

			// Form modify
			frmModify = new Form(Texts.MODIFY_COMPUTER);
				frmModify_txtName = new TextField(Texts.NAME + ":", "", 20, TextField.ANY);
				frmModify_txtIP = new TextField(Texts.IP + ":", "", 20, TextField.ANY);
				frmModify_txtMAC = new TextField(Texts.MAC + ":", "", 20, TextField.ANY);
				frmModify_txtPort = new TextField(Texts.PORT + ":", "", 4, TextField.ANY);
				frmModify_cmdSave = new Command(Texts.SAVE, Command.SCREEN, 0);
				frmModify_cmdBack = new Command(Texts.BACK, Command.SCREEN, 1);
				frmModify.append(frmModify_txtName);
				frmModify.append(frmModify_txtIP);
				frmModify.append(frmModify_txtMAC);
				frmModify.append(frmModify_txtPort);
				frmModify.addCommand(frmModify_cmdSave);
				frmModify.addCommand(frmModify_cmdBack);
				frmModify.addCommand(cmdEnd);
			loadingDone(70);

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

			// Form set password
			frmSetPassword = new Form(Texts.SET_PASSWORD);
				frmSetPassword_txtPassword1 = new TextField(Texts.PASSWORD + ":", "", 20, TextField.PASSWORD);
				frmSetPassword_txtPassword2 = new TextField(Texts.PASSWORD_AGAIN + ":", "", 20, TextField.PASSWORD);
				frmSetPassword_strInfo = new StringItem("", Texts.LEAVE_BLANK_TO_DISABLE);
				frmSetPassword_cmdSave = new Command(Texts.SAVE, Command.SCREEN, 0);
				frmSetPassword_cmdBack = new Command(Texts.BACK, Command.SCREEN, 1);
				frmSetPassword.append(frmSetPassword_txtPassword1);
				frmSetPassword.append(frmSetPassword_txtPassword2);
				frmSetPassword.append(frmSetPassword_strInfo);
				frmSetPassword.addCommand(frmSetPassword_cmdSave);
				frmSetPassword.addCommand(frmSetPassword_cmdBack);
				frmSetPassword.addCommand(cmdEnd);
			loadingDone(84);

			// Form info
			frmInfo = new Form(Texts.INFORMATION);
				frmInfo_strText = new StringItem(Texts.VERSION + " " + VERSION + "\n" + "\n", Texts.HELP);
				frmInfo_cmdBack = new Command(Texts.BACK, Command.SCREEN, 0);
				frmInfo_cmdVersion = new Command(Texts.VERSION, Command.SCREEN, 1);
				frmInfo.append(frmInfo_strText);
				frmInfo.addCommand(frmInfo_cmdBack);
				frmInfo.addCommand(frmInfo_cmdVersion);
				frmInfo.addCommand(cmdEnd);
			loadingDone(91);

			// Form version
			frmVersion = new Form(Texts.VERSION);
				frmVersion_strText = new StringItem("", "");
				frmVersion_cmdBack = new Command(Texts.BACK, Command.SCREEN, 0);
				frmVersion.append(frmVersion_strText);
				frmVersion.addCommand(frmVersion_cmdBack);
				frmVersion.addCommand(cmdEnd);
			loadingDone(100);

			// Activate lstMain or frmCheckPassword
			if(settings.settPassword.length() == 0) {
				lstMain.setCommandListener(this);
				disp.setCurrent(lstMain);
			} else {
				frmCheckPassword.setCommandListener(this);
				disp.setCurrent(frmCheckPassword);
			}

			canLoading = null;

			run = true;
		} else {
			settings.openDBSettings();
		}
	}

	public void pauseApp() {
		try {
			settings.closeDBSettings();
		} catch(Exception e) {}
	}

	public void destroyApp(boolean unconditional) {
		try {
			settings.closeDBSettings();
		} catch(Exception e) {}
		notifyDestroyed();
	}

	public void commandAction(Command c, Displayable d) {
		// All
		if(c == cmdEnd) {
			destroyApp(true);
		}

		// Form check password
		if(c == frmCheckPassword_cmdNext) {
			if(settings.settPassword.compareTo(frmCheckPassword_txtPassword.getString()) == 0) {
				lstMain.setCommandListener(this);
				disp.setCurrent(lstMain);
			} else {
				alert = new Alert(Texts.ENTER_PASSWORD, Texts.WRONG_PASSWORD, null, AlertType.ERROR);
				alert.setTimeout(Alert.FOREVER);
				disp.setCurrent(alert, frmCheckPassword);
			}
		}

		// List main
		if(c == lstMain_cmdWakeOn) {
			canResult.addCommand(canResult_cmdStop);
			canResult.removeCommand(canResult_cmdOk);

			if(lstMain.getSelectedIndex() == 0) {
				frmQuick.setCommandListener(this);
				disp.setCurrent(frmQuick);
			} else {
				canResult.setCommandListener(this);
				disp.setCurrent(canResult);

				try {
					int selectedIndex = (lstMain.getSelectedIndex() - 1);

					wakeOn = new WakeOn(this, canResult, String.valueOf(settings.settName.elementAt(selectedIndex)), String.valueOf(settings.settIP.elementAt(selectedIndex)), String.valueOf(settings.settMAC.elementAt(selectedIndex)), String.valueOf(settings.settPort.elementAt(selectedIndex)));
					Thread threadWakeOn = new Thread(wakeOn);
					threadWakeOn.start();
				} catch(Exception e) {}
			}
		} else if(c == lstMain_cmdAdd) {
			frmAdd_txtName.setString("");
			frmAdd_txtIP.setString("");
			frmAdd_txtMAC.setString("");
			frmAdd_txtPort.setString("");

			frmAdd.setCommandListener(this);
			disp.setCurrent(frmAdd);
		} else if(c == lstMain_cmdModify) {
			if(lstMain.getSelectedIndex() == 0) {
				alert = new Alert(Texts.WAKEON_COMPUTER, Texts.SELECT_COMPUTER, null, AlertType.INFO);
				alert.setTimeout(2000);
				disp.setCurrent(alert, lstMain);
			} else {
				int selectedIndex = (lstMain.getSelectedIndex() - 1);

				frmModify_txtName.setString(String.valueOf(settings.settName.elementAt(selectedIndex)));
				frmModify_txtIP.setString(String.valueOf(settings.settIP.elementAt(selectedIndex)));
				frmModify_txtMAC.setString(String.valueOf(settings.settMAC.elementAt(selectedIndex)));
				frmModify_txtPort.setString(String.valueOf(settings.settPort.elementAt(selectedIndex)));

				frmModify.setCommandListener(this);
				disp.setCurrent(frmModify);
			}
		} else if(c == lstMain_cmdRemove) {
			if(lstMain.getSelectedIndex() == 0) {
				alert = new Alert(Texts.WAKEON_COMPUTER, Texts.SELECT_COMPUTER, null, AlertType.INFO);
				alert.setTimeout(2000);
				disp.setCurrent(alert, lstMain);
			} else {
				frmRemove_strInfo.setText(Texts.REALY_REMOVE_COMPUTER + " " + String.valueOf(settings.settName.elementAt(lstMain.getSelectedIndex() - 1)) + "?");

				frmRemove.setCommandListener(this);
				disp.setCurrent(frmRemove);
			}
		} else if(c == lstMain_cmdSetPassword) {
			frmSetPassword_txtPassword1.setString(settings.settPassword);
			frmSetPassword_txtPassword2.setString(settings.settPassword);

			frmSetPassword.setCommandListener(this);
			disp.setCurrent(frmSetPassword);
		} else if(c == lstMain_cmdInfo) {
			frmInfo.setCommandListener(this);
			disp.setCurrent(frmInfo);
		}

		// Canvas result
		if(c == canResult_cmdOk) {
			lstMain.setCommandListener(this);
			disp.setCurrent(lstMain);
		} else if(c == canResult_cmdStop) {
			if(wakeOn != null) {
				wakeOn.interrupt();
				wakeOn = null;
			}

			lstMain.setCommandListener(this);
			disp.setCurrent(lstMain);
		}

		// Form quick
		if(c == frmQuick_cmdWakeOn) {
			try {
				canResult.setCommandListener(this);
				disp.setCurrent(canResult);

				wakeOn = new WakeOn(this, canResult, Texts.QUICK_WAKEON, frmQuick_txtIP.getString(), frmQuick_txtMAC.getString(), frmQuick_txtPort.getString());
				Thread threadWakeOn = new Thread(wakeOn);
				threadWakeOn.start();
			} catch(Exception e) {}
		} else if(c == frmQuick_cmdBack) {
			lstMain.setCommandListener(this);
			disp.setCurrent(lstMain);
		}

		// Form add
		if(c == frmAdd_cmdSave) {
			String strComputer = settings.saveComputer(frmAdd_txtName.getString(), frmAdd_txtIP.getString(), frmAdd_txtMAC.getString(), frmAdd_txtPort.getString(), -1);

			if(strComputer.compareTo("") == 0) {
				lstMain.setCommandListener(this);
				disp.setCurrent(lstMain);
			} else {
				alert = new Alert(Texts.ADD_COMPUTER, strComputer, null, AlertType.ERROR);
				alert.setTimeout(Alert.FOREVER);
				disp.setCurrent(alert, frmAdd);
			}
		} else if(c == frmAdd_cmdBack) {
			lstMain.setCommandListener(this);
			disp.setCurrent(lstMain);
		}

		// Form modify
		if(c == frmModify_cmdSave) {
			String strComputer = settings.saveComputer(frmModify_txtName.getString(), frmModify_txtIP.getString(), frmModify_txtMAC.getString(), frmModify_txtPort.getString(), (lstMain.getSelectedIndex() - 1));

			if(strComputer.compareTo("") == 0) {
				lstMain.setCommandListener(this);
				disp.setCurrent(lstMain);
			} else {
				alert = new Alert(Texts.MODIFY_COMPUTER, strComputer, null, AlertType.ERROR);
				alert.setTimeout(Alert.FOREVER);
				disp.setCurrent(alert, frmModify);
			}
		} else if(c == frmModify_cmdBack) {
			lstMain.setCommandListener(this);
			disp.setCurrent(lstMain);
		}

		// Form remove
		if(c == frmRemove_cmdYes) {
			settings.removeComputer(lstMain.getSelectedIndex() - 1);
			lstMain.setCommandListener(this);
			disp.setCurrent(lstMain);
		} else if(c == frmRemove_cmdNo) {
			lstMain.setCommandListener(this);
			disp.setCurrent(lstMain);
		}

		// Form set password
		if(c == frmSetPassword_cmdSave) {
			String strPassword = settings.savePassword(frmSetPassword_txtPassword1.getString(), frmSetPassword_txtPassword2.getString());

			if(strPassword.compareTo("") == 0) {
				lstMain.setCommandListener(this);
				disp.setCurrent(lstMain);
			} else {
				alert = new Alert(Texts.SET_PASSWORD, strPassword, null, AlertType.ERROR);
				alert.setTimeout(5000);
				disp.setCurrent(alert, frmSetPassword);
			}
		} else if(c == frmSetPassword_cmdBack) {
			lstMain.setCommandListener(this);
			disp.setCurrent(lstMain);
		}

		// Form info
		if(c == frmInfo_cmdBack) {
			lstMain.setCommandListener(this);
			disp.setCurrent(lstMain);
		} else if(c == frmInfo_cmdVersion) {
			frmVersion.setCommandListener(this);
			frmVersion_strText.setText(Texts.CHECKING_ACTUAL_VERSION);
			disp.setCurrent(frmVersion);

			try {
				CheckVersion checkVersion = new CheckVersion(this, frmVersion_strText, VERSION, URL);
				Thread threadCheckVersion = new Thread(checkVersion);
				threadCheckVersion.start();
			} catch(Exception e) {}
		}

		// Form version
		if(c == frmVersion_cmdBack) {
			frmInfo.setCommandListener(this);
			disp.setCurrent(frmInfo);
		}
	}

	// Loading
	public void loadingDone(int percent) {
		loadingPercent = percent;
		canLoading.repaint();
	}
}