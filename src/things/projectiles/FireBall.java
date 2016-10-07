package things.projectiles;

import spoopTime.Display;
import things.Thing;

public class FireBall extends Projectile {

	private static final double WIDTH = 50;
	private static final double HEIGHT = 50;

	public FireBall(double x, double y, Thing shooter) {
		super(x, y, WIDTH, HEIGHT, "FireBall.png", shooter);
	}
	
	public void startMoving(double direction) {
		super.startMoving(direction, 1200/Display.REFRESH_RATE);
	}

}
