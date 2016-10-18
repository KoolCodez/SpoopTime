package spoopTime;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class TextureUtil {
	
	public static Image compileImage(ArrayList<Image> images) {
		int scaled100 = (int) (100 * Display.SCALE);
		BufferedImage compiledImage = new BufferedImage(scaled100, scaled100, BufferedImage.TYPE_INT_ARGB);
		Graphics g = compiledImage.createGraphics();
		for (int i = 0; i < images.size(); i++) {
			g.drawImage(images.get(i), 0, 0, null);
		}
		
		return compiledImage;
	}
	
	public static BufferedImage loadImage(String pathFromTextures, double width, double height, boolean scale) {
		Image i;
		try {
			String name = "/Resources/Textures/" + pathFromTextures;
			URL url = TextureUtil.class.getResource(name);
			i = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		if (scale) {
			width *= Display.SCALE;
			height *= Display.SCALE;
		}
		i = i.getScaledInstance((int) width, (int) height, Image.SCALE_SMOOTH);
		BufferedImage newI = new BufferedImage((int) width, (int) height, BufferedImage.TYPE_INT_ARGB);
		Graphics g = newI.getGraphics();
		g.drawImage(i, 0, 0, null);
		return newI;
	}
	
	public static BufferedImage rotate(Image image, int degrees, Ellipse2D outline) {
		/*double rotationRequired = Math.toRadians (degrees);
		double locationX = image.getWidth(null) / 2;
		double locationY = image.getHeight(null) / 2;
		AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
		
		BufferedImage i = new BufferedImage((int) image.getWidth(null), (int) image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = i.createGraphics();
		g2d.drawImage(image, tx, null);*/
		ImageIcon icon = new ImageIcon(image);
		int difference = (int) ((outline.getWidth() - icon.getIconWidth()) / 2);
		BufferedImage newImage = new BufferedImage((int) outline.getWidth(), (int) outline.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = (Graphics2D) newImage.getGraphics();
		g2.rotate(Math.toRadians(degrees), outline.getWidth() / 2, outline.getHeight() / 2);
		
		g2.drawImage(image, difference, difference, null);
		return newImage;
		/*double theta = Math.toRadians (degrees);
		double w = image.getWidth(null);
		double h = image.getHeight(null);
		AffineTransform tx = new AffineTransform();
		tx.translate(h / 2, w / 2);
		tx.rotate(theta);
		tx.translate(-w / 2, -h / 2);
		double newW = Math.cos(theta) * w + Math.sin(theta) * h;
		double newH = Math.sin(theta) * w + Math.cos(theta) * h;
		int SCALED_100 = (int) (100 * Display.SCALE);
		BufferedImage i = new BufferedImage((int) Math.sqrt((SCALED_100 * SCALED_100) * 2), 
				(int) Math.sqrt((SCALED_100 * SCALED_100) * 2), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = i.createGraphics();
		g2d.drawImage(image, tx, null);
		return i;*/
	}
}
