package xyz.disarray.game.screens;

import processing.core.PApplet;

public abstract class Screen {
	public abstract void update();
	public abstract void draw(PApplet g);
	
	public int clickMouse(int x, int y) {
		return -1;
	}
}
