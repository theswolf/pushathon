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
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;

import core.september.foundation.Assets;
import core.september.foundation.util.Constants;


public class GameRenderer extends InputAdapter implements Disposable {

	private static final String TAG = GameRenderer.class.getName();

	private OrthographicCamera camera;
	private SpriteBatch batch;
	private ShapeRenderer shape;
	private GameController gameController;
	private Rectangle bounds,touchpoint,viewport;
	public Vector2 touchBounds;
	float scale = 1f;
	boolean increasable = true;


	public GameRenderer (GameController gameController) {
		Gdx.input.setInputProcessor(this);
		this.gameController = gameController;
		init();
	}

	private void init () {
		batch = new SpriteBatch();
		shape = new ShapeRenderer();
		camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
		//camera.setToOrtho(true);
		
	}

//	public void render () {
//		renderGame(batch);
//	}
	
    public void render()
    {
        
    	//Gdx.gl.glClearColor(0x64 / 255.0f, 0x95 / 255.0f, 0xed / 255.0f, 0xff / 255.0f);
    	Gdx.gl.glClearColor(Color.LIGHT_GRAY.r,Color.LIGHT_GRAY.g,Color.LIGHT_GRAY.b,Color.LIGHT_GRAY.a);
    	 Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		// Clears the screen
    	// update camera
		
		camera.position.set(Gdx.app.getGraphics().getWidth()/2, Gdx.app.getGraphics().getHeight()/2, 0);
        camera.update();

        // set viewport
        Gdx.gl.glViewport((int) viewport.x, (int) viewport.y,
                          (int) viewport.width, (int) viewport.height);

        // clear previous frame
       
        renderGame(batch);
        renderMask(shape);
        // DRAW EVERYTHING
    }

	private void renderGame (SpriteBatch batch) {
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		//gameController.level.render(batch);
		//renderBack(batch);
		renderButton(batch);
		renderCounter(batch);
		renderScorer(batch);
		
		
		batch.end();
	}
	
	private void renderMask (ShapeRenderer shape) {
		shape.setProjectionMatrix(camera.combined);
		shape.begin(ShapeType.Line);
		shape.setColor(Color.DARK_GRAY);
		Rectangle boundsD = gameController.level.counterD.getScaled(scale);
		shape.rect(boundsD.x,boundsD.y,boundsD.width*2,boundsD.height);
		Gdx.gl.glLineWidth(5);
		shape.end();
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
		TextureRegion  reg = null;
		reg = isTouched() ? gameController.level.button.down :  gameController.level.button.up;
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
	
	private void renderBack(SpriteBatch batch) {

		Rectangle bounds = gameController.level.box.getScaled(scale);
		
		batch.draw(gameController.level.box.back, 
				bounds.x,
				bounds.y,
				bounds.width,
				bounds.height
				);
		
		batch.setColor(1, 1, 1, 1);
	}
	
	private void renderCounter(SpriteBatch batch) {

		Rectangle boundsU = gameController.level.counterU.getScaled(scale);
		Rectangle boundsD = gameController.level.counterD.getScaled(scale);
		
		batch.draw(gameController.level.counterU.selected,
				boundsU.x,
				boundsU.y,
				boundsU.width,
				boundsU.height
				);
		
		batch.draw(gameController.level.counterD.selected,
				boundsD.x,
				boundsD.y,
				boundsD.width,
				boundsD.height
				);
		
		batch.setColor(1, 1, 1, 1);
	}
	
	private void renderScorer(SpriteBatch batch) {

		Rectangle boundsU = gameController.level.scoreU.getScaled(scale);
		Rectangle boundsD = gameController.level.scoreD.getScaled(scale);
		Rectangle boundsM = gameController.level.scoreM.getScaled(scale);
		Rectangle boundsDM = gameController.level.scoreDM.getScaled(scale);
		
		batch.draw(gameController.level.scoreU.selected,
				boundsU.x,
				boundsU.y,
				boundsU.width,
				boundsU.height
				);
		
		batch.draw(gameController.level.scoreD.selected,
				boundsD.x,
				boundsD.y,
				boundsD.width,
				boundsD.height
				);
		
		batch.draw(gameController.level.scoreM.selected,
				boundsM.x,
				boundsM.y,
				boundsM.width,
				boundsM.height
				);
		batch.draw(gameController.level.scoreDM.selected,
				boundsDM.x,
				boundsDM.y,
				boundsDM.width,
				boundsDM.height
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
		shape.dispose();
	}
	
	@Override
	public boolean touchDown (int screenX, int screenY, int pointer, int button) {
		touchBounds = new Vector2(screenX, screenY);
		if(increasable) {
			increasable = gameController.increaseScore();
		}
		return true;
	}

	@Override
	public boolean touchUp (int screenX, int screenY, int pointer, int button) {
		touchBounds = null;
		increasable = true;
		return true;
	}

}
