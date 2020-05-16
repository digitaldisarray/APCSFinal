package xyz.disarray.game.screens;

import java.util.ArrayList;

import processing.core.PApplet;
import xyz.disarray.game.entities.Entity;
import xyz.disarray.game.entities.LocalPlayer;
import xyz.disarray.game.entities.Wall;
import xyz.disarray.game.entities.Zombie;

public class Singleplayer extends Screen {
	public static final double ZOMBIE_SPAWN_RATE = 0.002;
	private ArrayList<Entity> entities;
	private ArrayList<Wall> walls;
	private LocalPlayer p;
	public Singleplayer(LocalPlayer p) {
		entities = new ArrayList<>();
		this.p = p;
		//entities.add(new Wall(80, 80, 100, 10));
		

		// TODO: Get walls from map loader and add to entities

	}

	public void update() {
		if(Math.random() < ZOMBIE_SPAWN_RATE)
			entities.add(new Zombie((int)(Math.random()*800), (int)(Math.random()* 600)));

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
		p.addKill();
	}
}
