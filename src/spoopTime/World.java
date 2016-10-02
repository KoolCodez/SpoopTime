package spoopTime;

import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

import things.Grass;
import things.Thing;

public class World {
	public static Layer[] layers = { new Layer(), new Layer(), new Layer() };

	public static void addLayerZero(Thing t) {
		layers[0].things.add(t);
		t.layer = 0;
	}

	public static void addLayerOne(Thing t) {
		layers[1].things.add(t);
		t.layer = 1;
	}

	public static void addLayerTwo(Thing t) {
		layers[2].things.add(t);
		t.layer = 2;
	}

	public static boolean legalMove(double deltaX, double deltaY, Thing thing) {
		if (layers[thing.layer].things.contains(thing)) {
			Ellipse2D e = thing.getOutline();
			Ellipse2D temp = new Ellipse2D.Double(e.getX() + deltaX, e.getY() + deltaY, e.getWidth(), e.getHeight());
			return layers[thing.layer].legalMove(temp, thing);
		}
		return true;
	}
	
	public static void createGrounds(int startingX, int startingY, int size) {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				layers[0].things.add(new Grass(startingX + i*Grass.SIZE, startingY + j*Grass.SIZE));
			}
		}
	}
}
