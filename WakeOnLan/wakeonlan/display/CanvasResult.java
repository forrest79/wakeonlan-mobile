package wakeonlan.display;

import java.io.IOException;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import wakeonlan.WakeOnLan;
import wakeonlan.animation.Working;

public class CanvasResult extends Canvas {

	private static final short STATUS_WORKING = 0;

	private static final short STATUS_DONE = 1;
	
	private static final short STATUS_ERROR = 2;

	/**
	 * WakeOnLan midlet.
	 */
	private WakeOnLan wakeOnLan = null;

	private String name = "";

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

	public CanvasResult(WakeOnLan wakeOnLan) throws IOException {
		this.wakeOnLan = wakeOnLan;

		this.working = new Working(this, (getWidth() - Working.WIDTH) / 2, (getHeight() + Working.HEIGHT) / 2);
	}

	/**
	 * Initialize components.
	 */
	public void initialize() {
		cmdOk = new Command(wakeOnLan.translate("Ok"), Command.SCREEN, 0);
		cmdExit = new Command(wakeOnLan.translate("Konec"), Command.SCREEN, 2);

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

	protected void paint(Graphics g) {
		g.setColor(197, 1, 1);
		g.fillRect(0, 0, getWidth(), getHeight());

		paintSending(g);

		paintStatus(g);

		if (working.isRunning()) {
			working.draw(g);
		}
	}

	private void paintSending(Graphics g) {
		g.setColor(255, 255, 255);

		g.setFont(Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_SMALL));

		g.drawString(wakeOnLan.translate("Probouzím počítač") + " " + name, (getWidth() / 2), 25, g.HCENTER | g.TOP);
		//g.drawString(name + "...", (getWidth() / 2), 37, g.HCENTER | g.TOP);
	}

	private void paintStatus(Graphics g) {
		g.setFont(Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_MEDIUM));

		if(status == STATUS_DONE) {
			g.setColor(255, 255, 255);
			g.drawString(wakeOnLan.translate("Magic paket\nbyl úspěšně\nodeslán!"), (getWidth() / 2), 55, g.HCENTER | g.TOP);
			//g.drawString(midlet.Texts.SEND_SUCCESSFULY_2, (getWidth() / 2), 67, g.HCENTER | g.TOP);
			//g.drawString(midlet.Texts.SEND_SUCCESSFULY_3, (getWidth() / 2), 79, g.HCENTER | g.TOP);
		} else if(status == STATUS_ERROR) {
			g.setColor(255, 0, 0);
			g.drawString(wakeOnLan.translate("Nastala chyba při odesílání magic paketu."), (getWidth() / 2), 55, g.HCENTER | g.TOP);
			//g.drawString(midlet.Texts.SEND_ERROR_2, (getWidth() / 2), 67, g.HCENTER | g.TOP);
		}
	}

	public void wakeComputer(String name) {
		status = STATUS_WORKING;
		this.name = name;
		working.restart();
	}

	public void done() {
		status = STATUS_DONE;
		working.stop();
	}

	public void error() {
		status = STATUS_ERROR;
		working.stop();
	}

	public void stop() {
		working.stop();
	}
}