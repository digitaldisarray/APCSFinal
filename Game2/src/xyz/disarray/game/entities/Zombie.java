package xyz.disarray.game.entities;

import processing.core.PApplet;
import processing.core.PConstants;
import xyz.disarray.game.util.Colors;

public class Zombie extends Entity {

	public Zombie(int x, int y) {
		super(x, y, 25, Colors.BAD);
	}

	@Override
	public void act() {
		
	}

	@Override
	public void draw(PApplet g) {
		g.pushMatrix();

		g.noStroke();
		g.fill(getColor().getRGB());
		g.ellipseMode(PConstants.CORNER);
		g.ellipse(getX(), getY(), getWidth(), getHeight());

		g.popMatrix();
	}
	
	public void collide(Entity e) {
		if(e instanceof Bullet || e instanceof RayBullet) 
			remove();
	}
	
}
