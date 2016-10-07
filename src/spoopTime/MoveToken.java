package spoopTime;

import things.Thing;

public class MoveToken {
	public Thing thing;
	public double deltaX;
	public double deltaY;
	public MoveToken(Thing thing, double deltaX, double deltaY) {
		this.thing = thing;
		this.deltaX = deltaX;
		this.deltaY = deltaY;
		
	}
}
