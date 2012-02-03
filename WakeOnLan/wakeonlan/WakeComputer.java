package wakeonlan;

import java.io.IOException;
import javax.microedition.io.Connector;
import javax.microedition.io.Datagram;
import javax.microedition.io.DatagramConnection;
import javax.microedition.lcdui.AlertType;
import wakeonlan.display.CanvasResult;

/**
 * Wake computer class.
 *
 * @author Jakub Trmota | Forrest79
 */
public final class WakeComputer {
	/**
	 * WakeOnLan midlet.
	 */
	private WakeOnLan wakeOnLan = null;

	/**
	 * Canvas with result.
	 */
	private CanvasResult result = null;

	/**
	 * Wake thread.
	 */
	private Wake wake = null;

	/**
	 * Initialize wake computer.
	 *
	 * @param wakeOnLan
	 * @param result
	 */
	public WakeComputer(WakeOnLan wakeOnLan, CanvasResult result) {
		this.wakeOnLan = wakeOnLan;
		this.result = result;
	}

	/**
	 * Wake computer.
	 *
	 * @param name
	 * @param ip
	 * @param mac
	 * @param port
	 * @throws Exception
	 */
	public void wake(String name, String ip, String mac, int port) throws Exception {
		Computers.check(wakeOnLan, name, ip, mac, port);

		result.wakeComputer(name);

		wake = new Wake(ip, mac, port);
		Thread threadWake = new Thread(wake);
		threadWake.start();
	}

	/**
	* Wake computer thread class.
	*
	* @author Jakub Trmota | Forrest79
	*/
	private class Wake extends Thread {
		/**
		 * IP address.
		 */
		private String ip;

		/**
		 * MAC address.
		 */
		private String mac;

		/**
		 * Port.
		 */
		private int port;

		/**
		 * Initialize wake thread.
		 *
		 * @param ip
		 * @param mac
		 * @param port
		 */
		private Wake(String ip, String mac, int port) {
			this.ip = ip;
			this.mac = mac.toLowerCase();
			this.port = port;
		}

		/**
		 * Run wake.
		 */
		public void run() {
			try {
				wake();
			} catch (IOException e) {
				wakeOnLan.alert(wakeOnLan.translate("Chyba"), e.getMessage(), AlertType.ERROR);
			}
		}

		/**
		 * Waking algorithm.
		 *
		 * @throws IOException
		 */
		private void wake() throws IOException {
			byte[] magicPacket = new byte[6 + (6 * 16)];

			for(int i = 0; i < 6; i++) {
				magicPacket[i] = (byte) 255; // 6x FF
			}

			byte[] decMac = new byte[6 * 16];

			String separator;
			if(mac.indexOf(":") > -1) {
				separator = ":";
			} else if (mac.indexOf("-") > -1) {
				separator = "-";
			} else {
				result.error(wakeOnLan.translate("špatná MAC adresa"));
				return;
			}

			mac = mac + separator;
			for (int x = 0; x < 6; x++) {
				if (mac.indexOf(separator) < 0) {
					result.error("špatná MAC adresa");
					return;
				}

				String partMac = mac.substring(0, mac.indexOf(separator));

				try {
					if(partMac.length() > 0) {
						decMac[x] = (byte) Integer.parseInt(partMac, 16);
					} else {
						result.error("špatná MAC adresa");
						return;
					}
				} catch(NumberFormatException e) {
					System.err.print(e);

					result.error("špatná MAC adresa");
					return;
				}

				mac = mac.substring(mac.indexOf(separator) + 1);
			}

			for(int y = 0; y < (6 * 16); y++) {
				magicPacket[6 + y] = decMac[y % 6];
			}

			DatagramConnection datagramConnection = null;

			try {
				datagramConnection = (DatagramConnection) Connector.open("datagram://" + ip + ":" + port);

				Datagram sendMagicPacket = datagramConnection.newDatagram(magicPacket, magicPacket.length, "datagram://" + ip + ":" + port);

				datagramConnection.send(sendMagicPacket);
			} catch(Exception e) {
				System.err.print(e);
				result.error("příkaz neodeslán");
			} finally {
				try {
					if(datagramConnection != null) {
						datagramConnection.close();
					}
				} catch (Exception e) {
					System.err.print(e);
					result.error("příkaz neodeslán");
				}

				// DONE :-)
				result.done();
			}
		}
	}
}