package things.spawners;

public class Grave1 extends Spawner {
	protected static final double WIDTH = 100;
	protected static final double HEIGHT = 50;
	protected static final String PATH = "tombstone1.png";
	public Grave1(double x, double y) {
		super(x, y, WIDTH, HEIGHT, PATH);
	}

}
