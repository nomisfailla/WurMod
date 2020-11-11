package wurmod.mod;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import wurmod.api.Wurm;

public class ModClassLoader extends URLClassLoader {

	private Wurm game;
	
	public ModClassLoader(Wurm game, File file, ClassLoader parent) throws MalformedURLException {
		super(new URL[] { file.toURI().toURL() }, parent);
		
		this.game = game;
	}
	
	public void initialize(ClientMod mod) {
		mod.setup(game);
	}
}
