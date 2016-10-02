package spoopTime;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import things.Thing;

public class DisplayPanel extends JPanel {
	
	
	@Override
	public void paintComponent(Graphics g) {
		drawBackGround(g);
		drawThings(g);
		drawDarkness(g);
	}
	
	private void drawBackGround(Graphics g) {
		/*BufferedImage image = TextureUtil.loadImage("BackGround.jpg", 50, 50, true);
		//g.drawImage(image, 0, 0, null);
		TexturePaint tp = new TexturePaint(image, new Rectangle(50, 50));
		Graphics2D g2d = (Graphics2D) g;
		g2d.setPaint(tp);
		g.fillRect((int) -Display.currentLoc.getX(), (int) -Display.currentLoc.getY(), 5000, 5000);*/
	}
	
	private void drawThings(Graphics g) {
		for (int i = 0; i < 3; i++) {
			for (Thing thing : World.layers[i].things) {
				thing.draw(g, Display.currentLoc);
			}
		}
	}
	
	private void drawDarkness(Graphics g) {
		g.setColor(Color.black);
		int points = 6;
		int[] xPoints = new int[points];
		int[] yPoints = new int[points];
		double angle = Math.toRadians(Control.player.getAngle() + 90 + 45);
		double size = 200;
		int halfScreen = 500;
		double x = size * Math.toDegrees(Math.cos(angle));
		double y = size * Math.toDegrees(Math.sin(angle));
		xPoints[0] = (int) x + halfScreen;
		yPoints[0] = (int) y + halfScreen;
		//g.drawLine(500, 500, (int) x, (int) y);
		angle = Math.toRadians(Control.player.getAngle() + 45);
		x = size * Math.toDegrees(Math.cos(angle));
		y = size * Math.toDegrees(Math.sin(angle));
		xPoints[1] = (int) x + halfScreen;
		yPoints[1] = (int) y + halfScreen;
		
		angle = Math.toRadians(Control.player.getAngle() - 45);
		x = size * Math.toDegrees(Math.cos(angle));
		y = size * Math.toDegrees(Math.sin(angle));
		xPoints[2] = (int) x + halfScreen;
		yPoints[2] = (int) y + halfScreen;
		
		double smallSize = 1;
		angle = Math.toRadians(Control.player.getAngle() + 45);
		x = smallSize * Math.toDegrees(Math.cos(angle));
		y = smallSize * Math.toDegrees(Math.sin(angle));
		xPoints[3] = (int) x + halfScreen;
		yPoints[3] = (int) y + halfScreen;
		
		angle = Math.toRadians(Control.player.getAngle() + 135);
		x = smallSize * Math.toDegrees(Math.cos(angle));
		y = smallSize * Math.toDegrees(Math.sin(angle));
		xPoints[4] = (int) x + halfScreen;
		yPoints[4] = (int) y + halfScreen;
		
		angle = Math.toRadians(Control.player.getAngle() - 90 - 45);
		x = size * Math.toDegrees(Math.cos(angle));
		y = size * Math.toDegrees(Math.sin(angle));
		xPoints[5] = (int) x + halfScreen;
		yPoints[5] = (int) y + halfScreen;
		Polygon p = new Polygon(xPoints, yPoints, points);
		g.fillPolygon(p);
	}
}
