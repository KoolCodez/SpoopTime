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
	public Projectile(double x, double y, double width, double height, String imagePath) {
		super(x, y, width, height, imagePath);
		
	}
	
	public void startMoving(double direction, double speed) {
		angle = direction;
		this.speed = speed;
		createThread();
	}
	
	private void createThread() {
		Projectile p = this;
		Thread t = new Thread() {
			@Override
			public void run() {
				while (speed > 2) {
					move();
					speed -= .1;
					synchronized (Core.display) {
						try {
							Core.display.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				World.destroy(p);
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
		if (World.legalMove(deltaX, deltaY, this)) {
			changePos(deltaX, deltaY);
		}
	}
	
	@Override
	public void collide(Thing thing) {
		speed = 0;
		if (thing instanceof Entity && thing != Control.player) {
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
