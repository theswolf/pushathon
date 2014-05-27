package core.september.pushathon.gameobjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import core.september.foundation.AbstractGameObject;
import core.september.foundation.AbstractGameScreen;
import core.september.foundation.Assets;
import core.september.foundation.util.Constants;

public class PushButton extends AbstractGameObject{
	
	private TextureRegion up;
	private TextureRegion down;
	private TextureRegion reg = null;
	public boolean touched = false;
	
	//private static float scroll = 1.0;

//	@Override
//	public void render(SpriteBatch batch) {
////		reg = null;
////		reg = touched ? down : up;
//////		batch.draw(reg.getTexture(), position.x, position.y, origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y,
//////			rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(),
//////			viewDirection == VIEW_DIRECTION.LEFT, false);
////
////		// Reset color to white
////		bounds = new Rectangle(
////				-Constants.VIEWPORT_WIDTH / 2, 
////				- Constants.VIEWPORT_HEIGHT / 4, 
////				reg.getRegionWidth()*AbstractGameScreen.scaleX,
////				reg.getRegionHeight()*AbstractGameScreen.scaleY
////				);
////		batch.draw(reg, -Constants.VIEWPORT_WIDTH / 2, - Constants.VIEWPORT_HEIGHT / 4);
////		
////		batch.setColor(1, 1, 1, 1);
//		
//	}
	
	public PushButton(float x, float y, float width, float height) {
		super( x,  y,  width,  height);
		init();
	}
	
	public void init () {
		up = Assets.instance.button.up;
		down = Assets.instance.button.down;
	}
	
	
	
	

	

	
	

}
