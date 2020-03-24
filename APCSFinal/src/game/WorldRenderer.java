package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import com.jogamp.opengl.*;

public class WorldRenderer implements GLEventListener, KeyListener {
	private World world;
	private Player player;
	private boolean w, a, s, d, space;
	static long t = 0;

	public WorldRenderer() {
		w = false;
		a = false;
		s = false;
		d = false;
		space = false;
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		final GL2 gl = drawable.getGL().getGL2();
		gl.glShadeModel(GL2.GL_SMOOTH);
		gl.glClearColor(0f, 0f, 0f, 0f);
		gl.glClearDepth(1.0f);
		gl.glEnable(GL2.GL_DEPTH_TEST);
		gl.glDepthFunc(GL2.GL_LEQUAL);
		gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);

		player = new Player(3, Cube.SIZE / 2 + Player.h / 2, 3);
		world = new World(40, 5, 40);
		// 6500 blocks max
		// < 2000 optimal

		Cube.loadTextures(gl);
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		long ti = System.currentTimeMillis();
		final GL2 gl = drawable.getGL().getGL2();

		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();

		if (d)
			player.right();
		if (a)
			player.left();
		if (w)
			player.forward();
		if (s)
			player.backward();
		if (space)
			player.jump();

		world.act(gl, player);

		drawable.getAnimator().getTotalFPS();
		reshape(drawable, 0, 0, drawable.getSurfaceWidth(), drawable.getSurfaceHeight());

		gl.glFlush();

		/*
		 * Debugging
		 */
		t += System.currentTimeMillis() - ti;
		// float fps = drawable.getAnimator().getLastFPS();
		// System.out.println("FPS: " +fps+" Frame: "+ t+" Collisions: " +Player.t+ "
		// Rendering: " +Cube.t);

		t = 0;
		Player.t = 0;
		Cube.t = 0;
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		final GL2 gl = drawable.getGL().getGL2();
		if (height <= 0)
			height = 1;

		final float h = (float) width / (float) height;
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();

		player.render(drawable, h);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
			System.exit(0);

		if (e.getKeyCode() == KeyEvent.VK_D)
			d = true;
		if (e.getKeyCode() == KeyEvent.VK_A)
			a = true;
		if (e.getKeyCode() == KeyEvent.VK_W)
			w = true;
		if (e.getKeyCode() == KeyEvent.VK_S)
			s = true;
		if (e.getKeyCode() == KeyEvent.VK_SPACE)
			space = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE)
			space = false;
		if (e.getKeyCode() == KeyEvent.VK_D)
			d = false;
		else if (e.getKeyCode() == KeyEvent.VK_A)
			a = false;
		if (e.getKeyCode() == KeyEvent.VK_W)
			w = false;
		else if (e.getKeyCode() == KeyEvent.VK_S)
			s = false;

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
	}
}
