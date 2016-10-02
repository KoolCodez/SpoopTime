package things;

import java.util.Random;

import spoopTime.Core;
import spoopTime.World;

public class Spawner extends Entity {
	private static final double WIDTH = 100;
	private static final double HEIGHT = 100;
	private Thread t;

	public Spawner(double x, double y) {
		super(x, y, WIDTH, HEIGHT, "Skull.png");
		createThread();
	}

	private void createThread() {
		t = new Thread() {
			@Override
			public void run() {
				while (health > 0) {
					if (Math.random() * 10000 < 10 + Core.difficulty) {
						spawn();
					}
					synchronized (Core.display) {
						try {
							Core.display.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} //end while loop
			} //end run()
		}; //end Thread declaration
		t.start();
	} //end createThread
	
	private void spawn() {
		NPC enemy = new NPC(getOutline().getX() + randomGap(), getOutline().getY() + randomGap(),
				70, 70, "Goblin.png");
		World.addLayerOne(enemy);
		enemy.startFollowing();
	}
	
	private int randomGap() {
		int random = (int) (Math.random() * 2) * 2 - 1;
		return random*140;
	}

}
