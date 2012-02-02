package wakeonlan.display;

import java.io.IOException;
import javax.microedition.lcdui.*;
import wakeonlan.WakeOnLan;
import wakeonlan.animation.Working;

/**
 * Loading canvas.
 *
 * @author Jakub Trmota | Forrest79
 */
public final class CanvasLoading extends Canvas implements CommandListener {
	/**
	 * Welcome image width.
	 */
	public static final int WELCOME_WIDTH = 120;

	/**
	 * Welcome image height
	 */
	public static final int WELCOME_HEIGHT = 40;

	/**
	 * Dictionary midlet.
	 */
	private WakeOnLan wakeOnLan = null;

	/**
	 * Image welcome.
	 */
	private Image welcome = null;

	/**
	 * Working animation.
	 */
	private Working working = null;

	/**
	 * End command.
	 */
	private Command cmdEnd = null;

	/**
	 * Initialize canvas loading.
	 *
	 * @param wakeOnLan
	 * @throws IOException
	 */
	public CanvasLoading(WakeOnLan wakeOnLan) throws IOException {
		this.wakeOnLan = wakeOnLan;

		this.working = new Working(this, (getWidth() - Working.WIDTH) / 2, (getHeight() + Working.HEIGHT + WELCOME_HEIGHT) / 2);

		welcome = Image.createImage("/resources/welcome.png");

		cmdEnd = new Command(wakeOnLan.translate("Konec"), Command.SCREEN, 0);
		this.addCommand(cmdEnd);
	}

	/**
	 * Paing with working animation.
	 *
	 * @param g
	 */
	public void paint(Graphics g) {
		g.setColor(197, 1, 1);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(welcome, ((getWidth() - WELCOME_WIDTH) / 2), ((getHeight() - WELCOME_HEIGHT) / 2), Graphics.TOP | Graphics.LEFT);

		working.draw(g);
	}

	/**
	 * Action listener.
	 *
	 * @param c
	 * @param d
	 */
	public void commandAction(Command c, Displayable d) {
		if (c == cmdEnd) {
			wakeOnLan.destroyApp(true);
		}
	}

	/**
	 * Start working animation.
	 */
	public void start() {
		working.start();
	}

	/**
	 * Stop working animation.
	 */
	public void stop() {
		working.stop();
	}
}