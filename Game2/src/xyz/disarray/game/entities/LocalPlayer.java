package xyz.disarray.game.entities;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;
import xyz.disarray.game.util.Colors;

public class LocalPlayer extends Entity {

	private final int SPEED = 2;
	private boolean right, left, up, down;
	private boolean clicked;
	private int cooldown; // Time in between shots fired for gun

	// If we use only ray casted bullets, we can only have one active bullet at a
	// time, and dont need an array list
	private ArrayList<Entity> bullets;

	public LocalPlayer(int x, int y) {
		super(x, y, 25, Colors.GOOD);
		cooldown = 0;
		bullets = new ArrayList<>();
	}

	@Override
	public void act() {
		if (right != left) {
			if (right)
				move(SPEED, 0);
			else
				move(-SPEED, 0);
		}

		if (up != down) {
			if (up)
				move(0, -SPEED);
			else
				move(0, SPEED);
		}

		for (Entity b : bullets)
			b.act();
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
