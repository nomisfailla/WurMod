package wurmod.mod;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.FileSystemNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

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
		
		ModDescriptionFile desc = null;
		
		try {
			JarFile jar = new JarFile(file);
			JarEntry entry = jar.getJarEntry("mod.yml");
			
			if(entry == null) {
				jar.close();
				throw new ModException(new FileSystemNotFoundException("Mod jar does not contain mod.yml"));
			}
			
			InputStream is = jar.getInputStream(entry);
			desc = new ModDescriptionFile(is);
			
			is.close();
			jar.close();
		} catch(Exception e) {
			throw new ModException(e);
		}
		
		try {
			ClassLoader loader = new ModClassLoader(game, file, getClass().getClassLoader());
			Class<?> clazz = Class.forName(desc.getMain(), true, loader);
			Class<? extends ClientMod> mod = clazz.asSubclass(ClientMod.class);
			return mod.getDeclaredConstructor().newInstance();
		} catch(Exception e) {
			throw new ModException(e);
		}
	}
}
