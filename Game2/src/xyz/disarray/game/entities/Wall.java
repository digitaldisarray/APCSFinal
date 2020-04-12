package xyz.disarray.game.entities;

import java.awt.Color;

import processing.core.PApplet;

public class Wall extends Entity {

	public Wall(int x, int y, int width, int height, Color color) {
		super(x, y, width, height, color);
	}

	@Override
	public void act() {
		
	}

	@Override
	public void draw(PApplet g) {
		g.pushMatrix();
		
		g.stroke(getColor().getRGB());
		g.rect(getX(), getY(), getWidth(), getHeight());
		
		g.popMatrix();
	}
	
}
