package spoopTime;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;

import javax.swing.JPanel;

import movement.MovementController;
import things.entities.Entity;
import things.projectiles.FireBall;
import things.projectiles.ShockWave;

public class Control {

	public static Entity player;
	private static JPanel controlPanel;

	public Control(Entity player) {
		this.player = player;
		setUpPanel();
	}

	private void setUpPanel() {
		controlPanel = new JPanel();
		Core.frame.add(controlPanel);
		addMotionListener();
		addClickListener();
		addKeyListener();
		MovementController movement = new MovementController(controlPanel);
	}
	private static boolean healing = false;
	private void addKeyListener() {
		KeyListener keyL = new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyChar() == 'e' && !healing) {
					healing = true;
					Thread t = new Thread() {
						@Override
						public void run() {
							heal();
						}
					};
					t.start();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyChar() == 'e') {
					healing = false;
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		};
		Core.frame.addKeyListener(keyL);
	}
	
	private static boolean firing = false;
	private void addClickListener() {
		MouseListener l = new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					firing = true;
					Thread t = new Thread() {
						@Override
						public void run() {
							shockWave();
						}
					};
					t.start();
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1 && !firing) {
					fireBall();
				} else if(e.getButton() == MouseEvent.BUTTON3) {
					firing = false;
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				

			}

		};
		Core.frame.addMouseListener(l);
	}

	private static void fireBall() {
		Point2D point = player.getLoc();
		double deltaX = player.getOutline().getWidth() * 1.25 * Math.cos(Math.toRadians(player.getAngle() - 90));
		double deltaY = player.getOutline().getWidth() * 1.25 * Math.sin(Math.toRadians(player.getAngle() - 90));
		point.setLocation(point.getX() + deltaX, point.getY() + deltaY);
		FireBall f = new FireBall(point.getX(), point.getY(), Control.player);
		World.addLayer(f, 1);
		f.startMoving(player.getAngle());
		Core.score--;
	}
	
	private static void shockWave() {
		while (firing) {
			Point2D point = player.getLoc();
			double deltaX = player.getOutline().getWidth() * 1.25 * Math.cos(Math.toRadians(player.getAngle() - 90));
			double deltaY = player.getOutline().getWidth() * 1.25 * Math.sin(Math.toRadians(player.getAngle() - 90));
			point.setLocation(point.getX() + deltaX, point.getY() + deltaY);
			ShockWave f = new ShockWave(point.getX(), point.getY(), Control.player);
			World.addLayer(f, 1);
			f.startMoving(player.getAngle());
			Core.score--;
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	private static void heal() {
		while (healing && Core.score > 0) {
			if (player.heal(.5)) {
				Core.score -= 1;
			}
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void addMotionListener() {
		MouseMotionListener l = new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				Point p = new Point((int) ((e.getPoint().x /Display.SCALE + Display.currentLoc.getX())),
						(int) (e.getPoint().y/Display.SCALE + Display.currentLoc.getY()));
				player.faceToward(p);

			}

			@Override
			public void mouseMoved(MouseEvent e) {
				Point p = new Point((int) (e.getPoint().x/Display.SCALE + Display.currentLoc.getX()),
						(int) (e.getPoint().y/ Display.SCALE + Display.currentLoc.getY()));
				player.faceToward(p);

			}
		};
		Core.frame.addMouseMotionListener(l);
	}
}
