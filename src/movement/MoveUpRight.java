package movement;

import spoopTime.Control;
import spoopTime.Core;
import spoopTime.Display;
import things.Entity;

public class MoveUpRight extends Thread{
	public void run() {
		try {
			while (MovementController.movingRight && MovementController.movingUp) {
				Control.player.move(1, -1);
				synchronized (Core.display) {
					Core.display.wait();
				}
			}

		} catch (InterruptedException e) {
			System.out.println("sleep interrupted");
		}
	}
}