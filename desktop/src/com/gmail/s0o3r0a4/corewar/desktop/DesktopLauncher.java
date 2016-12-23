package com.gmail.s0o3r0a4.corewar.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.gmail.s0o3r0a4.corewar.CoreWar;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.resizable = false;
		config.width = 480;
		config.height = 600;
		new LwjglApplication(new CoreWar(), config);
	}
}
