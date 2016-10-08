package things.spawners;

import java.util.Random;

import spoopTime.Core;
import spoopTime.Display;
import spoopTime.World;
import things.entities.Entity;
import things.entities.NPC;

public class Spawner extends Entity {
	private Thread t;

	public Spawner(double x, double y, double width, double height, String imagePath) {
		super(x, y, width, height, imagePath);
		createThread();
	}
	private static final double DIFFICULTY_CONSTANT = 10000;
	private static final double MAX_SPAWN = 1000;
	private void createThread() {
		t = new Thread() {
			@Override
			public void run() {
				while (health > 0) {
					if ((Math.random() * DIFFICULTY_CONSTANT) < Core.difficulty &&
							World.layers[1].things.size() <= MAX_SPAWN) {
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
		NPC enemy = new NPC(getOutline().getX(), getOutline().getY() + getOutline().getHeight(),
				50, 50, "Goblin.png");
		World.addLayerOne(enemy);
		enemy.startFollowing();
	}

}
