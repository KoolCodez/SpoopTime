package spoopTime;

import javax.swing.JFrame;

import things.Entity;

public class Core {
	public static JFrame frame;
	public static Display display;
	private static Control control;
	public static void main(String[] args) {
		setUpFrame();
		Entity player = new Entity(0, 0, 100, 100, "Villager.png");
		control = new Control(player);
		World.addLayerOne(player);
		World.createGrounds(-1500, -1500, 30);
		display = new Display();
		display.start();
	}
	
	private static void setUpFrame() {
		frame = new JFrame("Spoop Time!");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize((int) (1000 * Display.SCALE + 16), (int) (1000 * Display.SCALE + 38));
		frame.setVisible(true);
	}
}
