package things.decorations;

import things.Thing;

public class DeadPumpkin extends Thing {
	private static final double WIDTH = 50.0;
	private static final double HEIGHT = 50.0;
	public DeadPumpkin(double x, double y) {
		super(x, y, WIDTH, HEIGHT, "deadpumpkin.png");
	}
}
