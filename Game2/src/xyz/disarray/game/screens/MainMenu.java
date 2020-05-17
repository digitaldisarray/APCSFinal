package xyz.disarray.game.screens;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;
import xyz.disarray.game.screens.components.RecButton;

public class MainMenu extends Screen {

	private ArrayList<RecButton> buttons = new ArrayList<>();

	// Very great coding
	private float col = 0f;
	private String[] epicCode = {"Bad code, even worse gameplay", "No motivation", "Lacking Taylor tips and tricks"};
	private int epic;
	
	public MainMenu() {
		// This entire button system is bad but it works :)
		buttons.add(new RecButton("Singleplayer"));
		buttons.add(new RecButton("Multiplayer"));
		buttons.add(new RecButton("Options"));
		
		epic = (int) (Math.random() * epicCode.length);
	}

	public void draw(PApplet g) {
		// Cover up randomly generated background
		g.background(40f);
		
		// Rainbow (good code)
		g.colorMode(PConstants.HSB);
		g.fill(col, 255, 255);
		g.textSize(120);
		g.text("Game", g.width / 2 - g.textWidth("Game") / 2, 10);
		g.textSize(20);
		g.fill(col, 255, 255);
		g.stroke(255);
		g.text(epicCode[epic], g.width / 2 - g.textWidth(epicCode[epic]) / 2, 135);
		col += 0.5;
		if(col > 255f)
			col = 0f;
		
		g.colorMode(PConstants.RGB);
		
		g.rectMode(PConstants.CORNER);

		// Is hovered check in draw cause I don't want to add PApplet args to update
		for (RecButton b : buttons)
			if (b.isPointInside(g.mouseX, g.mouseY))
				b.hover();

		// Buttons
		g.textSize(40);
		for (int i = 0; i < buttons.size(); i++) {
			buttons.get(i).draw(g, -1, 300 + 100 * i);
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

	@Override
	public void update() {

	}
}
