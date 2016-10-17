package things.entities;

public class Skeleton extends NPC {
	
	protected static final double WIDTH = 50;
	protected static final double HEIGHT = 50;
	protected static final String PATH = "Skeleton.png";
	private static final double SPEED = 6;
	private static final double DAMAGE = 2;
	private static final double HEALTH = 5;
	
	public Skeleton(double x, double y) {
		super(x, y, WIDTH, HEIGHT, PATH, SPEED, DAMAGE, HEALTH);
	}
}
