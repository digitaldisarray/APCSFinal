package xyz.disarray.game.screens.components;

import java.awt.Color;

import processing.core.PApplet;
import processing.core.PConstants;

public class Button {
	private String text;
	private float currentX, currentY, currentWidth, currentHeight;

	/**
	 * Draws a button with (x, y) as the center.
	 * 
	 * @param x    The center x coordinate
	 * @param y    The center y coordinate
	 * @param text The text on the button
	 */
	public Button(String text) {
		this.text = text;
	}

	public void draw(PApplet g, float x, float y) {
		currentWidth = g.textWidth(text);
		currentHeight = g.textAscent() * 1.5f;
		currentX = x - currentWidth / 2f;
		currentY = y;

		// Draw button background
		g.fill(100);
		g.noStroke();
		g.rect(currentX, currentY, currentWidth, currentHeight, 4);

		// Draw text
		g.fill(0);
		g.textAlign(PConstants.LEFT, PConstants.TOP);
		g.text(text, currentX, y + 4);

		// Draw border
		g.noFill();
		g.stroke(0);
		g.rect(currentX, currentY, currentWidth, currentHeight, 4);
	}

	public void draw(PApplet g, float x, float y, float width, float height, Color color) {
		currentWidth = width;
		currentHeight = height;
		currentX = x;
		currentY = y;

		// Draw button background
		g.fill(color.getRed(), color.getGreen(), color.getBlue());
		g.noStroke();
		g.rect(currentX, currentY, currentWidth, currentHeight);

		// Draw text
		g.fill(0);
		
		float startX =currentX + (currentWidth - g.textWidth(text)) / 2;

		g.text(text, startX, currentY + currentHeight / 3, g.textWidth(text));
		//g.text(text, currentX, y + 4);

		// Draw border
		g.noFill();
		g.stroke(0);
		g.rect(currentX, currentY, currentWidth, currentHeight, 4);
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
}
