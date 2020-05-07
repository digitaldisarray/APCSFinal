package xyz.disarray.game.backgrounds;

import java.util.ArrayList;

import processing.core.PApplet;
import xyz.disarray.game.util.Vector2D;

public class CellsInterlinked extends Background {

	ArrayList<Vector2D> vecs;

	@Override
	public void setup() {
		vecs = new ArrayList<>();

		for (int i = 0; i < 25; i++)
			vecs.add(new Vector2D(Math.random() * 800, Math.random() * 600));

	}

	@Override
	public void draw(PApplet g) {
		g.background(0);

		g.textSize(21); 

		for (int i = 0; i < vecs.size(); i++) {
			g.fill((float) Math.random() * 255, (float) Math.random() * 255, (float) Math.random() * 255);
			g.text("cells interlinked", (float) vecs.get(i).getX(), (float) vecs.get(i).getY());
			double newX = vecs.get(i).getX();
			double newY = vecs.get(i).getY();
			
			if(Math.random() < 0.5) {
				newX += 7;
			} else {
				newX -= 7;
			}
			
			if(Math.random() < 0.5) {
				newY += 7;
			} else {
				newY -= 7;
			}
			
			if(newX < -10 || newX > 790)
				newX = Math.random() * 800;
			
			if(newY < -3 || newY >= 600)
				newY = Math.random() * 600;
			
			vecs.set(i, new Vector2D(newX, newY));
		}
	}

}
