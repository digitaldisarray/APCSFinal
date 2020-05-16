package xyz.disarray.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import processing.awt.PSurfaceAWT;
import processing.core.PApplet;

public class Launcher {
	public static void main(String[] args) {
//		Game drawing = new Game();
//		PApplet.runSketch(new String[] { "" }, drawing);
//		PSurfaceAWT surf = (PSurfaceAWT) drawing.getSurface();
//		SmoothCanvas canvas = (SmoothCanvas) surf.getNative();
//		JFrame window = (JFrame) canvas.getFrame();
//
//		window.setResizable(false);
////		window.setSize(800, 600);
////		window.setMinimumSize(new Dimension(800, 600));
////		window.setMaximumSize(new Dimension(800, 600));
//		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		window.setTitle("Epic - " + MessageGenerator.generate());
//
//		window.setVisible(true);

		Game drawing = new Game();
		PApplet.runSketch(new String[] { "" }, drawing);
		PSurfaceAWT surf = (PSurfaceAWT) drawing.getSurface();
		PSurfaceAWT.SmoothCanvas canvas = (PSurfaceAWT.SmoothCanvas) surf.getNative();
		JFrame window = (JFrame) canvas.getFrame();

		window.setSize(800, 600);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setTitle("Epic - " + generate());
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				window.setResizable(false);
			}
		});
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		canvas.requestFocus();
		
	}
	
	/**
	 * Selects a message at random from the messages text file to be displayed on the window's title.
	 * 
	 * @return Returns a random message from the messages text file.
	 */
	private static String generate() {
		File file = new File("res/messages");
		Scanner sc;
		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			return "";
		}

		ArrayList<String> messages = new ArrayList<>();

		while (sc.hasNextLine())
			messages.add(sc.nextLine());

		sc.close();

		return messages.get((int) (Math.random() * messages.size()));
	}
}
