package spoopTime;

import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

import things.Thing;

public class Layer {
	
	public List<Thing> things = new ArrayList<Thing>();
	
	public boolean legalMove(Ellipse2D outline, Thing thing) {
		for (Thing t: things) {
			if (thing != t && t.checkCollision(outline)) {
				return false;
			}
		}
		return true;
	}

}
