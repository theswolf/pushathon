package core.september.pushathon;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;

import core.september.foundation.Assets;
import core.september.pushathon.screens.GameScreen;

public class PushathonGame extends Game{

	@Override
	public void create() {
		// Set Libgdx log level
				Gdx.app.setLogLevel(Application.LOG_DEBUG);

				// Load assets
				Assets.instance.init(new AssetManager());

				// Start game at menu screen
				setScreen(new GameScreen(this));
		
	}

}
