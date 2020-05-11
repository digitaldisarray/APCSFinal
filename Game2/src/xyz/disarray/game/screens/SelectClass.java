package xyz.disarray.game.screens;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import processing.core.PApplet;
import processing.core.PConstants;
import xyz.disarray.game.Game;
import xyz.disarray.game.screens.components.Button;

public class SelectClass extends Screen {

	private ArrayList<Button> buttons = new ArrayList<>();
	private Color[] colors = new Color[3];

	public SelectClass() {
		buttons.add(new Button("Heavy class"));

		buttons.add(new Button("Light class"));

		buttons.add(new Button("Go back"));
		
		buttons.add(new Button("Base class"));


		for (int i = 0; i < 3; i++) {
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

		buttons.get(1).draw(g, 5 * (width / 7), height / 2 + height / 10, width / 7, height / 10, colors[1]);

		buttons.get(2).draw(g, 3 * (width / 7), height / 2 + 3 * (height / 10), width / 7, height / 10, colors[2]);
		
		buttons.get(3).draw(g, 3 * (width / 7), height / 2 + height / 10, width / 7, height / 10, colors[1]);

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

		else if (clicked.equals("Heavy class")) {
			Game.moveSpeed = Game.DEF_SPEED / 2;
			Game.damage = Game.DEF_DAMAGE * 2;
			Game.health = Game.DEF_HEALTH * 2;

		}

		else if (clicked.equals("Light class")) {
			Game.moveSpeed = Game.DEF_SPEED * 2;
			Game.damage = Game.DEF_DAMAGE - 1;
			Game.health = Game.DEF_HEALTH / 2;
		}
		
		else if (clicked.equals("Base class")) {
			Game.moveSpeed = Game.DEF_SPEED;
			Game.damage = Game.DEF_DAMAGE;
			Game.health = Game.DEF_HEALTH;
		}else if (clicked.equals("Go back")) {
			return 1;
		}

		return -1;
	}

	@Override
	public void update() {

	}

}
