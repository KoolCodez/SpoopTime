package things;

import java.awt.Graphics;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.awt.image.BufferedImage;

import spoopTime.Display;
import spoopTime.DisplayPanel;
import spoopTime.TextureUtil;
import spoopTime.World;

public class Thing {
	protected Ellipse2D outline;
	protected BufferedImage image;
	public double realWidth;
	public double realHeight;
	public int layer = 0;

	public Thing() {
		outline = new Ellipse2D.Double();
	}

	public Thing(double x, double y, double width, double height, String imagePath) {
		outline = new Ellipse2D.Double(x, y, Math.sqrt(2 * width * width), Math.sqrt(2 * height * height));
		this.image = TextureUtil.loadImage(imagePath, width, height, false);
		realWidth = width;
		realHeight = height;
	}

	public Point2D getLoc() {
		return new Point2D.Double(outline.getX(), outline.getY());
	}

	public Ellipse2D getOutline() {
		return outline;
	}

	public void collide(Thing thing) {
	}

	public void changePos(double deltaX, double deltaY) {
		outline = new Ellipse2D.Double(outline.getX() + deltaX, outline.getY() + deltaY, outline.getWidth(),
				outline.getHeight());
	}

	public boolean checkCollision(RectangularShape shape, Thing thing) {
		Ellipse2D e = getOutline();
		RectangularShape temp = new Rectangle2D.Double(e.getX(), e.getY(), e.getWidth() * .75, e.getHeight() * .75);
		return temp.intersects(shape.getX(), shape.getY(), shape.getWidth(), shape.getHeight());
	}

	public void draw(Graphics g, Point2D reference) {
		int drawX = DisplayPanel.scaled(outline.getX() - reference.getX());
		int drawY = DisplayPanel.scaled(outline.getY() - reference.getY());
		g.drawImage(image, drawX, drawY, DisplayPanel.scaled(outline.getWidth()),
				DisplayPanel.scaled(outline.getHeight()), null);
	}

	public boolean isWithinScreen(Point2D reference) {
		double radius = 600;
		radius = Math.sqrt(2 * (radius * radius));
		double xDist = reference.getX() - outline.getX();
		double yDist = reference.getY() - outline.getY();
		double distance = Math.sqrt(xDist * xDist + yDist * yDist);
		return Math.abs(distance) < radius;
	}
}
