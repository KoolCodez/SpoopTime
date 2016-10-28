package spoopTime;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.ConcurrentModificationException;
import java.util.Scanner;

public class Display extends Thread {
	public static Point2D currentLoc = new Point2D.Double(0, 0);
	
	private static DisplayPanel panel;
	public static double SCALE = 1;
	public static final double REFRESH_RATE = 60;
	public static final long MILLISECONDS_TO_SLEEP = (long) (1000/REFRESH_RATE);
	public static final Color ORANGE = new Color(214, 132, 0); //D68400
	public static final Color BORDER_ORANGE = new Color(189, 117, 0); //BD7500
	
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
				System.out.println("You're too slow!");
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
		//highscore();
		panel.gameOver();
		Core.frame.revalidate();
		Core.frame.repaint();
		synchronized (panel) {
			try {
				panel.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Core.frame.revalidate();
		Core.frame.repaint();
	}
	
	private void highscore() {
		File file = new File("C:/users/Nikolas Untoten/highscore.txt");
		Scanner input = null;
		PrintStream output = null;
		try {
			output = new PrintStream(file);
			input = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int last = 0;
		while (input.hasNextInt()) {
			last = input.nextInt();
			input.nextLine();
		}
	
		if (last < Core.score) {
			output.println(Core.score);
			System.out.println("1");
		} else {
			System.out.println("2");
		}
	}
}
