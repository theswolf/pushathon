package core.september.pushathon.screens;

import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

import core.september.foundation.AbstractGameScreen;
import core.september.foundation.util.Constants;
import core.september.foundation.util.GamePreferences;
import core.september.pushathon.workers.GameController;
import core.september.pushathon.workers.HelpController;
import core.september.pushathon.workers.HelpRenderer;


public class HelpScreen extends AbstractGameScreen {

	private GameController gameController;
	private HelpRenderer helpRenderer;
	private TweenManager manager;

	private boolean paused;


	public HelpScreen (Game game) {
		super(game);
		manager = new TweenManager();
	}
	
	

	@Override
	public void render (float deltaTime) {
		// Do not update game world when paused.
		if (!paused) {
			// Update game world by the time that has passed
			// since last rendered frame.
			gameController.update(deltaTime);
			manager.update(deltaTime);
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
		gameController.setCurrentScreen(this);
		helpRenderer = new HelpRenderer(gameController,manager,Constants.LOW_HELP_STEP);
		Gdx.input.setCatchBackKey(true);
	}

	@Override
	public void hide () {
		helpRenderer.dispose();
		Gdx.input.setCatchBackKey(false);
	}

	@Override
	public void pause () {
		saveSettings();
		paused = true;
		manager.pause();
	}

	@Override
	public void resume () {
		super.resume();
		manager.resume();
		// Only called on Android!
		paused = false;
	}


	

}
