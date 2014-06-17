package core.september.pushathon.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

import core.september.foundation.AbstractGameScreen;
import core.september.foundation.util.Constants;
import core.september.foundation.util.GamePreferences;
import core.september.pushathon.workers.GameController;
import core.september.pushathon.workers.GameRenderer;
import core.september.pushathon.workers.HelpController;
import core.september.pushathon.workers.HelpRenderer;


public class HelpScreen extends AbstractGameScreen {

	private GameController gameController;
	private HelpRenderer helpRenderer;

	private boolean paused;


	public HelpScreen (Game game) {
		super(game);
	}
	
	

	@Override
	public void render (float deltaTime) {
		// Do not update game world when paused.
		if (!paused) {
			// Update game world by the time that has passed
			// since last rendered frame.
			gameController.update(deltaTime);
		}
		// Sets the clear screen color to: Cornflower Blue
	
		// Render game world to screen
		helpRenderer.render();
	}
	
	

	@Override
	public void resize (int width, int height) {
		super.resize(width, height);
		helpRenderer.resize(width, height);
	}

	@Override
	public void show () {
		GamePreferences.instance.load();
		gameController = new HelpController(game);
		helpRenderer = new HelpRenderer(gameController,Constants.LOW_HELP_STEP);
		Gdx.input.setCatchBackKey(true);
	}

	@Override
	public void hide () {
		helpRenderer.dispose();
		Gdx.input.setCatchBackKey(false);
	}

	@Override
	public void pause () {
		paused = true;
	}

	@Override
	public void resume () {
		super.resume();
		// Only called on Android!
		paused = false;
	}

}
