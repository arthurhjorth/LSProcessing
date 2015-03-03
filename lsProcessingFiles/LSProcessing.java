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
import org.nlogo.app.App;
import org.nlogo.nvm.Workspace.OutputDestination;


public class LSProcessing implements ClassManager {
	
	public static void main(String[] args){
		myFrame = new ExampleFrame();
	}
	
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
		 Runnable doHelloWorld = new Runnable() {
		     public void run() {
		         myFrame = new ExampleFrame();
		     }
		 };

		 SwingUtilities.invokeLater(doHelloWorld);
	}

	@Override
	public void load(PrimitiveManager pm) throws ExtensionException {
		pm.addPrimitive("show", new OpenGUI());
		pm.addPrimitive("kill", new Kill());
		pm.addPrimitive("call", new Call());
		pm.addPrimitive("test", new Test());
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
					myFrame = new ExampleFrame();
				}

			});
			
		}
	}
	
	public static class Test extends DefaultCommand {
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
					myFrame.nlp().redraw();
				}

			});
			
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
			LogoList rawArgs = arg0[1].getList();
			//
			
			Class<? extends NetLogoProcessing> nlpClass = myFrame.nlp().getClass();
			
			Class<?>[] args;
			if(rawArgs.size()>0){args = getClassArgs(rawArgs);}
			else {args = null;}
			
//			
//			for (Class<?> c : args){
//			try {
//				App.app().workspace().outputObject(c.toString(), null, true, true, OutputDestination.NORMAL);
//			} catch (LogoException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//		}

			
			Method theMethod;
			
			try {
				theMethod = nlpClass.getMethod(methodName, args);
				// this here doesn't work. Look up what varargs are,
				// and make sure that's what I'm making instead of args,
				// because that's what is not working, I think.
			} catch (SecurityException e) {
				throw new ExtensionException("1 "+e.getMessage());
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				throw new ExtensionException("2 " + e.getMessage());
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				throw new ExtensionException("3" + e.getMessage());
			} 

			try {
				try {
					App.app().workspace().outputObject(args.toString(), null, true, true, OutputDestination.NORMAL);
				} catch (LogoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
				theMethod.invoke(myFrame.nlp(), args);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				throw new ExtensionException(e.getMessage());
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				throw new ExtensionException(e.getMessage());
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				throw new ExtensionException(e.getMessage());
			}

			// Always call redraw after doing business logic
			// - it calls the PApplet's draw() method
			myFrame.nlp().redraw();
		}
		
		public Class<?>[] getClassArgs(LogoList rawArgs){
			Class<?>[] args = new Class[rawArgs.size()];
			for (int i = 0; i < rawArgs.size();i++){
				Object anArg = rawArgs.get(i);
				if(anArg instanceof String){
					args[i] = String.class;
				}
				else if (anArg instanceof LogoList){
					args[i] = List.class;
				}
				else if (anArg instanceof Double){
					args[i] = Double.class;
				}
			}
			return args;
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

}