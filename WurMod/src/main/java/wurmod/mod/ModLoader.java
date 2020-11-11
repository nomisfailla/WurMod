package wurmod.mod;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import wurmod.api.Wurm;

public class ModLoader {

	private static final File MOD_DIR = new File("mods");
	
	private Wurm game;
	
	public ModLoader(Wurm game) {
		this.game = game;
	}
	
	public ClientMod[] loadMods() throws ModException {
		List<ClientMod> mods = new ArrayList<ClientMod>();
		
		if(!MOD_DIR.exists() || !MOD_DIR.isDirectory()) {
			MOD_DIR.mkdir();
		}
		
		File[] files = MOD_DIR.listFiles();
		for(File file : files) {
			if(file.getName().endsWith(".jar")) {
				ClientMod mod = loadMod(file);
				mod.onInitialize();
				
				mods.add(mod);
			}
		}
		
		return mods.toArray(new ClientMod[mods.size()]);
	}
	
	private ClientMod loadMod(File file) throws ModException {
		if(!file.exists()) {
			throw new ModException(new FileNotFoundException(file.getPath() + " does not exist"));
		}
		
		// TODO: Get mod.yaml and parse it, to obtain the main class.
		//       Could also iterate all classes and find one which inherits from ClientMod?
		
		try {
			ClassLoader loader = new ModClassLoader(game, file, getClass().getClassLoader());
			Class<?> clazz = Class.forName("test.MyMod", true, loader);
			Class<? extends ClientMod> mod = clazz.asSubclass(ClientMod.class);
			return mod.getDeclaredConstructor().newInstance();
		} catch(Exception e) {
			throw new ModException(e);
		}
	}
}
