package xyz.disarray.game;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import processing.core.PApplet;
import xyz.disarray.game.entities.Bullet;
import xyz.disarray.game.entities.Entity;
import xyz.disarray.game.entities.LocalPlayer;
import xyz.disarray.game.entities.RayBullet;
import xyz.disarray.game.screens.MainMenu;
import xyz.disarray.game.screens.Singleplayer;
import xyz.disarray.game.util.RayCasting;

public class Game extends PApplet {

	// This is the instance of our main menu, might want to reconsider how we handle
	// menus
	private MainMenu menu;

	private Singleplayer singleplayer; // Instance of the singleplayer game
	private LocalPlayer player; // The local player (shared across multiplayer and single player)
	private GameState state; // What state of the game we are currently on

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
	}

	public void draw() {
		background(200);

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

			// Draw
			singleplayer.draw(this);
			player.draw(this);
		}

	}

	public void mousePressed() {
		if (state == GameState.MENU) {
			int code = menu.clickMouse(mouseX, mouseY);

			/*
			 * 0 - Singleplayer 1 - Multiplayer 2 - Options
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
				// TODO: Remove this and do a better check for the type of bullet we are
				// checking collisions of
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
									Point2D p = RayCasting.getLineIntersection(b2.getLine(), l);
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

}
