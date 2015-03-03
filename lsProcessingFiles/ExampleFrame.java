import java.awt.BorderLayout;

import javax.swing.JFrame;

import org.nlogo.api.LogoList;

import processing.core.PApplet;

 public class ExampleFrame extends JFrame {
	 /**
	 * 
	 */
	private static final long serialVersionUID = -1574741235087050861L;
	private Embedded embedded;
	LogoList modelsInfo;

	public ExampleFrame() {
         super("LevelSpace Relationship Fixer");

         setLayout(new BorderLayout());
         newEmbedded();
         
     }
	public PApplet embedded(){
		return embedded;
	}
	
	
	public void setModelsInfo(LogoList alist){
		modelsInfo = alist;
		
	}
	
	public void updateModelsInfo(){
		embedded.setModelsInfo(modelsInfo);
	}
	
	public void reset(){
		embedded.dispose();
	}
	
	public void newEmbedded(){
        embedded = new Embedded();
        add(embedded, BorderLayout.WEST);

        // important to call this whenever embedding a PApplet.
        // It ensures that the animation thread is started and
        // that other internal variables are properly set.
        embedded.init();
        this.setVisible(true);
        this.setSize(800,800);
        embedded.draw();
	}
	
	public void kill(){
		embedded.dispose();
		this.dispose();
	}
	
 }