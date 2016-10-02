package things;

import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import spoopTime.Control;
import spoopTime.Core;

public class NPC extends Entity {
	private Thread thread;
	

	public NPC(double x, double y, double width, double height, String imagePath) {
		super(x, y, width, height, imagePath);
		speed = 6;
	}
	
	@Override
	public void collide(Thing thing) {
		if (thing instanceof Player) {
			Entity temp = (Entity) thing;
			temp.damage(.1);
		}
	}

	public void startFollowing() {
		thread = new Thread() {
			@Override
			public void run() {
				while (true) {
					Point2D point = Control.player.getLoc();
					Ellipse2D el = Control.player.getOutline();
					point.setLocation(point.getX() + el.getWidth()/2, point.getY() + el.getHeight()/2);
					double deltaX = point.getX() - outline.getX();
					double deltaY = point.getY() - outline.getY();
					if (Math.abs(deltaX) < 10) {
						deltaX = 0;
					}
					if (Math.abs(deltaY) < 10) {
						deltaY = 0;
					}
					faceToward(new Point((int) point.getX(), (int) point.getY()));
					move((int) (deltaX / Math.abs(deltaX)), (int) (deltaY / Math.abs(deltaY)));
					try {
						synchronized (Core.display) {
							Core.display.wait();
						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		thread.start();
	}

}
