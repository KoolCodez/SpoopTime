package things.spawners;

import spoopTime.TextureUtil;
import spoopTime.World;
import things.entities.NPC;
import things.entities.Skeleton;
import things.entities.Zombie;

public class Grave extends Spawner {
	private static final int[] widths = {100, 100, 100};
	private static final int[] heights = {50, 100, 75};
	private static final double REG_HEALTH = 40.0;
	private static final double OBS_HEALTH = 75.0;
	private int number;
	private String regPath;
	private String obsPath;
	private String deadPath;
	private int type = level;

	public Grave(double x, double y, int number) {
		super(x, y, getWidth(number), getHeight(number), getTexturePath(number));
		this.number = number;
		this.regPath = getTexturePath(number);
		this.obsPath = "ObsGrave" + (number + 1) + ".png";
		this.deadPath = "DeadGrave" + (number + 1) + ".png";
	}
	
	private static double getWidth(int number) {
		return widths[number];
	}
	
	private static double getHeight(int number) {
		return heights[number];
	}
	
	private static String getTexturePath(int number) {
		String s = "RegGrave" + (number + 1) + ".png";
		return s;
	}
	
	public String getDeadPath() {
		return deadPath;
	}
	
	@Override
	protected void spawn() {
		NPC enemy;
		if (type == 0) {
			enemy = new Zombie(getOutline().getX(), getOutline().getY() + getOutline().getHeight());
		} else {
			enemy = new Skeleton(getOutline().getX(), getOutline().getY() + getOutline().getHeight());
			
		}
		World.addLayer(enemy, 1);
		enemy.startFollowing();
	}
	
	@Override
	public void respawn() {
		super.respawn();
		if (level == 0) {
			this.image = TextureUtil.loadImage(obsPath, realWidth, realHeight, false);
			setHealth(REG_HEALTH + level);
		} else {
			int random = (int) (Math.random() * 2);
			if (random == 0) {
				this.image = TextureUtil.loadImage(regPath, realWidth, realHeight, false);
				setHealth(40.0);
			} else if (random == 1){
				this.image = TextureUtil.loadImage(obsPath, realWidth, realHeight, false);
			}
			setHealth(OBS_HEALTH + level);
			type = random;
		}
		createThread();
		
	}
}
