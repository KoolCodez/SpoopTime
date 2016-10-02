package movement;
import spoopTime.Control;
import spoopTime.Core;
import spoopTime.Display;
import things.Entity;

public class MoveDownLeft extends Thread{
	public void run() {
		try {
			while (MovementController.movingLeft && MovementController.movingDown) {
				Control.player.move(Entity.Move.DOWNLEFT);
				synchronized (Core.display) {
					Core.display.wait();
				}
			}

		} catch (InterruptedException e) {
			System.out.println("sleep interrupted");
		}
	}
}
