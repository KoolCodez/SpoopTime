package things;

import spoopTime.Core;

public class Player extends Entity {

	public Player() {
		super(0, 0, 70, 70, "Villager.png");
	}
	
	@Override
	public void kill() {
		super.kill();
		Core.gamingMode = false;
	}
}
