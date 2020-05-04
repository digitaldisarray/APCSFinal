
package xyz.disarray.game.entities;

import processing.core.PApplet;
import processing.core.PConstants;
import xyz.disarray.game.Game;

public class Zombie extends Entity {

	public static final int ATTACK_DAMAGE = -1;
	private int cooldown;

	public Zombie(int x, int y) {
		super(x, y, 25, Game.BAD);
		cooldown = 100;
	}

	@Override
	public void act() {
		if (cooldown > 0)
			cooldown--;

		double dst = Math.sqrt(Math.pow(Math.abs(Game.player.getX() - getX()), 2)
				+ Math.pow(Math.abs(Game.player.getY() - getY()), 2));

		if (dst < 30 && cooldown == 0) {
			Game.player.changeHealth(ATTACK_DAMAGE);
			cooldown = 100;
		}
	}

	@Override
	public void draw(PApplet g) {
		if (!isVisible()) {
			return;
		}
		g.pushMatrix();

		g.noStroke();
		g.fill(getColor().getRGB());
		g.ellipseMode(PConstants.CORNER);
		g.ellipse(getX(), getY(), getWidth(), getHeight());

		g.popMatrix();

	}

	public void collide(Entity e) {
		if (e instanceof Bullet || e instanceof RayBullet)
			remove();
	}

}
