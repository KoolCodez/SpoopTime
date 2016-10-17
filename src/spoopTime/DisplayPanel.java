package spoopTime;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import things.Thing;

public class DisplayPanel extends JPanel {
	private static final int BUTTON_WIDTH = 150;
	private static final int BUTTON_HEIGHT = 50;
	private boolean gameOverMode = false;
	private JButton restartButton;
	
	@Override
	public void paintComponent(Graphics g) {
		BufferedImage buffer = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_ARGB);
		
		Graphics g2 = buffer.getGraphics();
		g2.setColor(Display.BORDER_ORANGE);
		g2.fillRect(0, 0, 2000, 2000);
		if (gameOverMode) {
			g.setColor(Color.black);
			g.fillRect(0, 0, scaled(1000), scaled(1000));
			g.setColor(new Color(214, 132, 0));
			g.setFont(new Font(Font.SERIF, Font.PLAIN, scaled(100)));
			g.drawString("GAME OVER", scaled(200), scaled(300));
			g.setFont(new Font(Font.SERIF, Font.PLAIN, scaled(20)));
			g.drawString("Score:", scaled(400), scaled(400));
			g.setFont(new Font(Font.SERIF, Font.PLAIN, scaled(35)));
			g.drawString("    " + Core.score, scaled(450), scaled(400));
		} else {
			drawBackGround(g2);
			drawThings(g2);
			try {
				drawDarkness(g2);
			} catch(NullPointerException e) {
				
			}
			g2.setColor(new Color(214, 132, 0));
			g2.setFont(new Font(Font.SERIF, Font.PLAIN, scaled(35)));
			g2.drawString("" + Core.score, scaled(900), scaled(50));
			g.drawImage(buffer, 0, 0, null);
		}
	}
	
	public static int scaled(double size) {
		return (int) (size * Display.SCALE);
	}
	
	public void gameOver() {
		gameOverMode = true;
		JButton endButton = new JButton("QUIT");
		restartButton = new JButton("RESTART");
		JPanel thisPanel = this;
		restartButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameOverMode = false;
				Core.reset();
				thisPanel.remove(endButton);
				thisPanel.remove(restartButton);
				synchronized (thisPanel) {
					thisPanel.notifyAll();
				}
			}
		});
		restartButton.setBounds(scaled(425), scaled(475), 
				scaled(BUTTON_WIDTH), scaled(BUTTON_HEIGHT));
		restartButton.setBackground(Display.ORANGE);
		restartButton.setBorder(new LineBorder(Display.BORDER_ORANGE, 4));
		add(restartButton);
		
		
		endButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Core.frame.setVisible(false);
				System.exit(1);
			}
		});
		endButton.setBounds(scaled(425), scaled(525),
				scaled(BUTTON_WIDTH), scaled(BUTTON_HEIGHT));
		endButton.setBackground(Display.ORANGE);
		endButton.setBorder(new LineBorder(Display.BORDER_ORANGE, 4));
		add(endButton);
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
			for (Iterator<Thing> it = World.layers[i].things.iterator(); it.hasNext(); ) {
				it.next().draw(g, Display.currentLoc);
			}
		}
	}
	
	private void drawDarkness(Graphics g) {
		Color c = new Color(0, 0, 0, (int) (Core.difficulty*2) + 35);
		g.setColor(c);
		int points = 6;
		int[] xPoints = new int[points];
		int[] yPoints = new int[points];
		double angle = Math.toRadians(Control.player.getAngle() + 90 + 45);
		double size = scaled(200);
		int halfScreen = scaled(500);
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
		
		double smallSize = .6 * Display.SCALE;
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
