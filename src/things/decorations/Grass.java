package things.decorations;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

import things.Thing;

public class Grass extends Thing {
	public static final int SIZE = 100;
	
	public Grass(double x, double y) {
		outline = new Ellipse2D.Double(x, y, SIZE, SIZE);
		image = generateTexture();
	}
	
	public BufferedImage generateTexture() {
		BufferedImage i = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = (Graphics2D) i.getGraphics();
		int patchSize = 5;
		for (int col = 0; col < SIZE / patchSize; col++) {
			for (int row = 0; row < SIZE / patchSize; row++) {
				int r = (int) (Math.random() * 100);
				int g = (int) (100 + Math.random() * 50);
				int b = (int) (Math.random() * 60);
				Color c = new Color(r, g, b);
				g2.setColor(c);
				g2.fillRect(col * patchSize, row * patchSize, patchSize, patchSize);
			}
		}
		return i;
	}
}
