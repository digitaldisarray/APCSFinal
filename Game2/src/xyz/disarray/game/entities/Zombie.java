
package xyz.disarray.game.entities;

import java.awt.geom.Line2D;

import processing.core.PApplet;
import processing.core.PConstants;
import xyz.disarray.game.Game;

public class Zombie extends Entity {

	public static final int ATTACK_DAMAGE = -5;
	private int attackCooldown, moveCooldown;
	private double vx, vy;
	private final int FRICTION = 2;
	private boolean cright, cleft, cup, cdown;
	
	public static final int ATTACK_COOLDOWN = 100, MOVE_COOLDOWN = 3;

	public Zombie(int x, int y) {
		super(x, y, 25, Game.BAD);
		attackCooldown = ATTACK_COOLDOWN;
		moveCooldown = MOVE_COOLDOWN;
	}

	@Override
	public void act() {
		if (attackCooldown > 0)
			attackCooldown--;

		if (moveCooldown > 0)
			moveCooldown--;

		double dst = Math.sqrt(Math.pow(Math.abs(Game.player.getX() - getX()), 2)
				+ Math.pow(Math.abs(Game.player.getY() - getY()), 2));

		if (dst < 30 && attackCooldown == 0) {
			Game.player.changeHealth(ATTACK_DAMAGE);
			attackCooldown = ATTACK_COOLDOWN;
		}
		if (moveCooldown == 0) {
			int playerX = Game.player.getX();
			int playerY = Game.player.getY();

			if (playerX < getX())
				vx = -1;

			if (playerX > getX())
				vx = 1;

			if (playerY > getY())
				vy = 1;

			if (playerY < getY())
				vy = -1;
			
			if(playerY == getY())
				vy = 0;
			
			if(playerX == getX())
				vx = 0;

			move(vx, vy);

			if (cup)
				vy = FRICTION;

			if (cdown)
				vy = -FRICTION;

			if (cleft)
				vx = FRICTION;

			if (cright)
				vx = -FRICTION;

			move(vx, vy);

			if (vx < 0)
				vx += FRICTION;

			if (vx > 0)
				vx -= FRICTION;

			if (vy < 0)
				vy += FRICTION;

			if (vy > 0)
				vy -= FRICTION;

			// Reset collision variables
			cright = false;
			cleft = false;
			cup = false;
			cdown = false;
			
			moveCooldown = MOVE_COOLDOWN;
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

	public void lineCollided(Line2D line) {
		if (line.getBounds().equals(getSegments()[0].getBounds()))
			cup = true;

		if (line.getBounds().equals(getSegments()[1].getBounds()))
			cdown = true;

		if (line.getBounds().equals(getSegments()[2].getBounds()))
			cleft = true;

		if (line.getBounds().equals(getSegments()[3].getBounds()))
			cright = true;
	}

}
