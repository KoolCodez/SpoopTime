package things.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Point2D;

import javax.swing.JLabel;

import spoopTime.Display;
import spoopTime.DisplayPanel;
import spoopTime.TextureUtil;
import spoopTime.World;
import things.Thing;

public class Entity extends Thing {
	protected double health = 10;
	protected double maxHealth = health;
	private double angle = 0;
	protected double speed = 10;

	public static enum Move {
		LEFT, RIGHT, UP, DOWN, UPLEFT, UPRIGHT, DOWNLEFT, DOWNRIGHT
	};

	public Entity(double x, double y, double width, double height, String imagePath) {
		super(x, y, width, height, imagePath);
	}

	public Entity() {
		super();
	}

	public void faceToward(Point p) {
		double myX = outline.getX() + outline.getWidth() / 2;
		double myY = outline.getY() + outline.getHeight() / 2;
		angle = Math.atan2(myY - p.y, myX - p.x);
		angle *= 180 / Math.PI;
		angle -= 90;
	}

	protected void setHealth(double h) {
		health = h;
		maxHealth = health;
	}

	// -1, 0, and 1 ONLY!! IT WILL NOT WORK IF IT IS ANOTHER NUMBER!
	public void move(int horizontal, int vertical) {
		double deltaX;
		double deltaY;
		double newSpeed = speed;
		if (vertical != 0 && horizontal != 0) {
			newSpeed = Math.sqrt(speed * speed / 2);
		}
		deltaX = newSpeed * horizontal;
		deltaY = newSpeed * vertical;
		World.addToMoveQueue(this, deltaX, deltaY);
	}

	public void move(double xRatio, double yRatio) {
		double deltaX;
		double deltaY;
		deltaX = speed * xRatio;
		deltaY = speed * yRatio;
		World.addToMoveQueue(this, deltaX, deltaY);
	}

	public double getAngle() {
		return angle;
	}

	@Override
	public void collide(Thing thing) {
		// System.out.println("HEY! STOP THAT!");
	}

	public void damage(double damage) {
		health -= damage;
		if (health <= 0) {
			kill();
		}
	}

	public boolean heal(double heal) {
		if (health + heal <= maxHealth) {
			health += heal;
			return true;
		}
		return false;
	}

	protected void kill() {
		health = 0;
		World.destroy(this);
	}

	@Override
	public void draw(Graphics g, Point2D reference) {
		int drawX = DisplayPanel.scaled(outline.getX() - reference.getX());
		int drawY = DisplayPanel.scaled(outline.getY() - reference.getY());
		g.setColor(Color.red);
		g.fillRect(drawX, drawY - DisplayPanel.scaled(20), DisplayPanel.scaled(outline.getWidth() * health / maxHealth),
				DisplayPanel.scaled(10));
		g.drawImage(TextureUtil.rotate(image, (int) angle, outline), drawX, drawY,
				DisplayPanel.scaled(outline.getWidth()), DisplayPanel.scaled(outline.getHeight()), null);
	}
}
