package movement;

import spoopTime.Control;
import spoopTime.Core;
import spoopTime.Display;
import things.Entity;

public class MoveUpLeft extends Thread{
	public void run() {
		try {
			while (MovementController.movingLeft && MovementController.movingUp) {
				Control.player.move(Entity.Move.UPLEFT);
				synchronized (Core.display) {
					Core.display.wait();
				}
			}

		} catch (InterruptedException e) {
			System.out.println("sleep interrupted");
		}
	}
}