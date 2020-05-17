package xyz.disarray.game.backgrounds;

import java.util.ArrayList;

public class BackgroundManager {
	
	private ArrayList<Background> backgrounds = new ArrayList<>();
	private int index;
	
	public BackgroundManager() {
		backgrounds.add(new Wave());
		// Commented out for now cause processing sucks at drawing images without dropping FPS a ton
	//	backgrounds.add(new Man());
		backgrounds.add(new CellsInterlinked());
		
		for(Background b : backgrounds)
			b.setup();
		
		index = 0;
	}
	
	public void newBackground() {
		index = (int) (Math.random() * backgrounds.size());
	}
	
	public Background getBackground() {
		return backgrounds.get(index);
	}
}
