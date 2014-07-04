package core.september.pushathon.workers;

import com.badlogic.gdx.Game;

import core.september.pushathon.screens.GameScreen;

public class HelpController extends GameController{
	
	private float lastUpdate = 0;
	private float lastUpdateStep = 0.200f;
	public boolean visible;

	public HelpController(Game game) {
		super(game);
	}

	@Override
	public void update(float delta) {
		lastUpdate = lastUpdate+delta;
		if(lastUpdate > lastUpdateStep) {
			visible = !visible;
			lastUpdate = 0;
		}
		resources.update(delta);
		
	}

	public void play() {
		game.setScreen(new GameScreen(game));// TODO Auto-generated method stub
	}
	
	

}
