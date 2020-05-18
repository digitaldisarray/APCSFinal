package xyz.disarray.game.screens;

import java.util.ArrayList;

import processing.core.PApplet;
import xyz.disarray.game.entities.Entity;
import xyz.disarray.game.entities.LocalPlayer;
import xyz.disarray.game.entities.Zombie;

public class Singleplayer extends Screen {
	private double zombieSpawnRate = 0.003;
	private ArrayList<Entity> entities;
	private LocalPlayer p;

	public Singleplayer(LocalPlayer p) {
		entities = new ArrayList<>();
		this.p = p;
	}

	public void update() {
		if (Math.random() < zombieSpawnRate) {
			entities.add(new Zombie((int) (Math.random() * 800), (int) (Math.random() * 600)));
			if(zombieSpawnRate < 0.015)
			zombieSpawnRate *= 1.1;
		}

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
