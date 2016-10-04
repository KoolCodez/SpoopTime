package spoopTime;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;

import javax.swing.JPanel;

import movement.MovementController;
import things.Entity;
import things.FireBall;

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
		MovementController movement = new MovementController(controlPanel);
	}
	
	private void addClickListener() {
		MouseListener l = new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				fireBall();
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		};
		Core.frame.addMouseListener(l);
	}
	
	private void fireBall() {
		Point2D point = player.getLoc();
		double deltaX = player.getOutline().getWidth() * Math.cos(Math.toRadians(player.getAngle() - 90));
		double deltaY = player.getOutline().getWidth() * Math.sin(Math.toRadians(player.getAngle() - 90));
		point.setLocation(point.getX() + deltaX, point.getY() + deltaY);
		FireBall f = new FireBall(point.getX(), point.getY(), Control.player);
		World.addLayerOne(f);
		f.startMoving(player.getAngle());
	}
	
	private void addMotionListener() {
		MouseMotionListener l = new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				Point p = new Point((int) ((e.getPoint().x + Display.currentLoc.getX()) / Display.SCALE), 
						(int) ((e.getPoint().y + Display.currentLoc.getY()) / Display.SCALE));
				player.faceToward(p);
				
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				Point p = new Point((int) ((e.getPoint().x + Display.currentLoc.getX()) / Display.SCALE), 
						(int) ((e.getPoint().y + Display.currentLoc.getY()) / Display.SCALE));
				player.faceToward(p);
				
			}
		};
		Core.frame.addMouseMotionListener(l);
	}
}
