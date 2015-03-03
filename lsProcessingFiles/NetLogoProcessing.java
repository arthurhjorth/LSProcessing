import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PGraphics;
import processing.core.PGraphicsJava2D;

public class NetLogoProcessing extends PApplet {


	/**
	 * 
	 */
	private static final long serialVersionUID = 7587604932372737852L;

	int[] colors = {0,0,0};
	PFont f;
	String s;

	public void setup() {
		size(200, 200);
		background(0,255,255);
		f = createFont("Arial",16,true); // STEP 3 Create Font
		s = "start";
		noLoop();
	}

	public void draw() {
		background((int)this.random(255),(int)this.random(255),(int)this.random(255));
		  textFont(f,16);                 // STEP 4 Specify font to be used
		  fill(0);                        // STEP 5 Specify font color 
		  text(s,10,100);  // STEP 6 Display Text
		
	}
	
	public void showString(String s){
		this.s =s; 
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