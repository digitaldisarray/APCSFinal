package xyz.disarray.game.util;

import java.awt.Color;

public class Colors {
	// Primary colors for the game
	// TODO: Make this adjustable in settings
	public static final Color GOOD = new Color(0, 150, 250);
	public static final Color BAD = new Color(255, 40, 40);
	public static final Color GREY = new Color(100, 100, 100);
	public static final Color DARK_GREY = new Color(50, 50, 50);
	
	/**
	 * Generates colors for bullets so they are either slightly lighter or darker
	 * 
	 * @return A slightly light or dark color for the bullet trail is returned
	 */
	public static Color getBulletColor() {
		return new Color(255, 90 + (int) (Math.random() * 70), 0);
	}
}
