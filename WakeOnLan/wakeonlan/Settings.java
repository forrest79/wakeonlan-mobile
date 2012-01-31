/*
	Class
	Settings - 1.0.0 - 10.5.2007
	Jakub Trmota, jakub.trmota@forrest79.net
*/
import javax.microedition.lcdui.*;
import javax.microedition.rms.*;
import java.util.Vector;

public class Settings {

	private WakeOnMobile midlet = null;

	private RecordStore settings = null;

	private static final int PASSWORD = 1;
	private static final int COMPUTERS = 2;

	public String settPassword = "";
	public Vector settDBIndex = null;
	public Vector settName = null;
	public Vector settIP = null;
	public Vector settMAC = null;
	public Vector settPort = null;

	public Settings(WakeOnMobile midlet) {
		this.midlet = midlet;
	}

	public void openDBSettings() {
		try {
			settings = RecordStore.openRecordStore("settings", true);
		} catch(Exception e) {}
	}

	public void closeDBSettings() throws RecordStoreNotOpenException, RecordStoreException {
		try {
			settings.closeRecordStore();
		} catch(Exception e) {}
	}

	public void loadSettings() {
		settPassword = "";
		settDBIndex = new Vector();
		settName = new Vector();
		settIP = new Vector();
		settMAC = new Vector();
		settPort = new Vector();

		try {
			if(settings.getNumRecords() == 0) {
				String passwordValue;
				byte[] byteSettingsValue = null;

				int new_settings;

				passwordValue = "";
				byteSettingsValue = passwordValue.getBytes();
				new_settings = settings.addRecord(byteSettingsValue, 0, byteSettingsValue.length); // BLANK PASSWORD
			}

			byte[] settRecord = null;

			settRecord = new byte[settings.getRecordSize(PASSWORD)];
			settings.getRecord(PASSWORD, settRecord, 0);
			settPassword = new String(settRecord);

			if(settings.getNumRecords() > 1) {
				for(int i = COMPUTERS; i <= settings.getNumRecords(); i++) {
					if(settings.getRecordSize(i) > 0) {
						settDBIndex.addElement(new Integer(i));

						settRecord = new byte[settings.getRecordSize(i)];
						settings.getRecord(i, settRecord, 0);
						settName.addElement(new String(settRecord));

						settRecord = new byte[settings.getRecordSize(i + 1)];
						settings.getRecord((i + 1), settRecord, 0);
						settIP.addElement(new String(settRecord));

						settRecord = new byte[settings.getRecordSize(i + 2)];
						settings.getRecord((i + 2), settRecord, 0);
						settMAC.addElement(new String(settRecord));

						settRecord = new byte[settings.getRecordSize(i + 3)];
						settings.getRecord((i + 3), settRecord, 0);
						settPort.addElement(new String(settRecord));
					}
					i = i + 3;
				}
			}
		} catch(Exception e) {System.out.println(e);}
	}

	public String saveComputer(String name, String ip, String mac, String port, int computer) {
		if(name.compareTo("") == 0)
			return midlet.Texts.BLANK_NAME;
		else if(ip.compareTo("") == 0)
			return midlet.Texts.BLANK_IP;
		else if(mac.compareTo("") == 0)
			return midlet.Texts.BLANK_MAC;
		else if(port.compareTo("") == 0)
			return midlet.Texts.BLANK_PORT;

		try {
			byte[] byteSettingsValue = null;

			if(computer == -1) {
				int newDBIndex = 0;

				byteSettingsValue = name.getBytes();
				newDBIndex = settings.addRecord(byteSettingsValue, 0, byteSettingsValue.length);

				byteSettingsValue = ip.getBytes();
				settings.addRecord(byteSettingsValue, 0, byteSettingsValue.length);

				byteSettingsValue = mac.getBytes();
				settings.addRecord(byteSettingsValue, 0, byteSettingsValue.length);

				byteSettingsValue = port.getBytes();
				settings.addRecord(byteSettingsValue, 0, byteSettingsValue.length);

				settDBIndex.addElement(new Integer(newDBIndex));
				settName.addElement(name);
				settIP.addElement(ip);
				settMAC.addElement(mac);
				settPort.addElement(port);
				midlet.lstMain.append(name, null);
			} else {
				int dbIndex = ((Integer) settDBIndex.elementAt(computer)).intValue();

				byteSettingsValue = name.getBytes();
				settings.setRecord(dbIndex, byteSettingsValue, 0, byteSettingsValue.length);

				byteSettingsValue = ip.getBytes();
				settings.setRecord((dbIndex + 1), byteSettingsValue, 0, byteSettingsValue.length);

				byteSettingsValue = mac.getBytes();
				settings.setRecord((dbIndex + 2), byteSettingsValue, 0, byteSettingsValue.length);

				byteSettingsValue = port.getBytes();
				settings.setRecord((dbIndex + 3), byteSettingsValue, 0, byteSettingsValue.length);

				settName.setElementAt(name, computer);
				settIP.setElementAt(ip, computer);
				settMAC.setElementAt(mac, computer);
				settPort.setElementAt(port, computer);
				midlet.lstMain.set((computer + 1), name, null);
			}
		} catch(Exception e) {
			return midlet.Texts.SAVE_ERROR;
		}

		return "";
	}

	public void removeComputer(int computer) {
		try {
			int dbIndex = ((Integer) settDBIndex.elementAt(computer)).intValue();

			settings.setRecord(dbIndex, null, 0, 0);
			settings.setRecord((dbIndex + 1), null, 0, 0);
			settings.setRecord((dbIndex + 2), null, 0, 0);
			settings.setRecord((dbIndex + 3), null, 0, 0);

			settDBIndex.removeElementAt(computer);
			settName.removeElementAt(computer);
			settIP.removeElementAt(computer);
			settMAC.removeElementAt(computer);
			settPort.removeElementAt(computer);
			midlet.lstMain.delete(computer + 1);
		} catch(Exception e) {}
	}

	public String savePassword(String password1, String password2) {
		if(password1.compareTo(password2) != 0)
			return midlet.Texts.NOT_EQUAL_PASSWORD;

		try {
			byte[] byteSettingsValue = null;

			byteSettingsValue = password1.getBytes();
			settings.setRecord(PASSWORD, byteSettingsValue, 0, byteSettingsValue.length);

			settPassword = password1;
		} catch(Exception e) {
			return midlet.Texts.SAVE_ERROR;
		}

		return "";
	}
}