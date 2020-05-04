package xyz.disarray.game.entities;

import java.awt.geom.Line2D;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;
import xyz.disarray.game.Game;

public class LocalPlayer extends Entity {

	private int vx, vy;
	private final int SPEED = 2;
	private final int FRICTION = 2;
	private final int MAX_VELOCITY = 6;
	private boolean cright, cleft, cup, cdown;
	private boolean right, left, up, down;
	private boolean clicked;
	private int cooldown; 	// Time in between shots fired for gun


	// If we use only ray casted bullets, we can only have one active bullet at a
	// time, and dont need an array list
	private ArrayList<Entity> bullets;

	public LocalPlayer(int x, int y) {
		super(x, y, 25, Game.GOOD);
		cooldown = 0;
		bullets = new ArrayList<>();
	}

	@Override
	public void act() {
		
		
		if (right != left) {
			if (Math.abs(vx) + SPEED < MAX_VELOCITY) {
				if (right)
					vx += SPEED;
				else
					vx -= SPEED;
			} else if (Math.abs(vx) + SPEED > MAX_VELOCITY) {
				vx = MAX_VELOCITY * (vx / Math.abs(vx));
			}
		}

		if (up != down) {
			if (Math.abs(vy) + SPEED < MAX_VELOCITY) {
				if (up)
					vy -= SPEED;
				else
					vy += SPEED;
			} else {
				vy = MAX_VELOCITY * (vy / Math.abs(vy));
			}
		}

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

		for (Entity b : bullets)
			b.act();

		// Reset collision variables
		cright = false;
		cleft = false;
		cup = false;
		cdown = false;
	}

	@Override
	public void draw(PApplet g) {
		g.pushMatrix();

		g.noStroke();
		g.fill(getColor().getRGB());
		g.ellipseMode(PConstants.CORNER);
		g.ellipse(getX(), getY(), getWidth(), getHeight());

		if (clicked)
			if (cooldown <= 0) {
				shoot(g.mouseX, g.mouseY);
				cooldown = 20;
			}

		if (cooldown > 0)
			cooldown--;

		g.popMatrix();

		for (Entity b : bullets)
			b.draw(g);

	}

	public void shoot(int x2, int y2) {
		// TODO: Remove this debug var eventually
		boolean rayTraced = true;

		if (rayTraced) {
			double mouseAngle = Math.abs(Math.toDegrees(Math.atan2(y2 - getY(), x2 - getX())));

			if (y2 > getY())
				if (x2 < getX())
					mouseAngle = Math.abs(180 - mouseAngle) + 180;
				else
					mouseAngle = 360 - mouseAngle;

			bullets.add(new RayBullet(getX() + getWidth() / 2, getY() + getHeight() / 2, mouseAngle, 1000));

		} else {
			// Radians
//			double mouseAngle = Math.atan2(y2 - getY(), x2 - getX());
//
//			if (mouseAngle < 0)
//				mouseAngle -= Math.PI;
//
//			Vector2D shootVec = new Vector2D(Math.cos(mouseAngle), Math.sin(mouseAngle));
//
//			bullets.add(new Bullet(getX(), getY(), shootVec, 40));
		}
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

	public void setRight(boolean right) {
		this.right = right;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public void setClicked(boolean clicked) {
		this.clicked = clicked;
	}

	public ArrayList<Entity> getBullets() {
		return bullets;
	}

	public void removeBullet(Entity e) {
		bullets.remove(bullets.indexOf(e));
	}
	
}
