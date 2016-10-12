package things.spawners;

import java.awt.geom.Ellipse2D;
import java.util.Random;

import spoopTime.TextureUtil;

public class RandomGrave extends Spawner {
	public RandomGrave(double x, double y) {
		int randomCase = initiateRandomCase();
		outline = new Ellipse2D.Double(x, y, getWidth(randomCase), getHeight(randomCase));
		image = TextureUtil.loadImage(getTexturePath(randomCase), getWidth(randomCase), 
				getHeight(randomCase), false);
	}
	
	private static double getWidth(int random) {
		switch (random) {
		case 0:
			return Grave1.WIDTH;
		case 1:
			return Grave2.WIDTH;
		case 2:
			return Grave3.WIDTH;
		default:
			return Grave1.WIDTH;
		}
	}
	
	private static double getHeight(int random) {
		switch (random) {
		case 0:
			return Grave1.HEIGHT;
		case 1:
			return Grave2.HEIGHT;
		case 2:
			return Grave3.HEIGHT;
		default:
			return Grave1.HEIGHT;
		}
	}
	
	private static String getTexturePath(int random) {
		switch (random) {
		case 0:
			return Grave1.PATH;
		case 1:
			return Grave2.PATH;
		case 2:
			return Grave3.PATH;
		default:
			return Grave1.PATH;
		}
	}
	
	private static int initiateRandomCase() {
		Random rand = new Random();
		return rand.nextInt(3);
	}
}
