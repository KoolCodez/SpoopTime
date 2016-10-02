package spoopTime;

import java.awt.geom.Point2D;

import javax.swing.JPanel;

public class Display extends Thread {
	public static Point2D currentLoc = new Point2D.Double(0, 0);
	
	private static DisplayPanel panel;
	public static final double SCALE = 1;
	public static final double REFRESH_RATE = 60;
	public static final long MILLISECONDS_TO_SLEEP = (long) (1000/REFRESH_RATE);
	
	public Display() {
		setUpDisplay();
	}
	
	private static void setUpDisplay() {
		panel = new DisplayPanel();
		panel.setLayout(null);
		panel.setSize((int) (1000 * SCALE), (int) (1000 * SCALE));
		Core.frame.add(panel);
	}
	
	@Override
	public void run() {
		while (Core.gamingMode) {
			synchronized (this) {
				this.notifyAll();
			}
			Core.frame.revalidate();
			Core.frame.repaint();
			double playerWidth = Control.player.getOutline().getWidth();
			double x = Control.player.getLoc().getX() - 500 + playerWidth/2;
			double y = Control.player.getLoc().getY() - 500 + playerWidth/2;
			currentLoc = new Point2D.Double(x, y);
			
			
			try {
				sleep(MILLISECONDS_TO_SLEEP);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		panel.gameOver();
	}
}
