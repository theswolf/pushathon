package core.september.pushathon.gameobjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import core.september.foundation.AbstractGameObject;
import core.september.foundation.Assets;
import core.september.foundation.util.Constants;

public class PushButton extends AbstractGameObject{
	
	private TextureRegion up;
	private TextureRegion down;
	private TextureRegion reg = null;
	private Vector2[] bounds;
	
	//private static float scroll = 1.0;

	@Override
	public void render(SpriteBatch batch) {
		reg = null;
		reg = up;
//		batch.draw(reg.getTexture(), position.x, position.y, origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y,
//			rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(),
//			viewDirection == VIEW_DIRECTION.LEFT, false);

		// Reset color to white
		batch.draw(up, -Constants.VIEWPORT_WIDTH / 2, - Constants.VIEWPORT_HEIGHT / 4);
		batch.setColor(1, 1, 1, 1);
		
	}
	
	public PushButton() {
		super();
		init();
	}
	
	public void init () {
		dimension.set(1, 1);
		up = Assets.instance.button.up;
		down = Assets.instance.button.down;
		origin.set(dimension.x / 2, dimension.y / 2);
	}
	
	public void update (float deltaTime) {
		//= new Vector2(x, y)
	}
	

	
	

}
