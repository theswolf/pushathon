package core.september.pushathon.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

import core.september.foundation.AbstractGameScreen;
import core.september.foundation.DirectedGame;
import core.september.foundation.util.Constants;
import core.september.foundation.util.GamePreferences;
import core.september.pushathon.workers.GamePlayController;
import core.september.pushathon.workers.GameRenderer;


public class GameScreen extends AbstractGameScreen {

	private GamePlayController gameController;
	private GameRenderer gameRenderer;

	private boolean paused;


	public GameScreen (DirectedGame game) {
		super(game);
	}

	@Override
	public void render (float deltaTime) {
		// Do not update game world when paused.
		if (!paused && gameController.timeLeft > Constants.TIME_GONE) {
			// Update game world by the time that has passed
			// since last rendered frame.
			gameController.update(deltaTime);
		}
		// Sets the clear screen color to: Cornflower Blue
	
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
		gameController = new GamePlayController(game);
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

	@Override
	public InputProcessor getInputProcessor() {
		// TODO Auto-generated method stub
		return gameRenderer;
	}

	
}
