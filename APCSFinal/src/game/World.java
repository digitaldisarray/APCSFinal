package game;

import com.jogamp.opengl.GL2;

public class World {
	private Matrix3D world;

	public World(int size) {
		this(size, size, size);
	}

	public World(int sizex, int sizey, int sizez) {
		world = new Matrix3D();

		for (int x = 0; x < sizex; x++)
			for (int z = 0; z < sizez; z++)
				world.setCube(x * Cube.SIZE, 0, z * Cube.SIZE, Cube.GROUND);

		for (int z = 5; z < sizez - 3; z++)
			world.setCube(10, 1 * Cube.SIZE, z * Cube.SIZE, Cube.WALL);

	}

	public void act(GL2 gl, Player p) {
		p.act();
		for (Cube c : world.getCubes()) {
			// Check if c is in render range
			p.collide(c);
			c.draw(gl);
		}

	}

}
