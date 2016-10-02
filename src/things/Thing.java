package things;

import java.awt.Graphics;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import spoopTime.Display;
import spoopTime.TextureUtil;

public class Thing {
	protected Ellipse2D outline;
	protected BufferedImage image;
	public int layer = 0;
	public Thing() {
		outline = new Ellipse2D.Double();
		
		
	}
	
	public Thing(double x, double y, double width, double height, String imagePath) {
		outline = new Ellipse2D.Double(x, y, Math.sqrt(2 * width * width), Math.sqrt(2 * height * height));
		this.image = TextureUtil.loadImage(imagePath, width, height, true);
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
		outline = new Ellipse2D.Double(outline.getX() + deltaX, outline.getY() + deltaY, outline.getWidth(), outline.getHeight());
	}
	
	public boolean checkCollision(Ellipse2D shape) {
		return outline.intersects(shape.getX(), shape.getY(), shape.getWidth(), shape.getHeight());
	}
	
	public void draw(Graphics g, Point2D reference) {
		int drawX = (int) ((outline.getX() - reference.getX()) * Display.SCALE);
		int drawY = (int) ((outline.getY() - reference.getY()) * Display.SCALE);
		g.drawImage(image, drawX, drawY, (int) (outline.getWidth() * Display.SCALE), 
				(int) (outline.getHeight() * Display.SCALE), null);
	}
}
