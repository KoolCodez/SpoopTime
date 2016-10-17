package things.spawners;

public class Grave2 extends Spawner{
	protected static final double WIDTH = 100;
	protected static final double HEIGHT = 100;
	protected static final String REG_PATH = "tombstone2.png";
	public Grave2(double x, double y) {
		super(x, y, WIDTH, HEIGHT, REG_PATH);
	}
}
