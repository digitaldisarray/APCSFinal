package xyz.disarray.game;

import java.awt.Color;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import processing.core.PApplet;
import processing.core.PImage;
import xyz.disarray.game.backgrounds.BackgroundManager;
import xyz.disarray.game.entities.Bullet;
import xyz.disarray.game.entities.Entity;
import xyz.disarray.game.entities.LocalPlayer;
import xyz.disarray.game.entities.RayBullet;
import xyz.disarray.game.entities.Wall;
import xyz.disarray.game.entities.Zombie;
import xyz.disarray.game.screens.MainMenu;
import xyz.disarray.game.screens.Singleplayer;

public class Game extends PApplet {

	// Moved here to cut down on # of classes we have to draw in a UML diagram
	// Primary colors for the game
	// TODO: Make this adjustable in settings
	public static final Color GOOD = new Color(0, 150, 250);
	public static final Color BAD = new Color(255, 40, 40);
	public static final Color GREY = new Color(100, 100, 100);
	public static final Color DARK_GREY = new Color(50, 50, 50);
	
	// Storing here because I don't want to make an ImageManager class
	public static PImage man;

	// The instance of our main menu, might want to reconsider how we handle menus
	private MainMenu menu;

	private Singleplayer singleplayer; // Instance of the singleplayer game

	// how to do epic code like an epic coder make eveyrhting public static
	public static LocalPlayer player; // The local player (shared across multiplayer and single player)
	private GameState state; // What state of the game we are currently on
	private BackgroundManager bManager;

	private enum GameState {
		MENU, SINGLEPLAYER, MUTLIPLAYER
	}

	public void setup() {
		// I payed for 144hz so why not use all of them
		frameRate(144);
//		frameRate(60);
		menu = new MainMenu(this);
		state = GameState.MENU;
		surface.setSize(800, 600);
		surface.setResizable(false);
		
		bManager = new BackgroundManager();
		
		man = loadImage("res/img/man.png");
	}

	public void draw() {
		background(180, 180, 180);
		bManager.getBackground().draw(this);
		bManager.newBackground();

		if (state == GameState.MENU)
			menu.draw(this);

		/*
		 * What goes on in this singleplayer if statement (in order):
		 * 
		 * 1. Remove entites that should no longer exist (dead things, off screen
		 * bullets) 2. Handle collisions (in a kinda messy way) 3. Draw stuff
		 */
		if (state == GameState.SINGLEPLAYER) {
			// Remove entities
			// TODO: Move to its own method and make it not super bad code
			ArrayList<Entity> rem = new ArrayList<>();
			for (Entity e : singleplayer.getEntities())
				if (e.shouldRemove())
					rem.add(e);

			for (Entity e : rem)
				singleplayer.removeEntity(e);

			rem.clear();

			for (Entity b : player.getBullets())
				if (b.shouldRemove())
					rem.add(b);

			for (Entity b : rem)
				player.removeBullet(b);

			// Collide
			doCollisions(player, singleplayer);

			// Act
			player.act();
			singleplayer.act();

			if (player.getHealth() <= 0) {
				JOptionPane.showMessageDialog(null, "Printing complete");
				state = GameState.MENU;
			}
			
			// Draw
			singleplayer.draw(this);
			player.draw(this);
			
			// Vis checks
			doVisCheck(player, singleplayer.getEntities());
		}

	}

	/**
	 * This method takes a source entity and a list of entities in the game, and then hides the entities that are behind walls relative to the source.
	 * 
	 * Steps:
	 *  - Separate the obstructions (Walls) from the targets (Zombies & Enemy Players)
	 *  - Go to every target and:
	 *  	- Get all of the lines from the source to the target
	 *  	- Go to every obstruction and:
	 *  		- See if any of the lines from source to target don't intersect with an obsturction
	 *  	- If at least one line was not obstructed, set target to be visible
	 * 
	 * @param source - The entity that all visibility checks will be done relative to
	 * @param entities - The entities currently present in the game (only need walls, zombies, and enemies)
	 */
	private void doVisCheck(Entity source, ArrayList<Entity> entities) {
		ArrayList<Entity> obstructions = new ArrayList<>();
		ArrayList<Entity> targets = new ArrayList<>();

		// Sort obstructions and targets out
		for (Entity e : entities) {
			if (e instanceof Wall)
				obstructions.add(e);
			else if (e instanceof Zombie) // Add enemy player
				targets.add(e);
		}

		for (Entity t : targets) {
			List<Line2D> lines = new ArrayList<>();

			// Best code don't @ me
			// Upper left
			Line2D line = new Line2D.Float();
			line.setLine(source.getX(), source.getY(), t.getX(), t.getY());
			lines.add(line);
			// Upper right
			Line2D line2 = new Line2D.Float();
			line2.setLine(source.getX() + source.getWidth(), source.getY(), t.getX() + t.getWidth(), t.getY());
			lines.add(line2);
			// Lower left
			Line2D line3 = new Line2D.Float();
			line3.setLine(source.getX(), source.getY() + source.getHeight(), t.getX(), t.getY() + t.getHeight());
			lines.add(line3);
			// Lower right
			Line2D line4 = new Line2D.Float();
			line4.setLine(source.getX() + source.getWidth(), source.getY() + source.getHeight(),
					t.getX() + t.getWidth(), t.getY() + t.getHeight());
			lines.add(line4);
			// Center
			Line2D line5 = new Line2D.Float();
			line5.setLine(source.getX() + source.getWidth() / 2, source.getY() + source.getHeight() / 2,
					t.getX() + t.getWidth() / 2, t.getY() + t.getHeight() / 2);
			lines.add(line5);

			boolean vis = false;

			for (Entity o : obstructions) {
				for (Line2D l : lines) {
				
					boolean collide = false;
					for (Line2D seg : o.getSegments()) {
						Point2D p = getLineIntersection(l, seg);

						if (p != null) 
							collide = true;
					}
					
					if(!collide) {
						this.stroke(100, 255, 100);
						vis = true;
					} else {
						this.stroke(255, 100, 100);
					}
					
					this.line((float) l.getX1(), (float) l.getY1(), (float) l.getX2(), (float) l.getY2());
				}
			}

			t.setVisible(vis);
		}

	}

