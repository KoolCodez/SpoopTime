package things;

public class FireBall extends Projectile {

	private static final double WIDTH = 50;
	private static final double HEIGHT = 50;

	public FireBall(double x, double y) {
		super(x, y, WIDTH, HEIGHT, "FireBall.png");
	}
	
	public void startMoving(double direction) {
		super.startMoving(direction, 20);
	}

}
