package game;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;

import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.glu.GLU;

public class Camera {
	private Robot robot;
	private Vector3D pos, center, forward, right, up;
	private GLU glu;
	private float fov, viewDistance, pan, tilt;
	private final float zNear = 0.01f;
	private boolean setup;
	private Point mousei;

	public Camera(float x, float y, float z) {
		this(new Vector3D(x, y, z));
	}

	public Camera(Vector3D pos) {
		this(pos, 60f, 40f);
	}

	public Camera(Vector3D pos, float fov, float viewDistance) {
		this.pos = pos;

		forward = new Vector3D(0, 0, 1);
		right = new Vector3D(1, 0, 0);
		up = new Vector3D(0, 1, 0);

		glu = new GLU();
		this.fov = fov;
		this.viewDistance = viewDistance;

		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		setup = false;

	}

	public void render(GLAutoDrawable gl, float aspect) {
		if (!setup) {
			robot.mouseMove(gl.getSurfaceWidth() / 2, gl.getSurfaceHeight() / 2);
			setup = true;
		}

		glu.gluPerspective(fov, aspect, zNear, viewDistance);

		calcDirection(gl.getSurfaceWidth(), gl.getSurfaceHeight());

		center = pos.add(forward);
		glu.gluLookAt(pos.getX(), pos.getY() + Cube.SIZE, pos.getZ(), center.getX(), center.getY() + Cube.SIZE,
				center.getZ(), up.getX(), up.getY(), up.getZ());

	}

	private void calcDirection(int width, int height) {
		Point mousef = MouseInfo.getPointerInfo().getLocation();

		if (mousei == null)
			mousei = new Point(mousef.x, mousef.y);

		// means that the mouse went off the screen to the left so move it to the right
		if (mousef.x < 5 && (mousef.x - mousei.x) < 0) {
			robot.mouseMove(width, mousef.y);
			mousef.x = width;
			mousei.x = width;
		}

		// means that the mouse went off the screen to the right so move it to the left
		if (mousef.x > width - 5 && (mousef.x - mousei.x) > 0) {
			robot.mouseMove(0, mousef.y);
			mousef.x = 0;
			mousei.x = 0;
		}

		// means that the mouse went up off the screen so move it to the bottom
		if (mousef.y < 5 && (mousef.y - mousei.y) < 0) {
			robot.mouseMove(mousef.x, height);
			mousef.y = height;
			mousei.y = height;
		}

		// means that the mouse went down off the screen so move it to the top
		if (mousef.y > height - 5 && (mousef.y - mousei.y) > 0) {
			robot.mouseMove(mousef.x, 0);
			mousef.y = 0;
			mousei.y = 0;
		}

		pan += map(mousef.x - mousei.x, 0, width, 0, (float) Math.PI * 2); // * xSensitivity;
		tilt += map(mousef.y - mousei.y, 0, height, 0, (float) Math.PI); // * ySensitivity;
		tilt = clamp(tilt, (float) -Math.PI / 2.01f, (float) Math.PI / 2.01f);

		// tan of pi/2 or -pi/2 is undefined so if it happens to be exactly that
		// increase it so the code works
		if (tilt == Math.PI / 2)
			tilt += 0.001f;
		if (tilt == -Math.PI / 2)
			tilt -= 0.001f;

		forward = new Vector3D((float) Math.cos(pan), (float) Math.tan(tilt), (float) Math.sin(pan));

		// make it a unit vector because the direction is all that matters
		forward = forward.mult(-1);
		forward = forward.normalize();
		// subtract pi/2 from pan to get the vector perpendicular to forward to show
		// which way is right
		right = new Vector3D((float) Math.cos(pan - Math.PI / 2), 0, (float) Math.sin(pan - Math.PI / 2));
		right = right.normalize();

		mousei = mousef;
	}

	private float map(float value, float start1, float stop1, float start2, float stop2) {
		float range1 = stop1 - start1;
		float range2 = stop2 - start2;

		float percentage = value / range1;

		return percentage * range2;
	}

	private float clamp(float x, float min, float max) {
		if (x > max)
			return max;
		if (x < min)
			return min;
		return x;
	}

	public void moveTo(float x, float y, float z) {
		moveTo(new float[] { x, y, z });
	}

	public void moveTo(float[] pos) {
		moveTo(new Vector3D(pos));
	}

	public void moveTo(Vector3D pos) {
		this.pos = pos;
	}

	public Vector3D getPos() {
		return pos;
	}

	public void moveX(float dis) {
		pos = pos.add(right.mult(dis));
	}

	public void moveZ(float dis) {
		pos = pos.add(forward.mult(dis));
	}

	public void moveY(float dis) {
		pos = pos.add(up.mult(dis));
	}

}
