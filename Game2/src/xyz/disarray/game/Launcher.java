package xyz.disarray.game;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import processing.awt.PSurfaceAWT;
import processing.core.PApplet;
import xyz.disarray.game.util.MessageGenerator;

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
		window.setTitle("Epic - " + MessageGenerator.generate());
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				window.setResizable(false);
			}
		});

		window.setVisible(true);
		canvas.requestFocus();
	}
}
