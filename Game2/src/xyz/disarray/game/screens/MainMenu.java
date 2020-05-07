package xyz.disarray.game.screens;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;
import xyz.disarray.game.screens.components.Button;

public class MainMenu {

	private ArrayList<Button> buttons = new ArrayList<>();
	
	public MainMenu(PApplet g) {
		// This entire button system is bad but it works
		buttons.add(new Button("Singleplayer"));
		buttons.add(new Button("Multiplayer"));
		buttons.add(new Button("Options"));
	}

	public void draw(PApplet g) {
		g.rectMode(PConstants.CORNER);
		
		// Meme logo (delete before final version)
		g.textSize(50);
		g.fill(0, 50, 255);
		g.stroke(255, 255, 255);
		g.rect(g.width / 2 - g.textWidth("G A M E") / 2, g.height / 3 + 2, g.textWidth("G A M E"), 50);
		g.fill(255, 50, 100);
		g.text("G A M E", (g.width - g.textWidth("G A M E")) / 2 , g.height / 3);
		g.textSize(12);
		
		// Buttons
		for (int i = 0; i < buttons.size(); i++) {
			buttons.get(i).draw(g, g.width / 2, 300 + 40 * i);
		}
	}

	// This entire button system is bad but it works
	public int clickMouse(int x, int y) {
		String clicked = null;
		for (int i = 0; i < buttons.size(); i++)
			if (buttons.get(i).isPointInside(x, y))
				clicked = buttons.get(i).getName();

		if (clicked == null)
			return -1;

		if (clicked.equals("Singleplayer"))
			return 0;

		if (clicked.equals("Multiplayer"))
			return 1;

		if (clicked.equals("Options"))
			return 2;

		return -1;
	}
}
