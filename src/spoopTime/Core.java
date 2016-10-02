package spoopTime;

import javax.swing.JFrame;
import javax.swing.JPanel;

import things.Entity;
import things.NPC;
import things.Player;
import things.Spawner;

public class Core {
	public static JFrame frame;
	public static Display display;
	private static Control control;
	public static boolean setupMode = false;
	public static boolean gamingMode = false;
	public static double difficulty = 50;
	
	public static void main(String[] args) {
		setupMode();
		setUpFrame();
		reset();
	}
	
	public static void reset() {
		World.resetWorld();
		display = new Display();
		setUpWorld();
		display.start();
	}
	
	private static void setupMode() {
		setupMode = true;
		JFrame temp = new JFrame("Setup");
		temp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		temp.setSize((int) (500 * Display.SCALE + 16), (int) (500 * Display.SCALE + 38));
		temp.setVisible(true);
		SetUpPanel panel = new SetUpPanel();
		temp.add(panel);
		while (setupMode) {
			try {
				Thread.sleep(Display.MILLISECONDS_TO_SLEEP);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("boop");
		temp.setVisible(false);
	}
	
	private static void setUpFrame() {
		frame = new JFrame("Spoop Time!");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize((int) (1000 * Display.SCALE + 16), (int) (1000 * Display.SCALE + 38));
		frame.setVisible(true);
		
	}
	
	private static void setUpWorld() {
		gamingMode = true;
		Player player = new Player();
		control = new Control(player);
		World.addLayerOne(player);
		Spawner enemySpawn1 = new Spawner(-1000, -1000);
		World.addLayerOne(enemySpawn1);
		Spawner enemySpawn2 = new Spawner(-1000, 1000);
		World.addLayerOne(enemySpawn2);
		Spawner enemySpawn3 = new Spawner(1000, -1000);
		World.addLayerOne(enemySpawn3);
		Spawner enemySpawn4 = new Spawner(1000, 1000);
		World.addLayerOne(enemySpawn4);
		World.createGrounds(-1500, -1500, 3000);
		World.createWalls(-1500, -1500, 3000);
	}
}
