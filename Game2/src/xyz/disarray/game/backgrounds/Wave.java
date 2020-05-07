package xyz.disarray.game.backgrounds;

import processing.core.PApplet;
import processing.core.PConstants;

/*
 * Original code from user Saskia Freeke on openprocessing.org
 * 
 * https://www.openprocessing.org/sketch/405926
 * 
 */

public class Wave extends Background {

	private float t;
	private float theta;
	private int maxFrameCount = 300; // frameCount, change for faster or slower animation

	public void setup() {
	}

	public void draw(PApplet g) {
		g.pushMatrix();
		g.noStroke();
		g.background(14, 16, 29);
		g.translate(g.width / 2, g.height / 2); // translate 0,0 to center

		t = (float) g.frameCount / maxFrameCount;
		theta = PConstants.TWO_PI * t;

		for (int x = -175; x <= 175; x += 25) {
			for (int y = -100; y <= 155; y += 50) {
				float offSet = 50 * x + y + y; // play with offSet to change map below

				float x2 = PApplet.map(PApplet.cos(-theta + offSet), 0, 1, 0, 25); // map x position
				float y2 = PApplet.map(PApplet.cos(-theta + offSet), 0, 1, 25, 0); // map y position
				float sz2 = PApplet.map(PApplet.sin(-theta + offSet), 0, 1, 15, 45); // map size off the ellipse
				g.fill(250 - (x / 2), 150 + (x / 6), 250 - (y / 2)); // color with gradient created

				g.ellipse(x + x2, y - y2, sz2, sz2);
			}
		}
		g.popMatrix();
	}
}
