package things.spawners;

import spoopTime.Core;
import spoopTime.World;
import things.decorations.DeadGrave;
import things.entities.Entity;
import things.entities.NPC;
import things.entities.Zombie;

public class Spawner extends Entity {
	private Thread t;
	private int value = 50;
	
	public Spawner(double x, double y, double width, double height, String imagePath) {
		super(x, y, width, height, imagePath);
		createThread();
		setHealth(40.0);
	}
	
	public Spawner() {
		super();
		createThread();
		setHealth(40.0);
	}
	private static final double DIFFICULTY_CONSTANT = 50e7;
	private static final double MAX_SPAWN = 1000;
	private void createThread() {
		t = new Thread() {
			@Override
			public void run() {
				while (health > 0) {
					if ((Math.random() * DIFFICULTY_CONSTANT / (World.getSize()*2)) < Core.difficulty &&
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
	
	protected void spawn() {
		NPC enemy = new Zombie(getOutline().getX(), getOutline().getY() + getOutline().getHeight());
		World.addLayer(enemy, 1);
		enemy.startFollowing();
	}
	
	@Override
	public void kill() {
		health = 0;
		Core.score += value;
		World.addLayer(new DeadGrave(getLoc().getX(), getLoc().getY(), this), layer);
		World.destroy(this);
	}

}
