package game;

import java.awt.*;
import java.util.Scanner;
import javax.swing.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.*;

public class Main {
	private JFrame frame;
	private FPSAnimator animator;
	private GLCanvas canvas;
	private ServerConnection connection;

	public void setUpGameplay() {
		WorldRenderer renderer = new WorldRenderer();

		canvas = new GLCanvas();
		canvas.addGLEventListener(renderer);
		canvas.addKeyListener(renderer);
		canvas.setFocusable(true);

		animator = new FPSAnimator(canvas, 50, true);
		animator.setUpdateFPSFrames(3, null);
	}

	public void setUpFrame() {
		frame = new JFrame();

		frame.getContentPane().setCursor(Toolkit.getDefaultToolkit()
				.createCustomCursor(Toolkit.getDefaultToolkit().getImage(""), new Point(), ""));

		frame.getContentPane().add(canvas);

		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setUndecorated(true);
		frame.setVisible(true);

	}

	public void setUpServerConnection() {
		connection = new ServerConnection();
		Scanner in = new Scanner(System.in);

		System.out.println("Server IP: ");
		connection.connectToServer(in.nextLine());
		System.out.println("Username: ");
		connection.setName(in.nextLine());

		connection.sendMessage();
		in.close();
	}

	public void startGame() {
		animator.start();
	}

	public static void main(String[] args) {

		Main main = new Main();

		main.setUpGameplay();

		// main.setUpServerConnection();

		main.setUpFrame();

		main.startGame();

	}

}
