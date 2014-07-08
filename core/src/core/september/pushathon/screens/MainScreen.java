package core.september.pushathon.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import core.september.foundation.AbstractGameScreen;
import core.september.foundation.util.Constants;
import core.september.foundation.util.GamePreferences;
import core.september.pushathon.workers.BatchRenderer;
import core.september.pushathon.workers.GameController;
import core.september.pushathon.workers.MainController;
import core.september.pushathon.workers.MainRenderer;


public class MainScreen extends AbstractGameScreen {

	private GameController gameController;
	private BatchRenderer mainRenderer;

	private boolean paused;


	public MainScreen (Game game) {
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
		mainRenderer.render();
	}
	
	

	@Override
	public void resize (int width, int height) {
		super.resize(width, height);
		mainRenderer.resize(width, height);
	}

	
	
	@Override
	public void show () {
		GamePreferences.instance.load();
		gameController = new MainController(game);
		gameController.setCurrentScreen(this);
		mainRenderer = new MainRenderer(gameController);
		Gdx.input.setCatchBackKey(true);
	}

	@Override
	public void hide () {
		mainRenderer.dispose();
		Gdx.input.setCatchBackKey(false);
	}

	@Override
	public void pause () {
		saveSettings();
		paused = true;
	}

	@Override
	public void resume () {
		super.resume();
		// Only called on Android!
		paused = false;
	}


	

}
