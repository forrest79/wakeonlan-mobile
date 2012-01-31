/*
	Class
	CheckVersion - 1.0.0 - 10.5.2007
	Jakub Trmota, jakub.trmota@forrest79.net
*/
import javax.microedition.lcdui.*;
import javax.microedition.io.*;
import java.io.*;

class CheckVersion extends Thread {

	private WakeOnMobile midlet = null;

	private StringItem stringItem = null;
	private String VERSION = "";
	private String URL = "";

	public CheckVersion(WakeOnMobile midlet, StringItem stringItem, String VERSION, String URL) {
		this.midlet = midlet;
		this.stringItem = stringItem;
		this.VERSION = VERSION;
		this.URL = URL;
	}

	public void run() {
		try {
			checkVersion(stringItem, VERSION, URL);
		} catch (Exception e) {}
	}

	private void checkVersion(StringItem stringItem, String VERSION, String URL) {
		String netVersion = checkNetVersion(URL);

		stringItem.setLabel(midlet.Texts.YOUR_VERSION + " " + VERSION + "\n");

		if((netVersion.compareTo("") == 0) || (netVersion.compareTo("0.0.0") == 0)) stringItem.setText(midlet.Texts.CANT_CHECK);
		else if(netVersion.compareTo(VERSION) == 0) stringItem.setText(midlet.Texts.YOU_HAVE_ACTUAL_VERSION + " " + VERSION +  ".");
		else stringItem.setText(midlet.Texts.YOU_HAVE_OLDER_VERSION + " " + netVersion + ".");
	}

	public String checkNetVersion(String URL) {
		try {
			HttpConnection connection = null;
			InputStream input = null;

			try {
				connection = (HttpConnection) Connector.open(URL + "/wakeonmobile.php");
				connection.setRequestMethod(HttpConnection.GET);

				input = connection.openInputStream();

				String version = null;

				int len = (int) connection.getLength();
				if(len > 0) {
					byte[] data = new byte[len];
					int actual = input.read(data);
					version = new String(data);
				} else {
					StringBuffer buffer = new StringBuffer();
					int ch;
					while((ch = input.read()) != -1) buffer.append((char) ch);
					version = buffer.toString();
				}

				return version.trim();
			} catch(Exception e) {
				return "";
			} finally {
				if(input != null) input.close();
				if(connection != null) connection.close();
			}
		} catch(Exception e) {
			return "";
		}
	}
}