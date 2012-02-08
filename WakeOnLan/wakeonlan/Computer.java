package wakeonlan;

/**
	* Computer class.
	*
	* @author Jakub Trmota | Forrest79
	*/
public final class Computer {
	/**
	 * Index in RecordStore.
	 */
	private int recordIndex = 0;

	/**
	 * Computer name.
	 */
	private String name = "";

	/**
	 * Computer IP address.
	 */
	private String ip = "";

	/**
	 * Computer MAC address.
	 */
	private String mac = "";

	/**
	 * Computer port.
	 */
	private int port = 0;

	/**
	 * Constructor for removed records.
	 *
	 * @param recordIndex
	 */
	public Computer(int recordIndex) {
		this.recordIndex = recordIndex;
	}

	/**
	 * Constructor.
	 *
	 * @param recordIndex
	 * @param name
	 * @param ip
	 * @param mac
	 * @param port
	 */
	public Computer(int recordIndex, String name, String ip, String mac, int port) {
		this.recordIndex = recordIndex;
		this.name = name;
		this.ip = ip;
		this.mac = mac;
		this.port = port;
	}

	/**
	 * Get record index.
	 *
	 * @return
	 */
	public int getRecordIndex() {
		return recordIndex;
	}

	/**
	 * Get name.
	 *
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set name.
	 *
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get IP address.
	 *
	 * @return
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * Set IP address.
	 *
	 * @param ip
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * Get MAC address.
	 *
	 * @return
	 */
	public String getMac() {
		return mac;
	}

	/**
	 * Set MAC address.
	 *
	 * @param mac
	 */
	public void setMac(String mac) {
		this.mac = mac;
	}

	/**
	 * Get port.
	 *
	 * @return
	 */
	public int getPort() {
		return port;
	}

	/**
	 * Set port.
	 *
	 * @param port
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * Set computer as removed.
	 */
	public void remove() {
		name = "";
		ip = "";
		mac = "";
		port = 0;
	}

	/**
	 * Compare with another Computer, "" move always to end.
	 * 
	 * @param computer
	 * @return
	 */
	public int compareTo(Computer computer) {
		if (name.equals("")) {
			return 1;
		}

		return name.compareTo(computer.getName());
	}
}
