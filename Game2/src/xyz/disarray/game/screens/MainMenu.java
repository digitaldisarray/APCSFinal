package xyz.disarray.game.screens;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;
import xyz.disarray.game.screens.components.Button;

public class MainMenu {

	private ArrayList<Button> buttons = new ArrayList<>();

	public MainMenu() {

		// This entire button system is bad but it works
		buttons.add(new Button("Singleplayer"));
		buttons.add(new Button("Multiplayer"));
		buttons.add(new Button("Options"));
	}

	public void draw(PApplet g) {
		g.rectMode(PConstants.CORNER);

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
