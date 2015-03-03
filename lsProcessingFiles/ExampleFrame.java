import java.awt.BorderLayout;

import javax.swing.JFrame;

 public class ExampleFrame extends JFrame {
	 /**
	 * 
	 */
	private static final long serialVersionUID = -1574741235087050861L;
	private NetLogoProcessing nlp;
	
	public ExampleFrame() {
         super("NetLogo to Procesing Extension Frame");
         setLayout(new BorderLayout());
         nlp = new NetLogoProcessing();
         add(nlp, BorderLayout.CENTER);
         nlp.init();
         this.setSize(600,600);
         this.setVisible(true);
     }
	
	public void kill(){
		nlp.dispose();
		this.dispose();
	}
	
	public NetLogoProcessing nlp(){
		return nlp;
	}
	
 }