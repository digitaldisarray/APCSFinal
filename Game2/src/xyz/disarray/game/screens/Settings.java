package xyz.disarray.game.screens;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;
import xyz.disarray.game.screens.components.Button;

public class Settings extends Screen {

	// Again, this entire system of buttons is trash but works
	private ArrayList<Button> buttons = new ArrayList<>();

	// TODO: Add settings that are actually useful? (big maybe)
	public Settings() {
		buttons.add(new Button("return; <-- Comedy"));
		buttons.add(new Button("Randomize Colors"));
		buttons.add(new Button("Choose Colors"));
		buttons.add(new Button("Choose Class"));


	}

	public void draw(PApplet g) {

		g.rectMode(PConstants.CORNER);

		// Buttons
		for (int i = 0; i < buttons.size(); i++) {
			buttons.get(i).draw(g, g.width / 2, 300 + 40 * i);
		}
	}

	// Again, this entire button system is bad but it works which is all that we
	// care abt
	public int clickMouse(int x, int y) {
		
			String clicked = null;
			for (int i = 0; i < buttons.size(); i++)
				if (buttons.get(i).isPointInside(x, y))
					clicked = buttons.get(i).getName();

			if (clicked == null)
				return -1;

			if (clicked.equals("return; <-- Comedy"))
				return 0;

			if (clicked.equals("Randomize Colors"))
				return 1;
			
			if (clicked.equals("Choose Colors"))
				return 2;
			if (clicked.equals("Choose Class"))
				return 3;

		
		return -1;
	}

	@Override
	public void update() {

	}

}
