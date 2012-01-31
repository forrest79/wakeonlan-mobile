/*
	Class / Canvas
	Result - 1.0.0 - 10.5.2007
	Jakub Trmota, jakub.trmota@forrest79.net
*/
import javax.microedition.lcdui.*;

public class Result extends Canvas {

	private WakeOnMobile midlet = null;

	private Image iSending[] = new Image[5];

	public Result(WakeOnMobile midlet) {
		this.midlet = midlet;

		try {
			iSending[0] = Image.createImage("/sending1.png");
			iSending[1] = Image.createImage("/sending2.png");
			iSending[2] = Image.createImage("/sending3.png");
			iSending[3] = Image.createImage("/sending4.png");
			iSending[4] = Image.createImage("/sending5.png");
		} catch(Exception e) {}
	}

	protected void paint(Graphics g) {
		g.setColor(197, 1, 1);
		g.fillRect(0, 0, getWidth(), getHeight());

		sending(g);

		search(g);

		if(midlet.wakeOn.sending_status != 0) status(g);
	}

	private void search(Graphics g) {
		g.drawImage(iSending[midlet.wakeOn.sending_image], (getWidth() / 2), 5, g.HCENTER | g.TOP);
	}

	private void sending(Graphics g) {
		g.setColor(255, 255, 255);

		g.setFont(Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_SMALL));

		g.drawString(midlet.Texts.SENDING, (getWidth() / 2), 25, g.HCENTER | g.TOP);
		g.drawString(midlet.wakeOn.name + "...", (getWidth() / 2), 37, g.HCENTER | g.TOP);
	}

	private void status(Graphics g) {
		g.setFont(Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_MEDIUM));

		if(midlet.wakeOn.sending_status == 1) {
			g.setColor(255, 255, 255);
			g.drawString(midlet.Texts.SEND_SUCCESSFULY_1, (getWidth() / 2), 55, g.HCENTER | g.TOP);
			g.drawString(midlet.Texts.SEND_SUCCESSFULY_2, (getWidth() / 2), 67, g.HCENTER | g.TOP);
			g.drawString(midlet.Texts.SEND_SUCCESSFULY_3, (getWidth() / 2), 79, g.HCENTER | g.TOP);
		} else if(midlet.wakeOn.sending_status == -1) {
			g.setColor(255, 0, 0);
			g.drawString(midlet.Texts.SEND_ERROR_1, (getWidth() / 2), 55, g.HCENTER | g.TOP);
			g.drawString(midlet.Texts.SEND_ERROR_2, (getWidth() / 2), 67, g.HCENTER | g.TOP);
		}
	}
}