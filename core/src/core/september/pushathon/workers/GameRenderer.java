/*******************************************************************************
 * Copyright 2013 Andreas Oehlke
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/


package core.september.pushathon.workers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;

import core.september.foundation.AbstractGameScreen;
import core.september.foundation.Assets;
import core.september.foundation.util.Constants;
import core.september.foundation.util.GamePreferences;


public class GameRenderer extends InputAdapter implements Disposable {

	private static final String TAG = GameRenderer.class.getName();

	private OrthographicCamera camera;
	private SpriteBatch batch;
	private GameController gameController;
	private Rectangle bounds,touchpoint,viewport;
	public Vector2 touchBounds;
	float scale = 1f;


	public GameRenderer (GameController gameController) {
		Gdx.input.setInputProcessor(this);
		this.gameController = gameController;
		init();
	}

	private void init () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
		//camera.setToOrtho(true);
		
	}

//	public void render () {
//		renderGame(batch);
//	}
	
    public void render()
    {
        
    	Gdx.gl.glClearColor(0x64 / 255.0f, 0x95 / 255.0f, 0xed / 255.0f, 0xff / 255.0f);
    	 Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		// Clears the screen
    	// update camera
		
		camera.position.set(Gdx.app.getGraphics().getWidth()/2, Gdx.app.getGraphics().getHeight()/2, 0);
        camera.update();

        // set viewport
//        Gdx.gl.glViewport((int) viewport.x, (int) viewport.y,
//                          (int) viewport.width, (int) viewport.height);

        // clear previous frame
       
        renderGame(batch);
        // DRAW EVERYTHING
    }

	private void renderGame (SpriteBatch batch) {
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		//gameController.level.render(batch);
		renderButton(batch);
		
		
		batch.end();
	}
	
	private boolean isTouched() {
		if(touchBounds != null) {
			//touchpoint = new Rectangle(touchBounds.x, touchBounds.y, 1, 1);
			Vector3 unproject = camera.unproject(new Vector3(touchBounds.x, touchBounds.y, 0));
			touchpoint = new Rectangle(unproject.x,unproject.y,1,1);
			return bounds.overlaps(touchpoint);
		}
		return false;
	}

	private void renderButton(SpriteBatch batch) {
		AtlasRegion  reg = null;
		reg = isTouched() ? Assets.instance.button.down : Assets.instance.button.up;
//		batch.draw(reg.getTexture(), position.x, position.y, origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y,
//			rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(),
//			viewDirection == VIEW_DIRECTION.LEFT, false);

		// Reset color to white
		bounds = gameController.level.button.getScaled(scale);
		
		batch.draw(reg, 
				bounds.x,
				bounds.y,
				bounds.width,
				bounds.height
				);
		
		batch.setColor(1, 1, 1, 1);
	}
	

	public void resize (int width, int height) {

//		camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / (float)height) * (float)width;
//		camera.
//		camera.update();
		
		float ASPECT_RATIO = Constants.VIEWPORT_WIDTH/Constants.VIEWPORT_HEIGHT;
		float aspectRatio = (float)width/(float)height;
        
        Vector2 crop = new Vector2(0f, 0f);
        if(aspectRatio > ASPECT_RATIO)
        {
            scale = (float)height/(float)Constants.VIEWPORT_HEIGHT ;
            crop.x = (width - Constants.VIEWPORT_WIDTH *scale)/2f;
        }
        else if(aspectRatio < ASPECT_RATIO)
        {
            scale = (float)width/(float)Constants.VIEWPORT_WIDTH;
            crop.y = (height - Constants.VIEWPORT_HEIGHT*scale)/2f;
        }
        else
        {
            scale = (float)width/(float)Constants.VIEWPORT_WIDTH;
        }

        float w = (float)Constants.VIEWPORT_WIDTH*scale;
        float h = (float)Constants.VIEWPORT_HEIGHT*scale;
        viewport = new Rectangle(crop.x, crop.y, w, h);
        camera.viewportHeight = h;
        camera.viewportWidth = w;
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
	
	@Override
	public boolean touchDown (int screenX, int screenY, int pointer, int button) {
		touchBounds = new Vector2(screenX, screenY);
		return true;
	}

	@Override
	public boolean touchUp (int screenX, int screenY, int pointer, int button) {
		touchBounds = null;
		return true;
	}

}
