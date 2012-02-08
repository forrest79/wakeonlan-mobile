package wakeonlan;

import java.util.Vector;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreNotOpenException;
import wakeonlan.display.ListComputers;

/**
 * Computers.
 *
 * @author Jakub Trmota | Forrest79
 */
public final class Computers {
	/**
	 * Number of record properties.
	 */
	private static final int RECORD_PROPERTIES = 4;

	/**
	 * WakeOnLan midlet.
	 */
	private WakeOnLan wakeOnLan = null;

	/**
	 * Computers list.
	 */
	private ListComputers list = null;

	/**
	 * Record store for computers.
	 */
	private RecordStore records = null;

	/**
	 * Computers array.
	 */
	public Vector computers = null;

	/**
	 * Initialize computers.
	 *
	 * @param wakeOnLan
	 * @param list
	 * @throws RecordStoreException
	 */
	public Computers(WakeOnLan wakeOnLan, ListComputers list) throws RecordStoreException {
		this.wakeOnLan = wakeOnLan;
		this.list = list;

		computers = new Vector();

		openRecords();
	}

	/**
	 * Load computers from record store.
	 *
	 * @throws RecordStoreNotOpenException
	 * @throws RecordStoreException
	 */
	public void load() throws RecordStoreNotOpenException, RecordStoreException {
		if(records.getNumRecords() > 0) {
			for(int i = 1; i <= (records.getNumRecords() / RECORD_PROPERTIES); i++) {
				int index = ((i - 1) * RECORD_PROPERTIES) + 1;

				String name;
				String ip;
				String mac;
				int port;

				if(records.getRecordSize(index) > 0) {
					byte[] bName = new byte[records.getRecordSize(index)];
					records.getRecord(index, bName, 0);
					name = new String(bName);

					byte[] bIp = new byte[records.getRecordSize(index + 1)];
					records.getRecord(index + 1, bIp, 0);
					ip = new String(bIp);

					byte[] bMac = new byte[records.getRecordSize(index + 2)];
					records.getRecord(index + 2, bMac, 0);
					mac = new String(bMac);

					byte[] bPort = new byte[records.getRecordSize(index + 3)];
					records.getRecord(index + 3, bPort, 0);
					port = Integer.parseInt(new String(bPort));

					computers.addElement(new Computer(index, name, ip, mac, port));
				} else {
					computers.addElement(new Computer(index));
				}
			}

			sort();

			updateList();
		}
	}

	/**
	 * Update computers list.
	 */
	private void updateList() {
		list.deleteAll();
		list.reinitialize();

		for (int i = 0; i < computers.size(); i++) {
			Computer computer = (Computer) computers.elementAt(i);
			if (computer.getName().equals("")) {
				break;
			}

			list.append(computer.getName(), null);
		}
	}

	/**
	 * Get computer.
	 *
	 * @param listIndex
	 * @return
	 */
	public Computer get(int listIndex) {
		return (Computer) computers.elementAt(listIndex);
	}

	/**
	 * Add computer.
	 *
	 * @param name
	 * @param ip
	 * @param mac
	 * @param port
	 * @return success
	 * @throws Exception
	 */
	public boolean add(String name, String ip, String mac, int port) throws Exception {
		check(wakeOnLan, name, ip, mac, port);

		// Search for removed computer and modify it
		int removed = searchRemoved();
		if (removed > -1) {
			return modify(removed, name, ip, mac, port);
		}

		try {
			byte[] bName = name.getBytes();
			int index = records.addRecord(bName, 0, bName.length);

			byte[] bIp = ip.getBytes();
			records.addRecord(bIp, 0, bIp.length);

			byte[] bMac = mac.getBytes();
			records.addRecord(bMac, 0, bMac.length);

			byte[] bPort = String.valueOf(port).getBytes();
			records.addRecord(bPort, 0, bPort.length);

			computers.addElement(new Computer(index, name, ip, mac, port));

			sort();

			updateList();
		} catch(Exception e) {
			System.err.print(e);

			return false;
		}

		return true;
	}

	/**
	 * Modify computer.
	 *
	 * @param listIndex
	 * @param name
	 * @param ip
	 * @param mac
	 * @param port
	 * @return success
	 * @throws Exception
	 */
	public boolean modify(int listIndex, String name, String ip, String mac, int port) throws Exception {
		check(wakeOnLan, name, ip, mac, port);

		try {
			Computer computer = (Computer) computers.elementAt(listIndex);

			int index = computer.getRecordIndex();

			byte[] bName = name.getBytes();
			records.setRecord(index, bName, 0, bName.length);
			computer.setName(name);

			byte[] bIp = ip.getBytes();
			records.setRecord(index + 1, bIp, 0, bIp.length);
			computer.setIp(ip);

			byte[] bMac = mac.getBytes();
			records.setRecord(index + 2, bMac, 0, bMac.length);
			computer.setMac(mac);

			byte[] bPort = String.valueOf(port).getBytes();
			records.setRecord(index + 3, bPort, 0, bPort.length);
			computer.setPort(port);

			computers.setElementAt(computer, listIndex);

			sort();

			updateList();
		} catch(Exception e) {
			System.err.print(e);

			return false;
		}

		return true;
	}

	/**
	 * Remove computer.
	 *
	 * @param listIndex
	 * @return success
	 */
	public boolean remove(int listIndex) {
		try {
			Computer computer = (Computer) computers.elementAt(listIndex);
			computer.remove();

			int index = computer.getRecordIndex();

			records.setRecord(index, "".getBytes(), 0, 0);
			records.setRecord(index + 1, "".getBytes(), 0, 0);
			records.setRecord(index + 2, "".getBytes(), 0, 0);
			records.setRecord(index + 3, "".getBytes(), 0, 0);

			sort();

			updateList();
		} catch(Exception e) {
			System.err.print(e);

			return false;
		}

		return true;
	}

	/**
	 * Search for removed computer and return index or -1
	 *
	 * @return index or -1
	 */
	private int searchRemoved() {
		for (int i = 0; i < computers.size(); i++) {
			if (((Computer) computers.elementAt(i)).getName().equals("")) {
				return i;
			}
		}

		return -1;
	}

	/**
	 * Sort computers alphabetically.
	 */
	private void sort() {
		for (int i = 0; i < computers.size() - 1; i++) {
			for (int x = i; x < computers.size() - 1; x++) {
				Computer computer1 = (Computer) computers.elementAt(x);
				Computer computer2 = (Computer) computers.elementAt(x + 1);
				if(computer1.compareTo(computer2) > 0) {
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
		* Check computer params.
		*
		* @param name
		* @param ip
		* @param mac
		* @param port
		* @throws Exception
		*/
	public static void check(WakeOnLan wakeOnLan, String name, String ip, String mac, int port) throws Exception {
		if (ip.equals("")) {
			throw new Exception(wakeOnLan.translate("Musíte zadat IP adresu počítače."));
		}

		if (name.equals("")) {
			throw new Exception(wakeOnLan.translate("Musíte zadat název počítače."));
		}

		if (mac.equals("")) {
			throw new Exception(wakeOnLan.translate("Musíte zadat MAC adresu počítače."));
		} else if ((mac.indexOf(":") < 0) && (mac.indexOf("-") < 0)) {
			throw new Exception(wakeOnLan.translate("MAC adresu musí mít jako oddělovač \":\" nebo \"-\"."));
		}

		if (port <= 0) {
			throw new Exception(wakeOnLan.translate("Port počítače musí být větší než 0."));
		}
	}
}
