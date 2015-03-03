import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PGraphicsJava2D;

public class Embedded extends PApplet {


	/**
	 * 
	 */
	private static final long serialVersionUID = 7587604932372737852L;

	int[] colors = {0,0,0};

	public void setup() {
		size(800, 800);
		frameRate(15);
		stroke(255,255,255);
	}


	public void draw() {
		int r = (int)Math.random() * 255;
		int g = (int)Math.random() * 255;
		int b = (int)Math.random() * 255;
		background(r, g, b);
	}



	protected PGraphics makeGraphics(int iwidth, int iheight,
			String irenderer, String ipath,
			boolean iprimary) {

		PGraphicsJava2D pg = new PGraphicsJava2D();
		pg.setParent(this);
		pg.setPrimary(true);
		pg.setPath(null);
		pg.setSize(iwidth, iheight);
		return pg;
	}

}