package core.september.pushathon.workers;

import com.badlogic.gdx.Game;

public class HelpController extends GameController{

	public HelpController(Game game) {
		super(game);
	}

	@Override
	public void update(float delta) {
		resources.update(delta);
		
	}

}
