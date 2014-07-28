package core.september.pushathon.workers;

import com.badlogic.gdx.Game;

import core.september.foundation.util.GamePreferences;
import core.september.pushathon.PushathonGame;
import core.september.pushathon.screens.GameScreen;
import core.september.pushathon.screens.HelpScreen;

public class MainController extends GameController{
	
	private float lastUpdate = 0;
	private float lastUpdateStep = 0.200f;
	public boolean visible;

	public MainController(Game game) {
		super(game);
	}


	public void play() {
		GamePreferences.instance.save();
		game.setScreen(new GameScreen(game));// TODO Auto-generated method stub
	}
	
	public void help() {
		GamePreferences.instance.save();
		game.setScreen(new HelpScreen(game));// TODO Auto-generated method stub
	}


	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		
	}


	public void score() {
		((PushathonGame)game).actionResolver.getLeaderboardGPGS();
	}
	
	

}
