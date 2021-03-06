package org.ahjorth.lsp;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import org.nlogo.api.Argument;
import org.nlogo.api.ClassManager;
import org.nlogo.api.CompilerException;
import org.nlogo.api.Context;
import org.nlogo.api.DefaultCommand;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.ExtensionManager;
import org.nlogo.api.ExtensionObject;
import org.nlogo.api.ImportErrorHandler;
import org.nlogo.api.LogoException;
import org.nlogo.api.LogoList;
import org.nlogo.api.PrimitiveManager;
import org.nlogo.api.Syntax;


public class LSProcessing implements ClassManager {
	
//	public static void main(String[] args){
//		myFrame = new ExampleFrame();
//	}
	
	public static ExampleFrame myFrame;

	@Override
	public List<String> additionalJars() {
		ArrayList<String> jars = new ArrayList<String>();
		jars.add("core.jar");
		return jars;
	}

	@Override
	public void clearAll() {
		// TODO Auto-generated method stub
	}

	@Override
	public StringBuilder exportWorld() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void importWorld(List<String[]> arg0, ExtensionManager em,
			ImportErrorHandler arg2) throws ExtensionException {
//		 Runnable doHelloWorld = new Runnable() {
//		     public void run() {
//		         myFrame = new ExampleFrame();
//		     }
//		 };
//
//		 SwingUtilities.invokeLater(doHelloWorld);
	}

	@Override
	public void load(PrimitiveManager pm) throws ExtensionException {
		pm.addPrimitive("show", new OpenGUI());
		pm.addPrimitive("kill", new Kill());
		pm.addPrimitive("call", new Call());
		pm.addPrimitive("draw", new Draw());
        pm.addPrimitive("load-sketch", new LoadSketch());
	}

	@Override
	public ExtensionObject readExtensionObject(ExtensionManager arg0,
			String arg1, String arg2) throws ExtensionException,
			CompilerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void runOnce(ExtensionManager arg0) throws ExtensionException {
		// TODO Auto-generated method stub

	}

	@Override
	public void unload(ExtensionManager arg0) throws ExtensionException {
	}

	public static class OpenGUI extends DefaultCommand {
		public Syntax getSyntax() {
			return Syntax.commandSyntax(
					// we take in nothing 
					new int[] { });
		}
		@Override
		public void perform(Argument[] arg0, Context arg1)
				throws ExtensionException, LogoException {
			SwingUtilities.invokeLater(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					myFrame = new ExampleFrame("/Users/hah661/Documents/Processing/Bubbles/Bubbles.pde");
				}

			});
			
		}
	}
	public static class Draw extends DefaultCommand {
		public Syntax getSyntax() {
			return Syntax.commandSyntax(
					// we take in nothing 
					new int[] { });
		}
		@Override
		public void perform(Argument[] arg0, Context arg1)
				throws ExtensionException, LogoException {
			myFrame.nlp().redraw();
		}
	}
	
	public static class Call extends DefaultCommand {
		public Syntax getSyntax() {
			return Syntax.commandSyntax(
					// we take in a list of 
					new int[] {Syntax.StringType(), Syntax.ListType() | Syntax.OptionalType()});
		}
		@Override
		public void perform(Argument[] arg0, Context arg1)
				throws ExtensionException, LogoException {
			String methodName = arg0[0].getString();
			// array for classes to find the method
			Class<?>[] args;
			// array of objects as actual args
			Object[] actuals;
			if (arg0.length>1){
				args = getClassArgs(arg0[1].getList());		
				actuals = getCleanArgs(arg0[1].getList());
			}
			else {
				args = null;
				actuals = null;
			}			
			// get NetLogoProcessing class
			Class<? extends NetLogoProcessing> nlpClass = myFrame.nlp().getClass();
			Method theMethod;
			// get the method & invoke it
			try {
				theMethod = nlpClass.getMethod(methodName, args);
				theMethod.invoke(myFrame.nlp(), actuals);
			} catch (SecurityException e) {
				throw new ExtensionException(e.getMessage());
			} catch (NoSuchMethodException e) {
				throw new ExtensionException(e.getMessage());
			} catch (IllegalArgumentException e) {
				throw new ExtensionException(e.getMessage());
			} catch (IllegalAccessException e) {
				throw new ExtensionException(e.getMessage());
			} catch (InvocationTargetException e) {
				throw new ExtensionException(e.getMessage());
			} 
		}
		
		public Class<?>[] getClassArgs(LogoList rawArgs) throws ExtensionException{
			Class<?>[] args = new Class[rawArgs.size()];
			for (int i = 0; i < rawArgs.size();i++){
				Object anArg = rawArgs.get(i);
				if(anArg instanceof String){
					args[i] = String.class;
				}
				else if (anArg instanceof LogoList){
					args[i] = ArrayList.class;
				}
				else if (anArg instanceof Double){
					args[i] = Double.class;
				}
				else{
					throw new ExtensionException("You can only pass strings, numbers, or lists to Processing");
				}
			}
			return args;
		}
		public Object[] getCleanArgs(LogoList rawArgs){
			Object[] cleanArgs = new Object[rawArgs.size()];
			for (int i = 0; i<rawArgs.size();i++){
				cleanArgs[i] = getArg(rawArgs.get(i));
			}
			return cleanArgs;
		}
		public ArrayList<Object> cleanList(LogoList inList){
			ArrayList<Object> outlist = new ArrayList<Object>();
			for (Object o : inList){
				outlist.add(getArg(o));
			}
			return outlist;
		}
		public Object getArg(Object o){
			if(o instanceof String || o instanceof Double){
				return o;
			}
			else {
				return cleanList((LogoList)o);
			} 
			
		}
	}

    public static class Kill extends DefaultCommand {
        public Syntax getSyntax() {
            return Syntax.commandSyntax(
                    // we take in nothing
                    new int[] { });
        }

        public void perform(Argument args[], Context context)
                throws ExtensionException, org.nlogo.api.LogoException {
            myFrame.kill();
            myFrame = null;
        }
    }
    public static class LoadSketch extends DefaultCommand {
        public Syntax getSyntax() {
            return Syntax.commandSyntax(
                    // we take in nothing
                    new int[] {Syntax.StringType()});
        }

        public void perform(Argument args[], Context context)
                throws ExtensionException, org.nlogo.api.LogoException {

        }
    }
}