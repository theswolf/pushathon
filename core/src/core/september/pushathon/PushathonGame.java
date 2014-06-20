package core.september.pushathon;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;

import core.september.foundation.Assets;
import core.september.foundation.util.GamePreferences;
import core.september.pushathon.screens.HelpScreen;

public class PushathonGame extends Game{

	@Override
	public void create() {
		// Set Libgdx log level
				Gdx.app.setLogLevel(Application.LOG_DEBUG);

				// Load assets
				Assets.instance.init(new AssetManager());
				GamePreferences.instance.load();
				// Start game at menu screen
				setScreen(new HelpScreen(this));
		
	}

}
