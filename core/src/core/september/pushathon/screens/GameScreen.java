package core.september.pushathon.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

import core.september.foundation.AbstractGameScreen;
import core.september.foundation.util.Constants;
import core.september.foundation.util.GamePreferences;
import core.september.pushathon.PushathonGame;
import core.september.pushathon.workers.GamePlayController;
import core.september.pushathon.workers.GameRenderer;


public class GameScreen extends AbstractGameScreen {

	private GamePlayController gameController;
	private GameRenderer gameRenderer;

	private boolean paused;


	public GameScreen (Game game) {
		super(game);
	}

	@Override
	public void render (float deltaTime) {
		// Do not update game world when paused.
		if (!paused && gameController.timeLeft > Constants.TIME_GONE && gameController.resources.started) {
			// Update game world by the time that has passed
			// since last rendered frame.
			gameController.update(deltaTime);
		}
		else if (!paused && gameController.timeLeft <= Constants.TIME_GONE && gameController.resources.started) {
			gameController.timeLeft-=deltaTime;
			if(gameController.timeLeft <= 0) {
				GamePreferences.instance.save();
				((PushathonGame)game).actionResolver.submitScoreGPGS(gameController.getScore());
				game.setScreen(new MainScreen(game));
			}
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
		gameController.timeLeft = Constants.TIME_LEFT;
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
