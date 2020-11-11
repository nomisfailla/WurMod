package wurmod.api;

import wurmod.command.CommandExecutor;
import wurmod.mod.ClientMod;

public interface Wurm {

	public ClientMod[] getMods();
	
	public void addConsoleCommand(String command, CommandExecutor executor);
}
