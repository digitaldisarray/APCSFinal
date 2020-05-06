package xyz.disarray.game.entities;

import processing.core.PApplet;
import xyz.disarray.game.Game;

public class Wall extends Entity {

	public Wall(int x, int y, int width, int height) {
		super(x, y, width, height, Game.GREY);
	}

	@Override
	public void act() {
		
	}

	@Override
	public void draw(PApplet g) {
		g.pushMatrix();
		
		g.stroke(255);
		g.fill(getColor().getRGB());
		g.rect(getX(), getY(), getWidth(), getHeight());
		
		g.popMatrix();
	}
	
}
