package wakeonlan;

import java.util.Vector;
import javax.microedition.lcdui.List;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreNotOpenException;

/**
 * Computers.
 *
 * @author Jakub Trmota | Forrest79
 */
public class Computers {

	private static final int RECORD_PROPERTIES = 4;

	private WakeOnLan wakeOnLan = null;

	private List list = null;

	private RecordStore records = null;

	public Vector computers = null;

	private int count = 0;

	public Computers(WakeOnLan wakeOnLan, List list) {
		this.wakeOnLan = wakeOnLan;
		this.list = list;

		computers = new Vector();
	}

	public void loadComputers() throws RecordStoreNotOpenException, RecordStoreException {
		if(records.getNumRecords() > 0) {
			for(int i = 0; i < (records.getNumRecords() / RECORD_PROPERTIES); i++) {
				int index = i * RECORD_PROPERTIES;

				String name;
				String ip;
				String mac;
				int port;

				if(records.getRecordSize(index) > 0) {
					byte[] bName = new byte[records.getRecordSize(index)];
					records.getRecord(index, bName, 0);
					name = new String(bName);

					byte[] bIp = new byte[records.getRecordSize(index + 1)];
					records.getRecord((index + 1), bIp, 0);
					ip = new String(bIp);

					byte[] bMac = new byte[records.getRecordSize(index + 2)];
					records.getRecord((index + 2), bMac, 0);
					mac = new String(bMac);

					byte[] bPort = new byte[records.getRecordSize(index + 3)];
					records.getRecord((index + 3), bPort, 0);
					port = Integer.parseInt(new String(bPort));

					computers.addElement(new Computer(index, name, ip, mac, port));
				} else {
					computers.addElement(new Computer(index));
				}

				count++;
			}

			sortComputers();

			for (int i = 0; i < computers.size(); i++) {
				Computer computer = (Computer) computers.elementAt(i);
				if (computer.getName().equals("")) {
					break;
				}

				list.append(computer.getName(), null);
			}
		}
	}

	public String saveComputer(String name, String ip, String mac, String port, int computer) {
		//if(name.compareTo("") == 0)
		//	return midlet.Texts.BLANK_NAME;
		//else if(ip.compareTo("") == 0)
		//	return midlet.Texts.BLANK_IP;
		//else if(mac.compareTo("") == 0)
		//	return midlet.Texts.BLANK_MAC;
		//else if(port.compareTo("") == 0)
		//	return midlet.Texts.BLANK_PORT;

		try {
			byte[] byteSettingsValue;

			if(computer == -1) {
				int newDBIndex = 0;

				byteSettingsValue = name.getBytes();
				newDBIndex = records.addRecord(byteSettingsValue, 0, byteSettingsValue.length);

				byteSettingsValue = ip.getBytes();
				records.addRecord(byteSettingsValue, 0, byteSettingsValue.length);

				byteSettingsValue = mac.getBytes();
				records.addRecord(byteSettingsValue, 0, byteSettingsValue.length);

				byteSettingsValue = port.getBytes();
				records.addRecord(byteSettingsValue, 0, byteSettingsValue.length);

				//settDBIndex.addElement(new Integer(newDBIndex));
				//settName.addElement(name);
				//settIP.addElement(ip);
				//settMAC.addElement(mac);
				//settPort.addElement(port);
				//midlet.lstMain.append(name, null);
			} else {
				//int dbIndex = ((Integer) settDBIndex.elementAt(computer)).intValue();

				//byteSettingsValue = name.getBytes();
				//records.setRecord(dbIndex, byteSettingsValue, 0, byteSettingsValue.length);

				//byteSettingsValue = ip.getBytes();
				//records.setRecord((dbIndex + 1), byteSettingsValue, 0, byteSettingsValue.length);

				//byteSettingsValue = mac.getBytes();
				//records.setRecord((dbIndex + 2), byteSettingsValue, 0, byteSettingsValue.length);

				//byteSettingsValue = port.getBytes();
				//records.setRecord((dbIndex + 3), byteSettingsValue, 0, byteSettingsValue.length);

				//settName.setElementAt(name, computer);
				//settIP.setElementAt(ip, computer);
				//settMAC.setElementAt(mac, computer);
				//settPort.setElementAt(port, computer);
				//midlet.lstMain.set((computer + 1), name, null);
			}
		} catch(Exception e) {
			//return midlet.Texts.SAVE_ERROR;
		}

		return "";
	}

	public void removeComputer(int computer) {
		try {
			//int dbIndex = ((Integer) settDBIndex.elementAt(computer)).intValue();

			//records.setRecord(dbIndex, null, 0, 0);
			//records.setRecord((dbIndex + 1), null, 0, 0);
			//records.setRecord((dbIndex + 2), null, 0, 0);
			//records.setRecord((dbIndex + 3), null, 0, 0);

			//settDBIndex.removeElementAt(computer);
			//settName.removeElementAt(computer);
			//settIP.removeElementAt(computer);
			//settMAC.removeElementAt(computer);
			//settPort.removeElementAt(computer);
			//midlet.lstMain.delete(computer + 1);
		} catch(Exception e) {}
	}

	/**
	 * Sort computers alphabetically.
	 */
	private void sortComputers() {
		for (int i = 0; i < computers.size() - 1; i++) {
			for (int x = i; x < computers.size() - 1; x++) {
				Computer computer1 = (Computer) computers.elementAt(x);
				Computer computer2 = (Computer) computers.elementAt(x + 1);
				if(computer1.compareTo(computer2) < 0){
					computers.setElementAt(computer2, x);
					computers.setElementAt(computer1, x + 1);
				}
			}
		}
	}

	/**
	 * Open records.
	 *
	 * @throws RecordStoreException
	 */
	public void openRecords() throws RecordStoreException {
		records = RecordStore.openRecordStore("computers", true);
	}

	/**
	 * Close records.
	 *
	 * @throws RecordStoreNotOpenException
	 * @throws RecordStoreException
	 */
	public void closeRecords() throws RecordStoreNotOpenException, RecordStoreException {
		records.closeRecordStore();
	}

	/**
   * Form search.
	 *
	 * @author Jakub Trmota | Forrest79
	 */
	private class Computer {

		private int recordIndex = 0;

		private String name = "";

		private String ip = "";

		private String mac = "";

		private int port = 0;

		public Computer(int recordIndex) {
			this.recordIndex = recordIndex;
		}

		public Computer(int recordIndex, String name, String ip, String mac, int port) {
			this.recordIndex = recordIndex;
			this.name = name;
			this.ip = ip;
			this.mac = mac;
			this.port = port;
		}

		public int getRecordIndex() {
			return recordIndex;
		}

		public void setRecordIndex(int recordIndex) {
			this.recordIndex = recordIndex;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getIp() {
			return ip;
		}

		public void setIp(String ip) {
			this.ip = ip;
		}

		public String getMac() {
			return mac;
		}

		public void setMac(String mac) {
			this.mac = mac;
		}

		public int getPort() {
			return port;
		}

		public void setPort(int port) {
			this.port = port;
		}

		public int compareTo(Computer computer) {
			if (computer.getName().equals("")) {
				return 1;
			}

			return name.compareTo(computer.getName());
		}
	}
}
