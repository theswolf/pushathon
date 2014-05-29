package core.september.pushathon.gameobjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import core.september.foundation.AbstractGameObject;
import core.september.foundation.Assets;
import core.september.foundation.util.Constants;

public class Counter extends AbstractGameObject{
	
	private TextureRegion off;
	private TextureRegion zero;
	private TextureRegion uno;
	private TextureRegion due;
	private TextureRegion tre;
	private TextureRegion quattro;
	private TextureRegion cinque;
	private TextureRegion sei;
	private TextureRegion sette;
	private TextureRegion otto;
	private TextureRegion nove;
	public TextureRegion selected;
	private int posDivider;

	private float elapsedTime = Constants.TIME_LEFT;
//	@Override
//	public void render(SpriteBatch batch) {
//		TextureRegion reg = null;
//		reg = back;
////		batch.draw(reg.getTexture(), position.x, position.y, origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y,
////			rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(),
////			viewDirection == VIEW_DIRECTION.LEFT, false);
//
//		// Reset color to white
//		batch.draw(back, - Constants.VIEWPORT_WIDTH / 2, - Constants.VIEWPORT_HEIGHT / 2);
//		batch.setColor(1, 1, 1, 1);
//		
//	}
	
	public Counter(float x, float y, float width, float height, int posDivider) {
		super(x,y,width,height);
		this.posDivider = posDivider;
		init();
	}
	
	public void init () {
		off = Assets.instance.countdown.off;
		zero = Assets.instance.countdown.zero;  
		uno = Assets.instance.countdown.uno;  
		due = Assets.instance.countdown.due;
		tre = Assets.instance.countdown.tre;    
		quattro = Assets.instance.countdown.quattro;
		cinque = Assets.instance.countdown.cinque;
		sei = Assets.instance.countdown.sei;    
		sette = Assets.instance.countdown.sette;  
		otto = Assets.instance.countdown.otto;   
		nove = Assets.instance.countdown.nove; 
		selected = off;
	}
	
	
	private void chooseSelected(int input) {
		switch (input) {
		case 0:
			selected = zero;
			break;
		case 1:
			selected = uno;
			break;
		case 2:
			selected = due;
			break;
		case 3:
			selected = tre;
			break;
		case 4:
			selected = quattro;
			break;
		case 5:
			selected = cinque;
			break;
		case 6:
			selected = sei;
			break;
		case 7:
			selected = sette;
			break;
		case 8:
			selected = otto;
			break;
		case 9:
			selected = nove;
			break;

		}
		
	}
	
	public void update (float deltaTime) {
		elapsedTime-= deltaTime;
		chooseSelected((int)elapsedTime / posDivider % 10);
	}
}
