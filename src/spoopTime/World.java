package spoopTime;

import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import things.Thing;
import things.decorations.Dirt;
import things.decorations.Grass;
import things.entities.Entity;
import things.entities.NPC;
import things.projectiles.Projectile;
import things.spawners.Grave1;
import things.spawners.Grave2;
import things.spawners.Grave3;
import things.spawners.Spawner;

public class World {
	public static Layer[] layers = { new Layer(), new Layer(), new Layer() };
	private static List<MoveToken> moveList = new CopyOnWriteArrayList<MoveToken>();
	private static int size = 6000;
	
	public static void addLayer(Thing thing, int layer) {
		
		thing.layer = layer;
		if (thing instanceof Projectile) {
			if (legalMove(0, 0, thing)) {
				layers[layer].things.add(thing);
			}
		} else if (thing instanceof Entity) {
			if (legalMove(0, 0, thing)) {
				layers[layer].things.add(thing);
			}
		} else {
			if (legalMove(0, 0, thing)) {
				layers[layer].things.add(thing);
			}
		}
		
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
	
	public static void createWalls() {
		int startingX, startingY;
		startingX = -size / 2;
		startingY = -size / 2;
		Thing leftWall = new Thing(startingX, startingY, 20, size/1.4, "BackGround.jpg");
		addLayer(leftWall, 1);
		Thing upWall = new Thing(startingX, startingY, size/1.4, 20, "BackGround.jpg");
		addLayer(upWall, 1);
		Thing rightWall = new Thing(startingX + size, startingY, 20, size/1.4, "BackGround.jpg");
		addLayer(rightWall, 1);
		Thing downWall = new Thing(startingX, startingY + size, size/1.4, 20, "BackGround.jpg");
		addLayer(downWall, 1);
	}
	
	public static void createGraves(double ratioPerSquareThousand) {
		int startingX, startingY;
		startingX = -size / 2;
		startingY = -size / 2;
		int number = (int) (ratioPerSquareThousand * (size / 1000) * Core.difficulty / 10);
		System.out.println(ratioPerSquareThousand * (size / 1000));
		System.out.println(number);
		for (int i = 0; i < number; i++) {
			double x = startingX + Math.random()*size;
			double y = startingY + Math.random()*size;
			Spawner enemySpawn = randomGrave(x - 20, y);
			World.addLayer(enemySpawn, 1);
			World.addLayer(new Dirt(x, y + Dirt.SIZE), 0);
			World.addLayer(new Dirt(x, y + Dirt.SIZE * 2), 0);
		}
	}
	
	private static Spawner randomGrave(double x, double y) {
		int rand = (int) (Math.random() * 3);
		Spawner spawn;
		switch (rand) {
		case 0: spawn = new Grave1(x, y);
			break;
		case 1: spawn = new Grave2(x, y);
			break;
		case 2: spawn = new Grave3(x, y);
			break;
		default: spawn = new Grave1(x, y);
			break;
		}
		return spawn;
	}
	
	public static void destroy(Thing thing) {
		
		/*for (Iterator<Thing> iterator = layers[thing.layer].things.iterator(); iterator.hasNext(); ) {
			if (iterator.next().equals(thing)) {
				iterator.remove();
			}
		}*/
		if (thing instanceof NPC) {
			Core.score += ((NPC) thing).value;
		}
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
