package things.decorations;

import java.awt.Image;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import spoopTime.TextureUtil;
import things.Thing;

public class Tree extends Thing {
	private static final double WIDTH = 200.0;
	private static final double HEIGHT = 200.0;
	public Tree(double x, double y) {
		super(x, y, WIDTH, HEIGHT, "trunk.png");
		image = generateTexture();
	}
	
	private BufferedImage generateTexture() {
		ArrayList<Image> images = new ArrayList<Image>();
		int randomNum = (int) (Math.random() * 3) + 3;
		Ellipse2D ellipse = new Ellipse2D.Double(0, 0, WIDTH, HEIGHT);
		int randomAngle = (int) (Math.random() * 360);
		Image trunk = TextureUtil.loadImage("trunkB.png", WIDTH, HEIGHT, false);
		trunk = TextureUtil.rotate(trunk, randomAngle, ellipse);
		images.add(trunk);
		for (int i = 0; i < randomNum; i++) {
			int random = (int) (Math.random() * 3) + 1;
			Image branch = TextureUtil.loadImage("branch" + random + ".png", WIDTH, HEIGHT, false);
			branch = TextureUtil.rotate(branch, i * (int) (360 / randomNum), ellipse);
			images.add(branch);
		}
		BufferedImage compiledImage = TextureUtil.compileImage(images, (int) WIDTH);
		return compiledImage;
	}
}
