package xyz.disarray.game.entities;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import processing.core.PApplet;
import xyz.disarray.game.Game;

public class RayBullet extends Entity {

	private int x2, y2;
	private int lifeCoutner;
	private boolean endpointChecked;

	public RayBullet(int x, int y, double angle, int length) {
		this(x, y, x + (int) (Math.cos(Math.toRadians(angle)) * length + .5),
				y - (int) (Math.sin(Math.toRadians(angle)) * length + .5));
		endpointChecked = false;
	}

	public RayBullet(int x, int y, double angle, int length, int damage) {
		this(x, y, x + (int) (Math.cos(Math.toRadians(angle)) * length + .5),
				y - (int) (Math.sin(Math.toRadians(angle)) * length + .5));
		endpointChecked = false;
		setDamage(damage);
	}

	public RayBullet(int x, int y, int x2, int y2) {
		super(x, y, 0, Game.getBulletColor());
		this.x2 = x2;
		this.y2 = y2;
		lifeCoutner = 0;
	}

	@Override
	public void act() {
		lifeCoutner++;

		if (lifeCoutner > 8)
			remove();
	}

	@Override
	public void draw(PApplet g) {
		// For some reason the draw method is called before it is resized so we have
		// this
		if (endpointChecked) {
			g.pushMatrix();

			g.stroke(getColor().getRGB());
			g.line(getX(), getY(), x2, y2);

			g.popMatrix();
		}
	}

	public Line2D getLine() {
		Line2D l = new Line2D.Float();
		l.setLine(getX(), getY(), x2, y2);

		return l;
	}

	public void setEndpoint(Point2D end) {
		this.x2 = (int) end.getX();
		this.y2 = (int) end.getY();
	}

	public boolean isEndPointChecked() {
		return endpointChecked;
	}

	public void endPointChecked() {
		endpointChecked = true;
	}
}
