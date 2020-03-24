package game;

import com.jogamp.opengl.math.VectorUtil;

public class Vector3D {

	private float[] pos;

	public Vector3D(float x, float y, float z) {
		this(new float[] { x, y, z });
	}

	public Vector3D(float[] pos) {
		this.pos = pos;
	}

	public Vector3D add(Vector3D v2) {
		return new Vector3D(VectorUtil.addVec3(new float[3], pos, v2.pos));
	}

	public Vector3D normalize() {
		return new Vector3D(VectorUtil.normalizeVec3(new float[3], pos));
	}

	public Vector3D mult(float x) {
		return new Vector3D(VectorUtil.scaleVec3(new float[3], pos, x));
	}

	public float[] getPos() {
		return pos;
	}

	public float getX() {
		return pos[0];
	}

	public float getY() {
		return pos[1];
	}

	public float getZ() {
		return pos[2];
	}

	public String toString() {
		return String.format("%.2f, %.2f, %.2f", pos[0], pos[1], pos[2]);

	}

}
