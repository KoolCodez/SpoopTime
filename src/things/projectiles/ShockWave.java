package things.projectiles;

import java.awt.geom.RectangularShape;
import java.util.ArrayList;

import spoopTime.World;
import things.Thing;
import things.entities.Entity;

public class ShockWave extends Projectile {
	private static final double WIDTH = 50;
	private static final double HEIGHT = 50;
	private ArrayList<Entity> hitList;

	public ShockWave(double x, double y, Thing shooter) {
		super(x, y, WIDTH, HEIGHT, "FireBall.png", shooter);
		impactDamage = 1;
		hitList = new ArrayList<Entity>();
		hitList.add((Entity) shooter);
	}
	
	public void startMoving(double direction) {
		super.startMoving(direction, 10);
	}
	
	@Override
	public boolean checkCollision(RectangularShape shape, Thing thing) {
		return false;
	}
	
	@Override
	public void collide(Thing thing) {
		boolean exempt = false;
		for (Entity ent : hitList) {
			if (thing.equals(ent)) {
				exempt = true;
			}
		}
		if (thing instanceof Entity && !exempt) {
			
			((Entity) thing).damage(impactDamage);
			hitList.add((Entity) thing);
		}
	}
	
	@Override
	protected void move() {
		double deltaX;
		double deltaY;
		double theta = Math.toRadians(angle - 90);
		deltaX = speed * Math.cos(theta);
		deltaY = speed * Math.sin(theta);
		try {
			World.legalMove(deltaX, deltaY, this);
			changePos(deltaX, deltaY);
		} catch (java.util.ConcurrentModificationException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

	}
}
