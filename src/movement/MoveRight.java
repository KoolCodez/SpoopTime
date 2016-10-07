package movement;
import spoopTime.Control;
import spoopTime.Core;
import spoopTime.Display;
import things.entities.Entity;

public class MoveRight extends Thread {
	public void run() {
		try {
			while (MovementController.movingRight) {
				if (!MovementController.movingDown && !MovementController.movingUp) {
					Control.player.move(1, 0);
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
