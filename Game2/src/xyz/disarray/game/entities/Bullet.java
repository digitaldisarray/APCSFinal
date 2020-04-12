package xyz.disarray.game.entities;

import processing.core.PApplet;
import xyz.disarray.game.util.Colors;
import xyz.disarray.game.util.Vector2D;

public class Bullet extends Entity {

	// TODO: Decide if bullets should be ray-traced or not
	
	private int lifeCoutner;
//	private Vector2D direction;
	
	public Bullet(int x, int y, Vector2D direction, int length) {
		super(x, y, 0, Colors.getBulletColor());
		lifeCoutner = 0;
//		this.direction = direction;
		//this(x, y, x + (int) (Math.cos(angle) * length + .5), y + (int) (Math.sin(angle) * length + .5));
	}

	@Override
	public void act() {
		lifeCoutner++;

		if (lifeCoutner > 180)
			remove();
	}

	@Override
	public void draw(PApplet g) {
		g.pushMatrix();
		// TODO: Draw a dot with a trail behind it
		g.popMatrix();
	}

}
