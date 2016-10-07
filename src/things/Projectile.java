package things;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;

import spoopTime.Control;
import spoopTime.Core;
import spoopTime.Display;
import spoopTime.TextureUtil;
import spoopTime.World;

public class Projectile extends Thing {
	double angle = 0;
	double speed = 0;
	public Thing shooter;
	public Projectile(double x, double y, double width, double height, String imagePath, Thing shooter) {
		super(x, y, width, height, imagePath);
		this.shooter = shooter;
		
	}
	
	public void startMoving(double direction, double speed) {
		angle = direction;
		this.speed = speed;
		createThread();
	}
	private static final double SPEED_DECREMENT = .1;
	private static final double MIN_SPEED = 2;
	
	private void createThread() {
		Projectile p = this;
		Thread t = new Thread() {
			@Override
			public void run() {
				while (speed > MIN_SPEED) {
					move();
					speed -= SPEED_DECREMENT;
					try {
						synchronized (Core.display) {
							Core.display.wait();
						} 
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				while (World.contains(p)) {
					try {
					World.destroy(p);
					} catch(java.util.ConcurrentModificationException e) {
						e.printStackTrace();
					}
				}
			}
		};
		t.start();
	}
	
	private void move() {
		double deltaX;
		double deltaY;
		double theta = Math.toRadians(angle - 90);
		deltaX = speed * Math.cos(theta);
		deltaY = speed * Math.sin(theta);
		try {
			if (World.legalMove(deltaX, deltaY, this)) {
				changePos(deltaX, deltaY);
			} else {
				speed = -1;
			}
		} catch(java.util.ConcurrentModificationException e) {
			e.printStackTrace();
		} catch(NullPointerException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void collide(Thing thing) {
		if (thing instanceof Projectile) {
			if (((Projectile) thing).shooter != this.shooter) {
				speed = 0;
			}
		} else {
			speed = 0;
		}
		if (thing instanceof Entity && thing != shooter) {
			((Entity) thing).damage(5);
		}
	}
	
	@Override
	public void draw(Graphics g, Point2D reference) {
		int drawX = (int) ((outline.getX() - reference.getX()) * Display.SCALE);
		int drawY = (int) ((outline.getY() - reference.getY()) * Display.SCALE);
		g.drawImage(TextureUtil.rotate(image, (int) angle, outline), drawX, drawY, 
				 null);
	}

}
