package wurmod.mod;

import wurmod.api.Wurm;
import wurmod.command.CommandExecutor;

public abstract class ClientMod {

	private Wurm game;
	
	public ClientMod() {
		ClassLoader loader = this.getClass().getClassLoader();
		if(loader instanceof ModClassLoader) {
			((ModClassLoader)loader).initialize(this); 
		} else {
			throw new IllegalStateException("Mod was not loaded by ModClassLoader");
		}
	}

	public void onInitialize() { }
	
	public final void addConsoleCommand(String command, CommandExecutor executor) {
		this.game.addConsoleCommand(command, executor);
	}
	
	final void setup(Wurm game) {
		this.game = game;
	}
}
