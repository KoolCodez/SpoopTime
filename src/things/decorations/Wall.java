package things.decorations;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;

import spoopTime.Settings;
import things.Thing;

public class Wall extends Thing {
	public Wall(double x, double y, double w, double h) {
		super(x, y, w, h, "BackGround.jpg");
	}
	
	@Override
	public boolean checkCollision(RectangularShape shape, Thing thing) {
		Ellipse2D e = getOutline();
		RectangularShape temp = new Rectangle2D.Double(e.getX(), e.getY(), 
				e.getWidth(), e.getHeight());
		return temp.intersects(shape.getX(), shape.getY(), shape.getWidth(), shape.getHeight());
	}
	
	@Override
	public boolean isWithinScreen(Point2D reference) {
		if (Settings.fastWalls) {
			return true;
		} else {
			return super.isWithinScreen(reference);
		}
	}
}
