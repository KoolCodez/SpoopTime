package things.decorations;

import spoopTime.World;
import things.Thing;
import things.spawners.Grave;
import things.spawners.Spawner;

public class DeadGrave extends Thing {
	
	Spawner spawner;
	
	public DeadGrave(double x, double y, Spawner spawn) {
		super(x, y, spawn.realWidth, spawn.realHeight, getImage(spawn));
		spawner = spawn;
	}
	
	private static String getImage(Spawner s) {
		if (s instanceof Grave) {
			return ((Grave) s).getDeadPath();
		} else {
			return "Skull.png";
		}
	}
	
	public void respawn() {
		World.destroy(this);
		spawner.respawn();
	}
}
