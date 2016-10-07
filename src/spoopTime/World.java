package spoopTime;

import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import things.Entity;
import things.Grass;
import things.Grave1;
import things.Grave2;
import things.Grave3;
import things.Spawner;
import things.Thing;

public class World {
	public static Layer[] layers = { new Layer(), new Layer(), new Layer() };
	private static List<MoveToken> moveList = new ArrayList<MoveToken>();

	public static void addLayerZero(Thing t) {
		layers[0].things.add(t);
		t.layer = 0;
	}

	public static void addLayerOne(Thing t) {
		layers[1].things.add(t);
		t.layer = 1;
	}

	public static void addLayerTwo(Thing t) {
		layers[2].things.add(t);
		t.layer = 2;
	}

	public static boolean legalMove(double deltaX, double deltaY, Thing thing) {
		if (layers[thing.layer].things.contains(thing)) {
			Ellipse2D e = thing.getOutline();
			Ellipse2D temp = new Ellipse2D.Double(e.getX() + deltaX, e.getY() + deltaY, e.getWidth(), e.getHeight());
			return layers[thing.layer].legalMove(temp, thing);
		}
		return true;
	}
	
	public static void createGrounds(int startingX, int startingY, int size) {
		int newSize = size / Grass.SIZE;
		for (int i = 0; i < newSize; i++) {
			for (int j = 0; j < newSize; j++) {
				layers[0].things.add(new Grass(startingX + i*Grass.SIZE, startingY + j*Grass.SIZE));
			}
		}
	}
	
	public static void createWalls(int startingX, int startingY, int size) {
		Thing leftWall = new Thing(startingX, startingY, 20, size/1.4, "BackGround.jpg");
		addLayerOne(leftWall);
		Thing upWall = new Thing(startingX, startingY, size/1.4, 20, "BackGround.jpg");
		addLayerOne(upWall);
		Thing rightWall = new Thing(startingX + size, startingY, 20, size/1.4, "BackGround.jpg");
		addLayerOne(rightWall);
		Thing downWall = new Thing(startingX, startingY + size, size/1.4, 20, "BackGround.jpg");
		addLayerOne(downWall);
	}
	
	public static void createGraves() {
		Spawner enemySpawn1 = new Grave1(-1000, -1000);
		addLayerOne(enemySpawn1);
		Spawner enemySpawn2 = new Grave1(-1000, 1000);
		addLayerOne(enemySpawn2);
		Spawner enemySpawn3 = new Grave2(1000, -1000);
		addLayerOne(enemySpawn3);
		Spawner enemySpawn4 = new Grave3(1000, 1000);
		addLayerOne(enemySpawn4);
	}
	
	public static void destroy(Thing thing) {
		
		/*for (Iterator<Thing> iterator = layers[thing.layer].things.iterator(); iterator.hasNext(); ) {
			if (iterator.next().equals(thing)) {
				iterator.remove();
			}
		}*/
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
	
	public static void resetWorld() {
		layers = new Layer[3];
		for (int i = 0; i < 3; i++) {
			layers[i] = new Layer();
		}
	}
}
