package xyz.disarray.game.backgrounds;

import processing.core.PApplet;
import xyz.disarray.game.Game;

public class Man extends Background {

	@Override
	public void setup() {
		
	}

	@Override
	public void draw(PApplet g) {
		g.image(Game.man, 0, 0, g.width, g.height);
	}

}
