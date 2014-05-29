package core.september.pushathon.gameobjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import core.september.foundation.AbstractGameObject;
import core.september.foundation.Assets;
import core.september.foundation.util.Constants;

public class MetalBox extends AbstractGameObject{
	
	public TextureRegion back;

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
	
	public MetalBox(float x, float y, float width, float height) {
		super(x,y,width,height);
		init();
	}
	
	public void init () {
		back = Assets.instance.background.background;
	}
	
	public void update (float deltaTime) {}
	
	

}
