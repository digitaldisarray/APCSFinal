package xyz.disarray.game.entities;

import java.awt.Color;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import processing.core.PApplet;

public abstract class Entity {
	private int x, y, width, height, health, damage;
	private Color color;
	private boolean shouldRemove;
	private boolean visible;
	
	

	public Entity(int x, int y, int width, Color color) {
		this(x, y, width, width, color);
	}

	public Entity(int x, int y, int width, int height, Color color) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = color;

		shouldRemove = false;
		visible = true;
		
		health = 100;
	}

	public abstract void act();

	public abstract void draw(PApplet g);

	public void collide(Entity e) {
		
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}

	public void remove() {
		shouldRemove = true;
	}

	public boolean shouldRemove() {
		return shouldRemove;
	}

	public void move(double vx, double vy) {
		this.x += vx;
		this.y += vy;
	}

	public void setPos(int x, int y) {
		this.x = x;
		this.y = y;
	}

	// Lines that make up the hitbox
	public Line2D[] getSegments() {
		Line2D top = new Line2D.Float();
		top.setLine(x, y, x + width, y);

		Line2D bottom = new Line2D.Float();
		bottom.setLine(x, y + height, x + width, y + height);

		Line2D left = new Line2D.Float();
		left.setLine(x, y, x, y + height);

		Line2D right = new Line2D.Float();
		right.setLine(x + width, y, x + width, y + height);

		return new Line2D[] { top, bottom, left, right };
	}
	
	public Rectangle2D getRect() {
		Rectangle2D rect = new Rectangle2D.Float();
		
		rect.setRect(x, y, width, height);
		
		return rect;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public void changeHealth(int change) {
		health += change;
	}
	
	public int getHealth() {
		return health;
	}
	
	public void setHealth(int health) {
		this.health = health;
	}
	
	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	public int getDamage() {
		return damage;
	}

	public void drawHealthBar(PApplet draw, float healthI) {
		draw.pushStyle();
		
		draw.fill(0,255,0);
		draw.rect(x, y-10, 30 * health / healthI, 4);
		draw.popStyle();
	}
	
}
