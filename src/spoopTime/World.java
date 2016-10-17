package spoopTime;

import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import things.Thing;
import things.decorations.DeadGrave;
import things.decorations.Dirt;
import things.decorations.Grass;
import things.decorations.Wall;
import things.entities.Entity;
import things.entities.NPC;
import things.projectiles.Projectile;
import things.spawners.Grave;
import things.spawners.Grave1;
import things.spawners.Grave2;
import things.spawners.Grave3;
import things.spawners.Spawner;

public class World {
	public static Layer[] layers = { new Layer(), new Layer(), new Layer() };
	private static List<MoveToken> moveList = new CopyOnWriteArrayList<MoveToken>();
	private static int size = 3500;
	private static int totalGraves;
	
	public static void addLayer(Thing thing, int layer) {
		layers[layer].things.add(thing);
		thing.layer = layer;
		if (!exempt(thing)) {
			if (!legalMove(0, 0, thing)) {
				layers[layer].things.remove(thing);
			}
		}
	}
	
	private static boolean exempt(Thing thing) {
		if (thing instanceof Dirt) {
			return true;
		}
		if (thing instanceof Wall) {
			return true;
		}
		if (thing instanceof DeadGrave) {
			return true;
		}
		return false;
	}

	public static boolean legalMove(double deltaX, double deltaY, Thing thing) {
		if (layers[thing.layer].things.contains(thing)) {
			Ellipse2D e = thing.getOutline();
			Ellipse2D temp = new Ellipse2D.Double(e.getX() + deltaX, e.getY() + deltaY, e.getWidth(), e.getHeight());
			return layers[thing.layer].legalMove(temp, thing);
		}
		return true;
	}
	
	public static void createGrounds() {
		int startingX, startingY;
		startingX = -size / 2;
		startingY = -size / 2;
		int newSize = size / Grass.SIZE;
		for (int i = 0; i < newSize; i++) {
			for (int j = 0; j < newSize; j++) {
				layers[0].things.add(new Grass(startingX + i*Grass.SIZE, startingY + j*Grass.SIZE));
			}
		}
	}
	
	public static void setSize(int s) {
		size = s;
	}
	
	public static int getSize() {
		return size;
	}
	
	public static void createWalls() {
		int startingX, startingY;
		startingX = -size / 2;
		startingY = -size / 2;
		Wall leftWall = new Wall(startingX, startingY, 20, size/1.4);
		addLayer(leftWall, 1);
		Wall upWall = new Wall(startingX, startingY, size/1.4, 20);
		addLayer(upWall, 1);
		Wall rightWall = new Wall(startingX + size, startingY, 20, size/1.4);
		addLayer(rightWall, 1);
		Wall downWall = new Wall(startingX, startingY + size, size/1.4, 20);
		addLayer(downWall, 1);
	}
	
	public static void createGraves(double ratioPerSquareThousand) {
		int startingX, startingY;
		startingX = -size / 2;
		startingY = -size / 2;
		double squareThousands = (size / 1000) * (size / 1000);
		totalGraves = (int) (ratioPerSquareThousand * squareThousands * Core.difficulty / 20);
		System.out.println(squareThousands);
		System.out.println(totalGraves);
		for (int i = 0; i < totalGraves; i++) {
			double x = startingX + Math.random()*size;
			double y = startingY + Math.random()*size;
			int randomNum = (int) (Math.random() * 3);
			Spawner enemySpawn = new Grave(x - 20, y, randomNum);
			World.addLayer(enemySpawn, 1);
			World.addLayer(new Dirt(x, y + Dirt.SIZE), 0);
			World.addLayer(new Dirt(x, y + Dirt.SIZE * 2), 0);
		}
		createGraveChecker();
	}
	
	public static int getTotalGraves() {
		return totalGraves;
	}
	
	private static void createGraveChecker() {
		Thread graveChecker = new Thread() {
			@Override
			public void run() {
				while (true) {
					boolean allGravesGone = true;
					for (Thing thing : layers[1].things) {
						if (thing instanceof Spawner) {
							allGravesGone = false;
						}
					}
					if (allGravesGone) {
						rezGraves();
					}
					try {
						sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		graveChecker.start();
	}
	
	private static void rezGraves() {
		for (Thing thing : layers[1].things) {
			if (thing instanceof DeadGrave) {
				((DeadGrave) thing).respawn();
			}
		}
	}
	
	public static void destroy(Thing thing) {
		layers[thing.layer].things.remove(thing);
	}
	
	public static void addToMoveQueue(Thing thing, double deltaX, double deltaY) {
		moveList.add(new MoveToken(thing, deltaX, deltaY));
	}
	
	public static void runMoves() {
		for (MoveToken move : moveList) {
			if (move != null) {
				boolean isEntity = move.thing instanceof Entity;
				if (legalMove(move.deltaX, move.deltaY, move.thing)) {
					move.thing.changePos(move.deltaX, move.deltaY);
				} else if (legalMove(move.deltaX, 0, move.thing) && isEntity) {
					move.thing.changePos(move.deltaX, 0);
				} else if (legalMove(0, move.deltaY, move.thing) && isEntity) {
					move.thing.changePos(0, move.deltaY);
				}
			}
		}
		moveList = new ArrayList<MoveToken>();
	}
	
	public static boolean contains(Thing thing) {
		for (int i = 0; i < 3; i++) {
			if (layers[i].things.contains(thing)) {
				return true;
			}
		}
		return false;
	}
	
	public static void clearEnts() {
		for (Layer l : layers) {
			l.clearEnts();
		}
	}
	
	public static void resetWorld() {
		layers = new Layer[3];
		for (int i = 0; i < 3; i++) {
			layers[i] = new Layer();
		}
	}
}
