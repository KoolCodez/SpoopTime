package spoopTime;

import javax.swing.JFrame;

import things.entities.Player;

public class Core {
	public static JFrame frame;
	public static Display display;
	private static Control control;
	public static boolean setupMode = false;
	public static boolean gamingMode = false;
	public static int score = 0;
	
	private static String[] playList = {"SpoopySkeletons.mp3", "MonsterMash.mp3"};
	
	public static void main(String[] args) {
		SoundUtil.playPlaylist(playList);
		setupMode();
		setUpWorld();
		
		setUpFrame();
		display = new Display();
		populateWorld();
		display.start();
	}
	
	public static void reset() {
		//World.resetWorld();
		//setUpWorld();
		gamingMode = true;
		World.clearEnts();
		populateWorld();
	}
	public static JFrame setupFrame;
	private static void setupMode() {
		setupMode = true;
		System.out.println("beginning setup");
		setupFrame = new JFrame("Setup");
		setupFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setupFrame.setSize(516, 538);
		setupFrame.setVisible(true);
		SetUpPanel panel = new SetUpPanel();
		setupFrame.add(panel);
		while (setupMode) {
			setupFrame.revalidate();
			setupFrame.repaint();
			try {
				Thread.sleep(Display.MILLISECONDS_TO_SLEEP);
				//System.out.println(setupFrame);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		setupFrame.setVisible(false);
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
		World.createTrees();
		World.createWalls();
		
	}
	private static void populateWorld() {
		World.createGraves(1);
		Player player = new Player();
		control = new Control(player);
		World.addLayer(player, 1);
	}
}
