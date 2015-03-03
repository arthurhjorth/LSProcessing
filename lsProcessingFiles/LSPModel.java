import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;

import org.nlogo.api.LogoList;

public class LSPModel {
	Embedded parent;
	Rectangle myRect;
	Color aColor;
	Object model;
	String modelName;
	double modelWho;
	ArrayList<String> globals;
	HashMap<String, ArrayList<String>> breeds;
	
	public LSPModel(Embedded parent, LogoList modelList){
		this.parent = parent;
		
		myRect = new Rectangle();
		aColor = new Color((int)Math.random() * 255,(int)Math.random() * 255,(int)Math.random() * 255);
		
		globals = new ArrayList<String>();
		breeds = new HashMap<String,ArrayList<String>>();
		// The list is formed like this:
		// [[<modelObjectRef> model-who model-name] [[globals] [breed-name1 [breed-vars]] [breed-name2 [breed-vars]]]]
		// get the model
		LogoList baseInfo = (LogoList)modelList.first();
		model = baseInfo.first();
		baseInfo = baseInfo.butFirst();
		modelWho = new Double(baseInfo.first().toString());
		baseInfo = baseInfo.butFirst();
		modelName = baseInfo.first().toString();

		// then the list containing its information
		modelList = modelList.butFirst();

		// first object is a list of globals
		LogoList logoGlobals = (LogoList)modelList.first();
		for (Object global : logoGlobals){
			String theGlobal = (String)global;
			globals.add(theGlobal);
		}
		// then remove the list of globals
		modelList = (LogoList) modelList.butFirst();
		modelList = (LogoList) modelList.first();
		// rest are breed tuples - first is breed name, second is a list of their vars
		for (Object o : modelList){
			LogoList theBreedTuple = (LogoList)o;
			String breedName = (String)theBreedTuple.first().toString();
			ArrayList<String>breedvars = new ArrayList<String>();
			// iterate over breed vars, second item in the inner list
			LogoList breedvarnames = (LogoList)theBreedTuple.butFirst().first();
			for(Object n : breedvarnames){				
				String breedvar = n.toString();
				breedvars.add(breedvar);
			}
			breeds.put(breedName, breedvars);
		}

	}
	
	public Rectangle rect(){
		return this.myRect;
	}
	
	public void draw(){
		parent.fill(aColor.getRed(), aColor.getGreen(), aColor.getBlue());
		parent.rect(rect().x, rect().y, rect().width, rect().height);
	}
	
	public boolean contains(int x, int y){
		return rect().contains(x, y);
	}
	
	public void newColor(){
		int r = (int) (Math.random() * 255);
		int g = (int) (Math.random() * 255);
		int b = (int) (Math.random() * 255);
		aColor = new Color(r, g, b);
	}
	
	public Point center(){
		return new Point(rect().x + rect().width / 2, rect().y + rect().height / 2);
	}

}
