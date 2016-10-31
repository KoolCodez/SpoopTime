package things.decorations;

import spoopTime.World;
import things.Thing;

public class Pumpkin extends Thing {
	private static final double WIDTH = 50.0;
	private static final double HEIGHT = 50.0;
	public Pumpkin(double x, double y) {
		super(x, y, WIDTH, HEIGHT, "pumpkin.png");
	}
	@Override
	public void collide(Thing thing) {
		World.destroy(this);
		World.addLayer(new DeadPumpkin(outline.getX(), outline.getY()), layer - 1);
	}
}
