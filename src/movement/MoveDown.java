package movement;
import spoopTime.Control;
import spoopTime.Core;
import spoopTime.Display;
import things.entities.Entity;

public class MoveDown extends Thread {
	public void run() {
		try {
			while (MovementController.movingDown) {
				if (!MovementController.movingLeft && ! MovementController.movingRight) {
					Control.player.move(0, 1);
				}
				synchronized (Core.display) {
					Core.display.wait();
				}
			}
			
		} catch (InterruptedException e) {
			System.out.println("sleep interrupted");
		}
	}

}
