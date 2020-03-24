package game;

public class Player extends Camera {
	public static final float w = 1, h = 1, l = 1;
	private static final float dis = 0.1f, gravity = -0.03f, jump_velocity = 0.3f;
	private boolean grounded;
	private float yVelocity;
	static long t = 0;

	public Player(float x, float y, float z) {
		super(x, y, z);
		grounded = false;
		yVelocity = 0;
	}

	public void collide(Cube c) {
		long ti = System.currentTimeMillis();

		float leftBound = getPos().getX() - w / 2;
		float rightBound = getPos().getX() + w / 2;
		float bottomBound = getPos().getY() - h / 2;
		float frontBound = getPos().getZ() + l / 2;
		float backBound = getPos().getZ() - l / 2;

		if (c.isPointInside(getPos().getX(), c.getCenter().getY(), getPos().getZ())) {
			if (c.getTopEdge() > bottomBound) {
				moveTo(getPos().getX(), c.getTopEdge() + h / 2, getPos().getZ());
				yVelocity = 0;
				grounded = true;
			} else if (bottomBound == c.getTopEdge())
				grounded = true;
		}

		if (c.isPointInside(leftBound, getPos().getY(), getPos().getZ())) {
			moveTo(c.getRightEdge() + w / 2, getPos().getY(), getPos().getZ());
		} else if (c.isPointInside(rightBound, getPos().getY(), getPos().getZ())) {
			moveTo(c.getLeftEdge() - w / 2, getPos().getY(), getPos().getZ());

		}
		if (c.isPointInside(getPos().getX(), getPos().getY(), frontBound)) {
			moveTo(getPos().getX(), getPos().getY(), c.getBackEdge() - l / 2);
		} else if (c.isPointInside(getPos().getX(), getPos().getY(), backBound)) {
			moveTo(getPos().getX(), getPos().getY(), c.getFrontEdge() + l / 2);
		}

		t += System.currentTimeMillis() - ti;
	}

	public void act() {
		if (!grounded) {
			moveY(yVelocity);
			yVelocity += gravity;
		} else {
			grounded = false;
		}
	}

	public String toString() {
		return "[(" + getPos().getX() + ", " + getPos().getY() + ", " + getPos().getZ() + ")" + "]";
	}

	public void forward() {
		float y = getPos().getY();
		moveZ(dis);
		moveTo(getPos().getX(), y, getPos().getZ());
	}

	public void backward() {
		float y = getPos().getY();
		moveZ(-dis);

		moveTo(getPos().getX(), y, getPos().getZ());
	}

	public void right() {
		float y = getPos().getY();
		moveX(dis);

		moveTo(getPos().getX(), y, getPos().getZ());
	}

	public void left() {
		float y = getPos().getY();
		moveX(-dis);

		moveTo(getPos().getX(), y, getPos().getZ());
	}

	public void jump() {
		if (grounded) {
			grounded = false;
			yVelocity = jump_velocity;
		}
	}

}
