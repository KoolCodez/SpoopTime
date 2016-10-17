package things.projectiles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;

import spoopTime.Control;
import spoopTime.Core;
import spoopTime.Display;
import spoopTime.DisplayPanel;
import spoopTime.TextureUtil;
import spoopTime.World;
import things.Thing;
import things.entities.Entity;

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
					World.destroy(p);
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
			}
		} catch(java.util.ConcurrentModificationException e) {
			e.printStackTrace();
		} catch(NullPointerException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public boolean checkCollision(RectangularShape shape, Thing thing) {
		if (thing instanceof Projectile) {
			if (((Projectile) thing).shooter.equals(this.shooter)) {
				return false;
			}
		}
		Ellipse2D e = getOutline();
		RectangularShape temp = new Rectangle2D.Double(e.getX(), e.getY(), 
				e.getWidth() * .75, e.getHeight() * .75);
		return temp.intersects(shape.getX(), shape.getY(), shape.getWidth(), shape.getHeight());
	}
	
	@Override
	public void collide(Thing thing) {
		if (thing instanceof Projectile) {
			if (!((Projectile) thing).shooter.equals(this.shooter)) {
				speed = 0;
			}
		} else if (thing.equals(shooter)) {
		} else {
			speed = 0;
		}
		if (thing instanceof Entity && thing != shooter) {
			((Entity) thing).damage(5);
		}
	}
	
	@Override
	public void draw(Graphics g, Point2D reference) {
		int drawX = DisplayPanel.scaled(outline.getX() - reference.getX());
		int drawY = DisplayPanel.scaled(outline.getY() - reference.getY());
		g.drawImage(TextureUtil.rotate(image, (int) angle, outline), drawX, drawY, 
				DisplayPanel.scaled(outline.getWidth()), 
				DisplayPanel.scaled(outline.getHeight()), null);
	}

}
