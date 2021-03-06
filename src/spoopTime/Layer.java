package spoopTime;

import java.awt.geom.Ellipse2D;
import java.awt.geom.RectangularShape;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import things.Thing;
import things.decorations.DeadGrave;
import things.decorations.Dirt;
import things.entities.Entity;
import things.spawners.Spawner;

public class Layer {
	
	public List<Thing> thingsOld = new ArrayList<Thing>();
	public List<Thing> things = new CopyOnWriteArrayList<Thing>();
	
	public boolean legalMove(RectangularShape outline, Thing thing) {
		
		for (Iterator<Thing> iterator = things.iterator(); iterator.hasNext(); ) {
			Thing t = iterator.next();
			if (thing != t && t.checkCollision(outline, thing)) {
				thing.collide(t);
				t.collide(thing);
				return false;
			}
		}
		return true;
	}
	
	public void clearEnts() {
		for (Thing thing : things) {
			if (thing instanceof Entity || thing instanceof DeadGrave || thing instanceof Dirt) {
				things.remove(thing);
			}
		}
	}

}
