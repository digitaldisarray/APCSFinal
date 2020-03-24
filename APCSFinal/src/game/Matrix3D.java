package game;

import java.util.*;

public class Matrix3D {
	private HashMap<String, Cube> cubes;

	public Matrix3D() {
		cubes = new HashMap<String, Cube>();
	}

	public Cube getCube(int x, int y, int z) {
		return getCube(new Vector3D(x, y, z));
	}

	public Cube getCube(Vector3D p) {
		return cubes.get(p.toString());
	}

	public void setCube(float x, float y, float z, int type) {
		setCube(new Vector3D(x, y, z), type);
	}

	public void setCube(Vector3D p, int type) {
		cubes.remove(p.toString());
		cubes.put(p.toString(), new Cube(p, type));
	}

	public Collection<Cube> getCubes() {
		return cubes.values();
	}

}
