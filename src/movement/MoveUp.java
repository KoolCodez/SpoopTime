package movement;

import spoopTime.Control;
import spoopTime.Core;
import spoopTime.Display;
import things.entities.Entity;

public class MoveUp extends Thread {
	public void run() {
		try {
			while (MovementController.movingUp) {
				if (!MovementController.movingRight && !MovementController.movingLeft) {
					Control.player.move(0, -1);
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
