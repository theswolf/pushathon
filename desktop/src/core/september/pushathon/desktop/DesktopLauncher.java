package core.september.pushathon.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import core.september.pushathon.PushathonGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "PushBox";
		config.width = 1920;
		config.height = 1080;
		new LwjglApplication(new PushathonGame(), config);
	}
}
