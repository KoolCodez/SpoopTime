package spoopTime;

import java.awt.Canvas;

import javax.swing.JFrame;
import javax.swing.JPanel;

import things.entities.Entity;
import things.entities.NPC;
import things.entities.Player;
import things.spawners.Spawner;

public class Core {
	public static JFrame frame;
	public static Display display;
	private static Control control;
	public static boolean setupMode = false;
	public static boolean gamingMode = false;
	public static double difficulty = 60;
	public static int score = 0;
	
	public static void main(String[] args) {
		setupMode();
		setUpWorld();
		
		setUpFrame();
		display = new Display();
		populateWorld();
		display.start();
	}
	
	public static void reset() {
		World.resetWorld();
		setUpWorld();
		populateWorld();
	}
	
	private static void setupMode() {
		setupMode = true;
		System.out.println("beginning setup");
		JFrame temp = new JFrame("Setup");
		temp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		temp.setSize((int) (500 * Display.SCALE + 16), (int) (500 * Display.SCALE + 38));
		temp.setVisible(true);
		SetUpPanel panel = new SetUpPanel();
		temp.add(panel);
		while (setupMode) {
			temp.revalidate();
			temp.repaint();
			try {
				Thread.sleep(Display.MILLISECONDS_TO_SLEEP);
				//System.out.println(temp);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("boop");
		temp.setVisible(false);
		System.out.println("ending set up");
	}
	
	private static void setUpFrame() {
		frame = new JFrame("Spoop Time!");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize((int) (1000 * Display.SCALE + 16), (int) (1000 * Display.SCALE + 38));
		frame.setVisible(true);
	}
	
	private static void setUpWorld() {
		gamingMode = true;
		World.createGrounds();
		World.createWalls();
		
	}
	private static void populateWorld() {
		World.createGraves(1);
		Player player = new Player();
		control = new Control(player);
		World.addLayer(player, 1);
	}
}
