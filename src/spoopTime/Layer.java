package spoopTime;

import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import things.Thing;

public class Layer {
	
	public List<Thing> thingsOld = new ArrayList<Thing>();
	public List<Thing> things = new CopyOnWriteArrayList<Thing>();
	
	public boolean legalMove(Ellipse2D outline, Thing thing) {
		
		for (Iterator<Thing> iterator = things.iterator(); iterator.hasNext(); ) {
			Thing t = iterator.next();
			if (thing != t && t.checkCollision(outline)) {
				thing.collide(t);
				t.collide(thing);
				return false;
			}
		}
		return true;
	}

}
