/*
	Class / Canvas
	Loading - 1.0.0 - 7.6.2007
	Jakub Trmota, jakub.trmota@forrest79.net
*/
import javax.microedition.lcdui.*;

public class Loading extends Canvas {

	private WakeOnMobile midlet = null;

	private Image iLoading = null;

	private static final int ILOADING_WIDTH = 120;
	private static final int ILOADING_HEIGHT = 40;
	private static final int ILOADING_LINE_X = 18;
	private static final int ILOADING_LINE_Y = 15;
	private static final int ILOADING_100PERCENT = 86;

	public Loading(WakeOnMobile midlet) {
		this.midlet = midlet;

		try {
			iLoading = Image.createImage("/loading.png");
		} catch(Exception e) {
		}
	}

	public void paint(Graphics g) {
		g.setColor(197, 1, 1);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(iLoading, ((getWidth() - ILOADING_WIDTH) / 2), ((getHeight() - ILOADING_HEIGHT) / 2), g.TOP | g.LEFT);

		increase(g);
	}

	private void increase(Graphics g) {
		g.setColor(255, 255, 255);
		g.drawLine((((getWidth() - ILOADING_WIDTH) / 2) + ILOADING_LINE_X), ((((getHeight() - ILOADING_HEIGHT) / 2) + ILOADING_HEIGHT) - ILOADING_LINE_Y), ((((getWidth() - ILOADING_WIDTH) / 2) + ILOADING_LINE_X) + ((ILOADING_100PERCENT * midlet.loadingPercent) / 100)), ((((getHeight() - ILOADING_HEIGHT) / 2) + ILOADING_HEIGHT) - ILOADING_LINE_Y));
		g.setColor(152, 163, 202);
		g.drawLine((((getWidth() - ILOADING_WIDTH) / 2) + ILOADING_LINE_X), ((((getHeight() - ILOADING_HEIGHT) / 2) + ILOADING_HEIGHT) - ILOADING_LINE_Y + 1), ((((getWidth() - ILOADING_WIDTH) / 2) + ILOADING_LINE_X) + ((ILOADING_100PERCENT * midlet.loadingPercent) / 100)), ((((getHeight() - ILOADING_HEIGHT) / 2) + ILOADING_HEIGHT) - ILOADING_LINE_Y + 1));
	}
}