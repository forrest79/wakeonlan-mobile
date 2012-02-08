package wakeonlan.display;

import java.io.IOException;
import javax.microedition.lcdui.*;
import wakeonlan.WakeOnLan;
import wakeonlan.animation.Working;

/**
 * Result canvas.
 *
 * @author Jakub Trmota | Forrest79
 */
public class CanvasResult extends Canvas implements CommandListener {
	/**
	 * Working status.
	 */
	private static final short STATUS_WORKING = 0;

	/**
	 * Done status.
	 */
	private static final short STATUS_DONE = 1;

	/**
	 * Error status.
	 */
	private static final short STATUS_ERROR = 2;

	/**
	 * WakeOnLan midlet.
	 */
	private WakeOnLan wakeOnLan = null;

	/**
	 * Computer name.
	 */
	private String name = "";

	/**
	 * Error text.
	 */
	private String error = "";

	/**
	 * Actual status.
	 */
	private short status = STATUS_WORKING;

	/**
	 * Working animation.
	 */
	private Working working = null;

	/**
	 * Command ok.
	 */
	private Command cmdOk = null;

	/**
	 * Command exit.
	 */
	private Command cmdExit = null;

	/**
	 * Initialize canvas result.
	 *
	 * @param wakeOnLan
	 * @throws IOException
	 */
	public CanvasResult(WakeOnLan wakeOnLan) throws IOException {
		this.wakeOnLan = wakeOnLan;

		initialize();

		this.working = new Working(this, (getWidth() - Working.WIDTH) / 2, getHeight() - Working.HEIGHT - 50);
	}

	/**
	 * Initialize components.
	 */
	private void initialize() {
		cmdOk = new Command(wakeOnLan.translate("Ok"), Command.OK, 0);
		cmdExit = new Command(wakeOnLan.translate("Konec"), Command.BACK, 1);

		addCommand(cmdOk);
		addCommand(cmdExit);
	}

	/**
	 * Reinitialize components.
	 */
	public void reinitialize() {
		removeCommand(cmdOk);
		removeCommand(cmdExit);

		initialize();
	}

	/**
	 * Paint with working animation.
	 *
	 * @param g
	 */
	protected void paint(Graphics g) {
		g.setColor(197, 1, 1);
		g.fillRect(0, 0, getWidth(), getHeight());

		paintSending(g);

		paintStatus(g);

		if (working.isRunning()) {
			working.draw(g);
		}
	}

	/**
	 * Paint sending to computer text.
	 *
	 * @param g
	 */
	private void paintSending(Graphics g) {
		g.setColor(255, 255, 255);

		g.setFont(Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_SMALL));

		g.drawString(wakeOnLan.translate("Probouzím počítač") + " " + name, getWidth() / 2, 25, Graphics.HCENTER | Graphics.TOP);
	}

	/**
	 * Paint status - done or error text.
	 * @param g
	 */
	private void paintStatus(Graphics g) {
		g.setFont(Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_MEDIUM));

		if(status == STATUS_DONE) {
			g.setColor(255, 255, 255);
			g.drawString(wakeOnLan.translate("Požadavek odeslán") + "!", getWidth() / 2, 55, Graphics.HCENTER | Graphics.TOP);
		} else if(status == STATUS_ERROR) {
			g.setColor(200, 200, 200);
			g.drawString(wakeOnLan.translate("Chyba") + ": " + this.error + ".", getWidth() / 2, 55, Graphics.HCENTER | Graphics.TOP);
		}
	}

	/**
	 * Start result.
	 *
	 * @param name
	 */
	public void wakeComputer(String name) {
		status = STATUS_WORKING;
		this.name = name;
		this.error = "";
		working.restart();
		repaint();
	}

	/**
	 * Successfully done.
	 */
	public void done() {
		status = STATUS_DONE;
		working.stop();
		repaint();
	}

	/**
	 * An error occured.
	 *
	 * @param error
	 */
	public void error(String error) {
		status = STATUS_ERROR;
		this.error = error;
		working.stop();
		repaint();
	}

	/**
	 * Action listener.
	 *
	 * @param c
	 * @param d
	 */
	public void commandAction(Command c, Displayable d) {
		if (c == cmdOk) {
			wakeOnLan.showComputers();
		} else if (c == cmdExit) {
			wakeOnLan.exit();
		}
	}
}