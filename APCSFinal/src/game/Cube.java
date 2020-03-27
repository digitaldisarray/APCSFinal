package game;

import java.io.File;
import java.io.IOException;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

public class Cube {
	private Vector3D center, ftl, ftr, fbl, fbr, btl, btr, bbl, bbr;
	public static final float SIZE = 1f;
	public static final int GROUND = 1;
	public static final int WALL = 2;
	private static int grass_texture;
	private static int dirt_texture;
	private int type;
	static long t = 0;

	public Cube(float x, float y, float z, int type) {
		this(new Vector3D(x, y, z), type);
	}

	public Cube(Vector3D center, int type) {
		this.center = center;

		ftl = new Vector3D(-SIZE / 2, SIZE / 2, SIZE / 2);
		ftr = new Vector3D(SIZE / 2, SIZE / 2, SIZE / 2);
		fbl = new Vector3D(-SIZE / 2, -SIZE / 2, SIZE / 2);
		fbr = new Vector3D(SIZE / 2, -SIZE / 2, SIZE / 2);

		btl = new Vector3D(-SIZE / 2, SIZE / 2, -SIZE / 2);
		btr = new Vector3D(SIZE / 2, SIZE / 2, -SIZE / 2);
		bbl = new Vector3D(-SIZE / 2, -SIZE / 2, -SIZE / 2);
		bbr = new Vector3D(SIZE / 2, -SIZE / 2, -SIZE / 2);

		this.type = type;
	}

	// TODO: Seperate TextureManager class
	public static void loadTextures(GL2 gl) {
		gl.glEnable(GL2.GL_TEXTURE_2D);

		try {
			File im = new File("res/Grass.jpg");
			Texture t = TextureIO.newTexture(im, true);
			grass_texture = t.getTextureObject(gl);
		} catch (IOException e) {
			grass_texture = -1;
			e.printStackTrace();
		}

		try {
			File im = new File("res/Dirt.jpg");
			Texture t = TextureIO.newTexture(im, true);
			dirt_texture = t.getTextureObject(gl);
		} catch (IOException e) {
			dirt_texture = -1;
			e.printStackTrace();
		}

	}

	public void draw(GL2 gl) {
		long ti = System.currentTimeMillis();

		gl.glPushMatrix();
		gl.glTranslatef(center.getX(), center.getY(), center.getZ());

		if (type == GROUND)
			drawGround(gl);
		if (type == WALL)
			drawWall(gl);

		gl.glPopMatrix();

		t += System.currentTimeMillis() - ti;
	}

	private void drawGround(GL2 gl) {
		gl.glBindTexture(GL2.GL_TEXTURE_2D, grass_texture);

		// giving different colors to different sides
		gl.glBegin(GL2.GL_QUADS); // Start Drawing The Cube

		// Front face
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(ftl.getX(), ftl.getY(), ftl.getZ());
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(ftr.getX(), ftr.getY(), ftr.getZ());
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(fbr.getX(), fbr.getY(), fbr.getZ());
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(fbl.getX(), fbl.getY(), fbl.getZ());

		// Right Face
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(ftr.getX(), ftr.getY(), ftr.getZ());
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(btr.getX(), btr.getY(), btr.getZ());
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(bbr.getX(), bbr.getY(), bbr.getZ());
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(fbr.getX(), fbr.getY(), fbr.getZ());

		// Back face
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(btr.getX(), btr.getY(), btr.getZ());
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(btl.getX(), btl.getY(), btl.getZ());
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(bbl.getX(), bbl.getY(), bbl.getZ());
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(bbr.getX(), bbr.getY(), bbr.getZ());

		// Left face
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(btl.getX(), btl.getY(), btl.getZ());
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(ftl.getX(), ftl.getY(), ftl.getZ());
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(fbl.getX(), fbl.getY(), fbl.getZ());
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(bbl.getX(), bbl.getY(), bbl.getZ());

		// Top face
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(ftl.getX(), ftl.getY(), ftl.getZ());
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(ftr.getX(), ftr.getY(), ftr.getZ());
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(btr.getX(), btr.getY(), btr.getZ());
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(btl.getX(), btl.getY(), btl.getZ());

		// Bottom face
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(fbl.getX(), fbl.getY(), fbl.getZ());
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(fbr.getX(), fbr.getY(), fbr.getZ());
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(bbr.getX(), bbr.getY(), bbr.getZ());
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(bbl.getX(), bbl.getY(), bbl.getZ());

		gl.glEnd();
	}

	private void drawWall(GL2 gl) {
		gl.glBindTexture(GL2.GL_TEXTURE_2D, dirt_texture);

		// giving different colors to different sides
		gl.glBegin(GL2.GL_QUADS); // Start Drawing The Cube

		// Front face
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(ftl.getX(), ftl.getY(), ftl.getZ());
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(ftr.getX(), ftr.getY(), ftr.getZ());
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(fbr.getX(), fbr.getY(), fbr.getZ());
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(fbl.getX(), fbl.getY(), fbl.getZ());

		// Right Face
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(ftr.getX(), ftr.getY(), ftr.getZ());
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(btr.getX(), btr.getY(), btr.getZ());
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(bbr.getX(), bbr.getY(), bbr.getZ());
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(fbr.getX(), fbr.getY(), fbr.getZ());

		// Back face
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(btr.getX(), btr.getY(), btr.getZ());
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(btl.getX(), btl.getY(), btl.getZ());
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(bbl.getX(), bbl.getY(), bbl.getZ());
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(bbr.getX(), bbr.getY(), bbr.getZ());

		// Left face
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(btl.getX(), btl.getY(), btl.getZ());
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(ftl.getX(), ftl.getY(), ftl.getZ());
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(fbl.getX(), fbl.getY(), fbl.getZ());
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(bbl.getX(), bbl.getY(), bbl.getZ());

		// Top face
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(ftl.getX(), ftl.getY(), ftl.getZ());
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(ftr.getX(), ftr.getY(), ftr.getZ());
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(btr.getX(), btr.getY(), btr.getZ());
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(btl.getX(), btl.getY(), btl.getZ());

		// Bottom face
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(fbl.getX(), fbl.getY(), fbl.getZ());
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(fbr.getX(), fbr.getY(), fbr.getZ());
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(bbr.getX(), bbr.getY(), bbr.getZ());
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(bbl.getX(), bbl.getY(), bbl.getZ());

		gl.glEnd();
	}

	public boolean isPointInside(float x, float y, float z) {
		return Math.abs(x - center.getX()) < SIZE / 2 && Math.abs(y - center.getY()) < SIZE / 2
				&& Math.abs(z - center.getZ()) < SIZE / 2;
	}

	public boolean isPointInside(Vector3D p) {
		return isPointInside(p.getX(), p.getY(), p.getZ());
	}

	public float getLeftEdge() {
		return ftl.add(center).getX();
	}

	public float getRightEdge() {
		return ftr.add(center).getX();
	}

	public float getFrontEdge() {
		return ftl.add(center).getZ();
	}

	public float getBackEdge() {
		return btl.add(center).getZ();
	}

	public float getTopEdge() {
		return ftl.add(center).getY();
	}

	public float getBottomEdge() {
		return fbl.add(center).getY();
	}

	public String toString() {
		return "[(" + center.getX() + ", " + center.getY() + ", " + center.getZ() + "), " + SIZE + "]";
	}

	public Vector3D getCenter() {
		return center;
	}
}
