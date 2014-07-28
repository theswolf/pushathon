package core.september.pushathon;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;

import core.september.foundation.ActionResolver;
import core.september.foundation.Assets;
import core.september.foundation.AudioManager;
import core.september.foundation.util.GamePreferences;
import core.september.pushathon.screens.MainScreen;

public class PushathonGame extends Game{

	public ActionResolver actionResolver;
	
	public  PushathonGame(ActionResolver actionResolver) {
		super();
		this.actionResolver = actionResolver;
	}
	@Override
	public void create() {
		// Set Libgdx log level
				Gdx.app.setLogLevel(Application.LOG_DEBUG);

				// Load assets
				Assets.instance.init(new AssetManager());
				GamePreferences.instance.load();
				// Start game at menu screen
				AudioManager.instance.play(Assets.instance.assetMusic.soundtrack);
				GamePreferences.instance.save();
				setScreen(new MainScreen(this));
		
	}

}
