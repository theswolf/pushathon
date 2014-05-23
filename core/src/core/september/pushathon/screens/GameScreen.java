package core.september.pushathon.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

import core.september.foundation.AbstractGameScreen;
import core.september.foundation.util.Constants;
import core.september.foundation.util.GamePreferences;
import core.september.pushathon.workers.GameController;
import core.september.pushathon.workers.GameRenderer;


public class GameScreen extends AbstractGameScreen {

	private GameController gameController;
	private GameRenderer gameRenderer;

	private boolean paused;


	public GameScreen (Game game) {
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
		Gdx.gl.glClearColor(0x64 / 255.0f, 0x95 / 255.0f, 0xed / 255.0f, 0xff / 255.0f);
		// Clears the screen
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		// Render game world to screen
		gameRenderer.render();
	}

	@Override
	public void resize (int width, int height) {
		super.resize(width, height);
		gameRenderer.resize(width, height);
	}

	@Override
	public void show () {
		GamePreferences.instance.load();
		gameController = new GameController(game);
		gameRenderer = new GameRenderer(gameController);
		Gdx.input.setCatchBackKey(true);
	}

	@Override
	public void hide () {
		gameRenderer.dispose();
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
