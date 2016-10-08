package spoopTime;

import java.awt.geom.Point2D;
import java.util.ConcurrentModificationException;

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
		panel.setDoubleBuffered(true);
		Core.frame.add(panel);
	}
	private long delay = 0;
	@Override
	public void run() {
		while (true) {
			runGame();
			runGameOver();
		}
	}
	
	private void runGame() {
		while (Core.gamingMode) {
			synchronized (this) {
				this.notifyAll();
			}
			
			try {
				sleep(MILLISECONDS_TO_SLEEP - delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			long startTime = System.nanoTime();
			try {
				World.runMoves();
			} catch (ConcurrentModificationException e) {
				//e.printStackTrace();
			}
			long endTime = System.nanoTime();
			delay = (endTime - startTime) / 1000000;
			if (delay >= MILLISECONDS_TO_SLEEP) {
				delay = 0;
			}
			Core.frame.revalidate();
			Core.frame.repaint();
			
			double playerWidth = Control.player.getOutline().getWidth();
			double x = Control.player.getLoc().getX() - 500 + playerWidth/2;
			double y = Control.player.getLoc().getY() - 500 + playerWidth/2;
			currentLoc = new Point2D.Double(x, y);
		}
	}
	
	
	private void runGameOver() {
		panel.gameOver();
		synchronized (panel) {
			try {
				panel.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
