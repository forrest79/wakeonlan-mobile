/*
	Class
	WakeOn - 1.0.0 - 10.5.2007
	Jakub Trmota, jakub.trmota@forrest79.net
*/
import javax.microedition.lcdui.*;
import javax.microedition.io.*;
import java.io.*;
import java.util.Timer;
import java.util.TimerTask;

class WakeOn extends Thread {

	private WakeOnMobile midlet = null;
	private Canvas result = null;

	String name = "";
	String ip = "";
	String mac = "";
	String port = "";

	private Timer timer_sending = null;
	private TimerTask task_sending = null;

	public boolean sending_run = false;
	public int sending_image = 0;
	public int sending_status = 0;

	public WakeOn(WakeOnMobile midlet, Canvas result, String name, String ip, String mac, String port) {
		this.midlet = midlet;
		this.result = result;

		this.name = name;
		this.ip = ip;
		this.mac = mac;
		this.port = port;
	}

	public void run() {
		try {
			wakeOn(name, ip, mac, port);
		} catch (Exception e) {}
	}

	private void wakeOn(String name, String ip, String mac, String port) {
		sending_status = 0;

		sending_run = true;

		task_sending = new ResultsSending();
		timer_sending = new Timer();
		timer_sending.schedule(task_sending, 0, 200);

		result.repaint();

		byte[] magicPacket = new byte[6 + (6 * 16)];

		for(int i = 0; i < 6; i++)
			magicPacket[i] = (byte) 255;

		byte[] decMAC = new byte[6 * 16];

		String separator = null;

		if(mac.indexOf(":") > -1)
			separator = ":";
		else
			separator = "-";

		int x = 0;
		mac = mac + separator;
		while((mac.indexOf(separator) != -1) && (x < 6)) {
			String oneMAC = mac.substring(0, mac.indexOf(separator));

			try {
				if(oneMAC.length() > 0) decMAC[x] = (byte) Integer.valueOf(oneMAC, 16).intValue();
			} catch(Exception e) {
				for(int z = 0; z < 6; z++)
					decMAC[z] = 0;

				sending_status = -1;

				break;
			}

			mac = mac.substring((mac.indexOf(separator) + 1), mac.length());

			x++;
		}

		for(int y = 0; y < (6 * 16); y++)
			magicPacket[6 + y] = decMAC[y % 6];

		DatagramConnection datagramConnection = null;

		try {
			datagramConnection = (DatagramConnection) Connector.open("datagram://" + ip + ":" + port);

			Datagram sendMagicPacket = datagramConnection.newDatagram(magicPacket, magicPacket.length, "datagram://" + ip + ":" + port);

			datagramConnection.send(sendMagicPacket);
		} catch(Exception e) {
			sending_status = -1;
		} finally {
			try {
				if(datagramConnection != null)
					datagramConnection.close();
			} catch (Exception e) {}

			if(sending_status == 0)
				sending_status = 1;
		}

		sending_run = false;
		sending_image = 0;

		timer_sending.cancel();
		timer_sending = null;
		task_sending = null;

		result.repaint();

		result.addCommand(midlet.canResult_cmdOk);
		result.removeCommand(midlet.canResult_cmdStop);
	}

	private class ResultsSending extends TimerTask {
		public void run() {
			if(sending_image == 4) sending_image = 0;
			else sending_image++;
			result.repaint();
		}
	}
}