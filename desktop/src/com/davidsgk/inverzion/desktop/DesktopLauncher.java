package com.davidsgk.inverzion.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.davidsgk.inverzion.Inverzion;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Inverzion.WIDTH;
		config.height = Inverzion.HEIGHT;
		config.resizable = false;
		new LwjglApplication(new Inverzion(), config);
	}
}
