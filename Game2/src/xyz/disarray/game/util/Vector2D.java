package xyz.disarray.game.util;

/**
 * This class represents a 2D vector and offers some very basic features like normalizing and getting the magnitude.
 */
public class Vector2D {
	private double x, y;

	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Vector2D normalize() {
		return new Vector2D(x / getMagnitude(), y / getMagnitude());
	}

	public double getMagnitude() {
		return Math.sqrt(x * x + y * y);
	}

	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
}
