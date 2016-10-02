package things;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Point2D;

import javax.swing.JLabel;

import spoopTime.Display;
import spoopTime.TextureUtil;
import spoopTime.World;

public class Entity extends Thing {
	private double angle = 0;
	private double speed = 12;
	public static enum Move {LEFT, RIGHT, UP, DOWN, UPLEFT, UPRIGHT, DOWNLEFT, DOWNRIGHT};
	
	public Entity(double x, double y, double width, double height, String imagePath) {
		super(x, y, width, height, imagePath);
	}
	
	public void faceToward(Point p) {
		double myX = outline.getX() + outline.getWidth()/2;
		double myY = outline.getY() + outline.getHeight()/2;
		angle = Math.atan2(myY - p.y, myX - p.x);
		angle *= 180 / Math.PI;
		angle -= 90;
	}
	
	public void move(Move m) {
		double deltaX = 0;
		double deltaY = 0;
		double newSpeed = Math.sqrt(speed * speed / 2);
		switch (m) {
		case LEFT: deltaX = -speed;
			break;
		case RIGHT: deltaX = speed;
			break;
		case UP: deltaY = -speed;
			break;
		case DOWN: deltaY = speed;
			break;
		case UPLEFT: deltaX = -newSpeed;
		deltaY = -newSpeed;
			break;
		case UPRIGHT: deltaX = newSpeed;
		deltaY = -newSpeed;
			break;
		case DOWNLEFT: deltaX = -newSpeed;
		deltaY = newSpeed;
			break;
		case DOWNRIGHT: deltaX = newSpeed;
		deltaY = newSpeed;
			break;
		}
		if (World.legalMove(deltaX, deltaY, this)) {
			changePos(deltaX, deltaY);
		}
		//System.out.println(outline.getX() + " " + outline.getY());
	}
	
	public double getAngle() {
		return angle;
	}
	
	@Override
	public void draw(Graphics g, Point2D reference) {
		int drawX = (int) ((outline.getX() - reference.getX()) * Display.SCALE);
		int drawY = (int) ((outline.getY() - reference.getY()) * Display.SCALE);
		g.drawImage(TextureUtil.rotate(image, (int) angle, outline), drawX, drawY, 
				 null);
	}
}
