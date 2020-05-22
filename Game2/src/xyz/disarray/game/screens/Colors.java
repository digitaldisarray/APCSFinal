package xyz.disarray.game.screens;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import processing.core.PApplet;
import xyz.disarray.game.Game;
import xyz.disarray.game.screens.components.Button;

public class Colors extends Screen {

	private ArrayList<Button> buttons = new ArrayList<>();
	private Color[] colors = new Color[4];

	public Colors() {
		buttons.add(new Button("Set player color"));

		buttons.add(new Button("Set lazer color"));

		buttons.add(new Button("Set enemy color"));

		buttons.add(new Button("Go back"));

		for (int i = 0; i < 4; i++) {
			int r = ((int) (Math.random() * 255));
			int g = ((int) (Math.random() * 255));

			int b = ((int) (Math.random() * 255));

			colors[i] = new Color(r, g, b);

		}

	}

	public void draw(PApplet g) {
		// buttons.get(1).draw(g, x, y);

		float height = g.height;
		float width = g.width;

		buttons.get(0).draw(g, width / 7, height / 2 + height / 10, width / 7, height / 10, colors[0]);
		buttons.get(1).draw(g, 3 * (width / 7), height / 2 + height / 10, width / 7, height / 10, colors[1]);

		buttons.get(2).draw(g, 5 * (width / 7), height / 2 + height / 10, width / 7, height / 10, colors[2]);

		buttons.get(3).draw(g, 3 * (width / 7), height / 2 + 3 * (height / 10), width / 7, height / 10, colors[3]);
	}

	// This entire button system is bad but it works
	public int clickMouse(int x, int y) {
		String clicked = null;
		for (int i = 0; i < buttons.size(); i++)
			if (buttons.get(i).isPointInside(x, y))
				clicked = buttons.get(i).getName();
		if (clicked == null) {
			return -1;
		}

		else if (clicked.equals("Set player color")) {
			Color playerColor = createColor();
			Game.good = playerColor;
		}

		else if (clicked.equals("Set lazer color")) {
			Color lazerColor = createColor();
		}

		else if (clicked.equals("Set enemy color")) {
			Color badColor = createColor();
			Game.bad = badColor;
		} else if (clicked.equals("Go back")) {
			return 1;
		}

		return -1;
	}

	@Override
	public void update() {

	}

	private Color createColor() {
		int pc1 = 0, pc2 = 0, pc3 = 0; // Initializes integers used to create RGB so the color is black if not changed

		try {// Tries to create RBG value from input
			JOptionPane.showMessageDialog(null,
					"Enter a value between 0 and 255. Invalid inputs will reset color to black");

			pc1 = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter level of red", null));
			pc2 = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter level of green", null));
			pc3 = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter level of blue", null));
		} catch (Exception e) {

		}
		if (pc1 < 0 || pc1 > 255 || pc2 < 0 || pc2 > 255 || pc3 < 0 || pc3 > 255) {
			return new Color(0, 0, 0);// Creates color from input
		}
		return new Color(pc1, pc2, pc3);// Creates color from input

	}

}
