import java.awt.Point;
import java.util.ArrayList;
import java.util.Hashtable;

import org.nlogo.api.LogoList;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PGraphicsJava2D;
import controlP5.CallbackEvent;
import controlP5.CallbackListener;
import controlP5.ControlP5;
import controlP5.MultiList;
import controlP5.MultiListButton;

public class Embedded extends PApplet {


	/**
	 * 
	 */
	private static final long serialVersionUID = 7587604932372737852L;

	int[] colors = {0,0,0};

	ArrayList<LSPModel>models = new ArrayList<LSPModel>();
	LogoList modelsInfo;

	ControlP5 myControlP5;
	MultiList l;	
	
	LSPModel eventModel = null;
	LSPModel targetModel = null;
	
	Hashtable<Integer, String> expressions = new Hashtable<Integer, String>();
//	Hashmap<Hash, String> commands = new HashMap<HashMap, String>();


	public void setup() {
		size(800, 800);
		background(0);
		myControlP5 = new ControlP5(this);
		frameRate(15);
		stroke(255,255,255);
	}


	public void draw() {
		background(0);
		for (LSPModel amodel : models){
			amodel.draw();
		}
		if (eventModel != null){
			Point eventMidPoint = eventModel.center();
			line(eventMidPoint.x,eventMidPoint.y,mouseX,mouseY);
		}
	}

	public void updateModels(LogoList modelsList){

	}

	public void mousePressed() {
		eventModel = null;
		targetModel = null;
		if (touchedModel() != null){
			eventModel = touchedModel();
		}
	}
	
	public void mouseReleased(){
		LSPModel theModel = touchedModel();
		if(eventModel != null && theModel != eventModel && theModel != null){
			targetModel = theModel;
			targetModel.newColor();
			eventModel.newColor();
		}
		eventModel = null;
		targetModel = null;
	}
	// AH: 8/27/2014 if models overlap, we will receieve the last model in the list - needs to be fixed
	public LSPModel touchedModel(){
		LSPModel returnModel = null;
		for (LSPModel amodel : models){
			if(amodel.contains(mouseX,  mouseY)){
				returnModel = amodel;
			}
		}
		return returnModel;
	}
	

	public void setModelsInfo(LogoList alist){
		int buttonCounter = 0;
		
		//modelsInfo = alist;
		for(Object o : alist){
			LogoList modelInfo = (LogoList)o;
			LSPModel aModel = new LSPModel(this, modelInfo);
			aModel.rect().setBounds((int)random(600), (int)random(400), 100, 100);
			aModel.newColor();
			models.add(aModel);
		}
		
		myControlP5 = new ControlP5(this);

		l = myControlP5.addMultiList("models",20,20,200,12);
		

		// create a multiListButton which we will use to
		// add new buttons to the multilist
		MultiListButton b;
		MultiListButton c;
		MultiListButton d;
		@SuppressWarnings("unused")
		MultiListButton e;
		@SuppressWarnings("unused")
		MultiListButton f;
		for (LSPModel amodel : models){
//			modelCounter++;
			b = l.add(amodel.modelName + " " + amodel.modelWho, buttonCounter);
			expressions.put(buttonCounter, "ls:model " + amodel.modelWho);
			buttonCounter++;
			c = b.add("Globals for model " + amodel.modelWho, buttonCounter);
			buttonCounter++;
			for (String global : amodel.globals){
				d = c.add(global  + " " + amodel.modelWho, buttonCounter);
				expressions.put(buttonCounter, "\"" + global + "\" ls:of ls:model " + amodel.modelWho);
				buttonCounter++;
			}
			c = b.add("Breeds for model " + amodel.modelWho, buttonCounter);
			buttonCounter++;
			for(String breedname : amodel.breeds.keySet()){
				d = c.add(breedname  + " " + amodel.modelWho, buttonCounter);
				expressions.put(buttonCounter, "\"" + breedname + "\" ls:of ls:model " + amodel.modelWho);
				buttonCounter++;
				for (String varname : amodel.breeds.get(breedname)){
					e = d.add(breedname  + " " + varname  + " " + amodel.modelWho, buttonCounter);
					expressions.put(buttonCounter, "\"[ " + varname + " ] of " + breedname + "\" ls:of ls:model " + amodel.modelWho);
					buttonCounter++;
				}
			}
		}
		b = l.add("Math", buttonCounter);
		buttonCounter++;
		c = b.add("Sum", buttonCounter);
		buttonCounter++;
		c = b.add("Mean", buttonCounter);
		buttonCounter++;
		c = b.add("Median", buttonCounter);
		buttonCounter++;
		c = b.add("Max", buttonCounter);
		buttonCounter++;
		c = b.add("Min", buttonCounter);
		buttonCounter++;
		c = b.add(">", buttonCounter);
		buttonCounter++;
		c = b.add("<", buttonCounter);
		buttonCounter++;
		c = b.add("=", buttonCounter);
		buttonCounter++;
		c = b.add("+", buttonCounter);
		buttonCounter++;
		c = b.add("-", buttonCounter);
		buttonCounter++;
		c = b.add("/", buttonCounter);
		buttonCounter++;
		c = b.add("*", buttonCounter);
		buttonCounter++;
		
		System.out.println("Added all buttons");
		
		CallbackListener cb = new CallbackListener() {
			
			@Override
			public void controlEvent(CallbackEvent e) {
				if(e.getAction() == ControlP5.ACTION_PRESSED){
					System.out.println(expressions.get((int)e.getController().getValue()));
				}
			}
		};
		myControlP5.addCallback(cb);
	}
	
	public void line(Point p1, Point p2){
		line(p1.x, p1.y, p2.x, p2.y);
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