package spoopTime;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;

import javax.swing.JPanel;

import movement.MovementController;
import things.Entity;

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
		MovementController movement = new MovementController(controlPanel);
	}
}
