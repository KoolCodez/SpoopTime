package things.decorations;

import spoopTime.World;
import things.Thing;
import things.spawners.RandomGrave;
import things.spawners.Spawner;

public class DeadGrave extends Thing {
	
	public DeadGrave(double x, double y, Spawner spawn) {
		super(x, y, spawn.getOutline().getWidth(), 
				spawn.getOutline().getHeight(), "Skull.png");
	}
	
	public void respawn() {
		World.destroy(this);
		World.addLayer(new RandomGrave(getLoc().getX(), getLoc().getY()), layer);
	}
}
