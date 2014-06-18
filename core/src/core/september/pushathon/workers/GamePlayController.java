package core.september.pushathon.workers;

import com.badlogic.gdx.Game;

import core.september.foundation.DirectedGame;
import core.september.foundation.util.Constants;

public class GamePlayController extends GameController{

	public static float timeLeft = Constants.TIME_LEFT;
	
	public GamePlayController(DirectedGame game) {
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

}
