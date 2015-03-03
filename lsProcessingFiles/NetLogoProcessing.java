import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PGraphicsJava2D;

/*
 * Abstract class for doing Processing in NetLogo. Extend this class
 * and fill in your code
 */
public abstract class NetLogoProcessing extends PApplet {

	private static final long serialVersionUID = 7587604932372737852L;
	
	public abstract void setup();
	public abstract void draw();
	
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