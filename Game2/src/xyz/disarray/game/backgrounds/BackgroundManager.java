package xyz.disarray.game.backgrounds;

import java.util.ArrayList;

public class BackgroundManager {
	
	/*
	 * Potential changes:
	 * - Only image backgrounds when in a menu
	 * - Mode in settings to disable/enable not serious backgrounds
	 */
	
	private ArrayList<Background> backgrounds = new ArrayList<>();
	private int index;
	
	public BackgroundManager() {
		backgrounds.add(new Wave());
	//	backgrounds.add(new Man());
		//backgrounds.add(new CellsInterlinked());
		
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
