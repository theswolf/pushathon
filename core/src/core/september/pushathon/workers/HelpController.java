package core.september.pushathon.workers;

import com.badlogic.gdx.Game;

import core.september.pushathon.screens.GameScreen;

public class HelpController extends GameController{

	public HelpController(Game game) {
		super(game);
	}

	@Override
	public void update(float delta) {
		resources.update(delta);
		
	}

	public void play() {
		game.setScreen(new GameScreen(game));// TODO Auto-generated method stub
	}
	

}