	public void mousePressed() {
		// Mouse clicking logic for while we are in the menu
		if (state == GameState.MENU) {
			int code = menu.clickMouse(mouseX, mouseY);

			/*
			 * 0 - Singleplayer button clicked
			 * 1 - Multiplayer button clicked
			 * 2 - Options button clicked
			 */

			switch (code) {
			case 0:
				player = new LocalPlayer(50, 50);
				singleplayer = new Singleplayer();
				state = GameState.SINGLEPLAYER;
				break;
			case 1:
				break;
			case 2:
				break;
			}
		} else {
			player.setClicked(true);
		}
	}

	public void mouseReleased() {
		if (state != GameState.MENU) {
			player.setClicked(false);
		}
	}

	public void keyPressed() {
		if (state != GameState.MENU) {
			switch (key) {
			case 'w':
				player.setUp(true);
				break;
			case 'a':
				player.setLeft(true);
				break;
			case 's':
				player.setDown(true);
				break;
			case 'd':
				player.setRight(true);
				break;
			}
		}
	}

	public void keyReleased() {
		if (state != GameState.MENU) {
			switch (key) {
			case 'w':
				player.setUp(false);
				break;
			case 'a':
				player.setLeft(false);
				break;
			case 's':
				player.setDown(false);
				break;
			case 'd':
				player.setRight(false);
				break;
			}
		}
	}

	// TODO: Make this work with mutliplayer
	public void doCollisions(LocalPlayer player, Singleplayer singleplayer) {
		ArrayList<Entity> bullets = player.getBullets(); // Refresh the list of bullets
		ArrayList<Entity> entities = singleplayer.getEntities(); // Refresh the list of entities
		if (entities.size() > 0) {
			// Bullet collisions
			if (bullets.size() > 0) {
				// TODO: Do a better check for the type of bullet we are running collisions on
				if (bullets.get(0) instanceof RayBullet) {
					for (Entity b : bullets) {
						RayBullet b2 = (RayBullet) b;
						// If we have not processed the shot already.
						if (!b2.isEndPointChecked()) {

							Entity closestEnt = null;
							Point2D closest = null;
							double closestDst = Double.MAX_VALUE;
							for (Entity e : entities) {
								for (Line2D l : e.getSegments()) {
									Point2D p = getLineIntersection(b2.getLine(), l);
									if (p == null)
										continue;

									double dst = Math.sqrt(Math.pow(Math.abs(player.getX() - p.getX()), 2)
											+ Math.pow(Math.abs(player.getY() - p.getY()), 2));

									if (dst < closestDst) {
										closest = p;
										closestDst = dst;
										closestEnt = e;
									}
								}
							}

							if (closest != null) {
								b2.setEndpoint(closest);
								closestEnt.collide(b);
							}

							b2.endPointChecked();
						}
					}

				} else if (bullets.get(0) instanceof Bullet) {

				}
			}

			// Collide local player with walls and players
			// TODO: This is really bad collision, someone fix it lol
			for (Entity e : entities) {

				if (e instanceof Zombie)
					continue;

				for (Line2D pl : player.getSegments()) {
					if (pl.intersects(e.getRect())) {
						player.lineCollided(pl);
						continue;
					}
				}
			}

			// Edge of window collisions for local player
			// NOTICE: If we have it so the player can move around an area larger than the
			// window size we will need to change this
			if (player.getX() < 0)
				player.setPos(0, player.getY());

			if (player.getX() > 800)
				player.setPos(800, player.getY());

			if (player.getY() < 0)
				player.setPos(player.getX(), 0);

			if (player.getY() > 600)
				player.setPos(player.getX(), 600);

		}
	}

	// Moved here to cut down on # of classes we have to draw in a UML diagram
	/**
	 * Generates colors for bullets so they are either slightly lighter or darker
	 * 
	 * @return A slightly light or dark color for the bullet trail is returned
	 */
	public static Color getBulletColor() {
		return new Color(255, 90 + (int) (Math.random() * 70), 0);
	}

	// Moved here to cut down on # of classes we have to draw in a UML diagram
	// Stolen and then slightly modified from StackOverflow
	public static Point2D getLineIntersection(Line2D ray, Line2D segment) {
		if (ray.intersectsLine(segment)) {
			double rx1 = ray.getX1(), ry1 = ray.getY1(), rx2 = ray.getX2(), ry2 = ray.getY2(), sx1 = segment.getX1(),
					sy1 = segment.getY1(), sx2 = segment.getX2(), sy2 = segment.getY2(), rdx = rx2 - rx1,
					rdy = ry2 - ry1, sdx = sx2 - sx1, sdy = sy2 - sy1, t1, t2, ix, iy;

			t2 = (rdx * (sy1 - ry1) + rdy * (rx1 - sx1)) / (sdx * rdy - sdy * rdx);
			t1 = (sx1 + sdx * t2 - rx1) / rdx;

			// Perpendicular line edge-case
			if (rdx == 0.0)
				return new Point2D.Double((int) rx1, (int) sy1);

			if (t1 > 0/* && 1 > t2 && t2 > 0 */) {
				ix = rx1 + rdx * t1;
				iy = ry1 + rdy * t1;
				return new Point2D.Double((int) ix, (int) iy);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

}
