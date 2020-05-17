package xyz.disarray.game.screens.components;

import processing.core.PApplet;
import processing.core.PConstants;

public class RecButton {
	private String text;
	private float currentX, currentY, currentWidth, currentHeight;
	private boolean hovered;

	/**
	 * Draws a button with (x, y) as the center.
	 * 
	 * @param x    The center x coordinate
	 * @param y    The center y coordinate
	 * @param text The text on the button
	 */
	public RecButton(String text) {
		this.text = text;
		hovered = false;
	}

	public void draw(PApplet g, float x, float y) {
		currentWidth = g.textWidth(text) + 20;
		currentHeight = g.textAscent() * 1.5f;
		currentX = x;
		currentY = y;

		// Draw button background
		g.fill(100);
		g.noStroke();
		g.rect(currentX, currentY, currentWidth, currentHeight);

		// Draw text
		g.fill(0);
		g.textAlign(PConstants.LEFT, PConstants.TOP);
		g.text(text, currentX, y + 4);

		// Draw border
		g.noFill();
		if(hovered)
			g.stroke((float) (Math.random() * 255), (float) (Math.random() * 255), (float) (Math.random() * 255));
		else
			g.stroke(0);
		g.rect(currentX, currentY, currentWidth, currentHeight);

		// Hover rect on the right
		if (hovered) {
			g.noStroke();
			g.fill((float) (Math.random() * 255), (float) (Math.random() * 255), (float) (Math.random() * 255));
			g.rect(currentX + currentWidth - 18, currentY + 1, 19, currentHeight - 1);
		}

		// Reset hover var
		hovered = false;

	}

	public boolean isPointInside(int x, int y) {
		if (y >= currentY && y <= currentY + currentHeight)
			if (x >= currentX && x <= currentX + currentWidth)
				return true;

		return false;
	}

	public String getName() {
		return text;
	}

	public void hover() {
		hovered = true;
	}

}
