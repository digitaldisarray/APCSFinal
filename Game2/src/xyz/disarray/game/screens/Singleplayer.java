package xyz.disarray.game.screens;

import java.util.ArrayList;

import processing.core.PApplet;
import xyz.disarray.game.entities.Entity;
import xyz.disarray.game.entities.Wall;
import xyz.disarray.game.entities.Zombie;

public class Singleplayer {

	private ArrayList<Entity> entities;

	public Singleplayer() {
		entities = new ArrayList<>();
		entities.add(new Wall(80, 80, 100, 10));
		entities.add(new Zombie(100, 100));

		// TODO: Get walls from map loader and add to entities

	}

	public void act() {
		for (Entity entity : entities)
			entity.act();
	}

	public void draw(PApplet g) {
		for (Entity entity : entities)
			entity.draw(g);
	}

	public ArrayList<Entity> getEntities() {
		return entities;
	}

	public void removeEntity(Entity e) {
		entities.remove(entities.indexOf(e));
	}
}
