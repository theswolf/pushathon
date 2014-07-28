package core.september.pushathon.workers;

import com.badlogic.gdx.Game;

import core.september.foundation.util.Constants;
import core.september.pushathon.screens.MainScreen;

public class GamePlayController extends GameController{

	public static float timeLeft = Constants.TIME_LEFT;
	
	public GamePlayController(Game game) {
		super(game);
	}
	
	public void update (float deltaTime) {
		
		timeLeft-=deltaTime;
		resources.update(deltaTime);
	}
	
	public boolean increaseScore() {
		resources.score+=1;
		return false;
	}
	
	public int getScore() {
		return resources.score;
	}

}
