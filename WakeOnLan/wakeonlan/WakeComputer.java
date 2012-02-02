package wakeonlan;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javax.microedition.io.Connector;
import javax.microedition.io.Datagram;
import javax.microedition.io.DatagramConnection;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Canvas;
import wakeonlan.display.CanvasResult;

public final class WakeComputer {

	private WakeOnLan wakeOnLan = null;

	private CanvasResult result = null;

	public WakeComputer(WakeOnLan wakeOnLan, CanvasResult result) {
		this.wakeOnLan = wakeOnLan;
		this.result = result;
	}

	private void wakeComputer(String name, String ip, String mac, String port) {

		//result.addCommand(midlet.canResult_cmdOk);
		//result.removeCommand(midlet.canResult_cmdStop);
	}

	private class Wake extends Thread {
		/**
		 * IP address.
		 */
		private String ip;

		/**
		 * MAC address.
		 */
		private String mac;

		private int port;

		/**
		 *
		 *
		 * @param ip
		 * @param mac
		 */
		private Wake(String ip, String mac, int port) {
			this.ip = ip;
			this.mac = mac;
			this.port = port;
		}

		/**
		 * Run search.
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
				magicPacket[i] = (byte) 255;
			}

			byte[] decMac = new byte[6 * 16];

			String separator;
			if(mac.indexOf(":") > -1) {
				separator = ":";
			} else {
				separator = "-";
			}

			mac = mac + separator;
			for (int x = 0; x < 6; x++) {
				if (mac.indexOf(separator) > -1) {
					result.error();
					return;
				}

				String partMac = mac.substring(0, mac.indexOf(separator));

				try {
					if(partMac.length() > 0) {
						decMac[x] = (byte) Integer.valueOf(partMac, 16).intValue();
					}
				} catch(Exception e) {
					System.err.print(e);

					result.error();
					return;
				}

				mac = mac.substring((mac.indexOf(separator) + 1), mac.length());
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
				result.error();
			} finally {
				try {
					if(datagramConnection != null) {
						datagramConnection.close();
					}
				} catch (Exception e) {
					System.err.print(e);
				}

				result.done();
			}
		}
	}
}