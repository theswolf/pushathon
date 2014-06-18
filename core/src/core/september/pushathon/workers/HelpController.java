package core.september.pushathon.workers;

import core.september.foundation.DirectedGame;
import core.september.foundation.transitions.ScreenTransition;
import core.september.foundation.transitions.ScreenTransitionFade;

public class HelpController extends GameController{

	public HelpController(DirectedGame game) {
		super(game);
	}

	@Override
	public void update(float delta) {
		resources.update(delta);
		
	}
	
	public void prevNextTouched() {
		ScreenTransition transition = ScreenTransitionFade.init(0.75f);
		game.setScreen(getCurrentScreen(), transition);
	}

}
