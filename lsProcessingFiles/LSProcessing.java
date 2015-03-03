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
import org.nlogo.api.PrimitiveManager;
import org.nlogo.api.Syntax;


public class LSProcessing implements ClassManager {
	
	public static ExampleFrame myFrame;

	@Override
	public List<String> additionalJars() {
		ArrayList<String> jars = new ArrayList<String>();
		jars.add("core.jar");
		jars.add("ControlP5.jar");
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
		// TODO Auto-generated method stub

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