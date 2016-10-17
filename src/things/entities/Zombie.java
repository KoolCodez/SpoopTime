package things.entities;

public class Zombie extends NPC {
	protected static final double WIDTH = 50;
	protected static final double HEIGHT = 50;
	protected static final String PATH = "Goblin.png";
	private static final double SPEED = 4;
	private static final double DAMAGE = 1;
	private static final double HEALTH = 15;
	
	public Zombie(double x, double y) {
		super(x, y, WIDTH, HEIGHT, PATH, SPEED, DAMAGE, HEALTH);
	}

}
