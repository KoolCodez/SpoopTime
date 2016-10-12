package things.entities;

public class Zombie extends NPC {
	protected static final double WIDTH = 50;
	protected static final double HEIGHT = 50;
	protected static final String PATH = "Goblin.png";
	public Zombie(double x, double y) {
		super(x, y, WIDTH, HEIGHT, PATH);
	}

}
