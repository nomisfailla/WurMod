package test;

import wurmod.mod.ClientMod;

public class MyMod extends ClientMod {

	public void onInitialize() {
		System.out.println("Initializing mymod");
		
		addConsoleCommand("test_command", (input) -> {
			System.out.println("this is a test mod");
			return true;
		});
	}
}
