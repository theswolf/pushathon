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

import java.util.logging.Level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;

import core.september.foundation.Assets;
import core.september.foundation.util.Constants;
import core.september.pushathon.gameobjects.HelpNavigation;
import core.september.pushathon.shaders.Shader;


public class HelpRenderer extends BatchRenderer implements Disposable {

	private static final String TAG = HelpRenderer.class.getName();
	
	protected OrthographicCamera upCamera;
	protected SpriteBatch upBatch;
	public Rectangle boundsNext;
	public int step;
	protected int lastStep;
	protected boolean touchLock = true;
	
	
	private InputAdapter iAdapter;

	
	public HelpRenderer(GameController gameController,int step) {
		super(gameController);
		this.step = step;
	}
	
	protected void init () {
		super.init();
		upBatch = new SpriteBatch();
		upCamera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
		upCamera.setToOrtho(true);
	}
	
	public void resize (int width, int height) {
		super.resize(width, height);
    	resize(width,height,upCamera);
	}
	
	 public void render() {
		 super.render();
		 
		 upCamera.position.set(Gdx.app.getGraphics().getWidth()/2, Gdx.app.getGraphics().getHeight()/2, 0);
		 upCamera.update();
		 renderViewableObjects(upBatch);
		 renderUpControls(upBatch);
		 
		 
	 }
	
	protected void renderViewableObjects (SpriteBatch batch) { 
		
	}
	 
	protected void renderUpControls (SpriteBatch batch) { 
		 batch.setProjectionMatrix(upCamera.combined);
		 batch.begin();
		 bounds = gameController.resources.prev.getScaled(scale);
		 boundsNext = gameController.resources.next.getScaled(scale);
		 renderUpControlsNavigation(batch);
		 batch.end();
	 }
	 
	 protected void renderUpControlsNavigation (SpriteBatch batch) { 
		if(step > Constants.LOW_HELP_STEP) {
			TextureRegion prev = gameController.resources.prev.prev;
			Rectangle prevSquare = gameController.resources.prev.getScaled(scale);
			batch.draw(prev,prevSquare.x,prevSquare.y,prevSquare.getWidth(),prevSquare.getHeight() );
		}
		
		TextureRegion next = step == Constants.MAX_HELP_STEP ?
				gameController.resources.next.play : gameController.resources.next.next;
		Rectangle nextSquare = gameController.resources.next.getScaled(scale);
		batch.draw(next,nextSquare.x,nextSquare.y,nextSquare.getWidth(),nextSquare.getHeight() );
		
		
	 }
	 
	protected void renderGame (SpriteBatch batch) {
		batch.setProjectionMatrix(camera.combined);
		batch.setShader(Shader.grayscaleShader);
		batch.begin();
		//gameController.level.render(batch);
		//renderBack(batch);
		bounds = gameController.resources.started? gameController.resources.button.getScaled(scale) :
			gameController.resources.powerButton.getScaled(scale);
		renderPowerUnit(batch);
		renderButton(batch);
		renderCounter(batch);
		renderScorer(batch);
		//renderText(batch);
		
		batch.end();
	}
	
		@Override
		public boolean touchDown (int screenX, int screenY, int pointer, int button) {
			touchBounds = new Vector2(screenX, screenY);
			if(isNextTouched()) {
				stepUp();
				
			}
			else if(isPrevTouched()) {
				stepDown();
			}
			return true;
		}

		@Override
		public boolean touchUp (int screenX, int screenY, int pointer, int button) {
			touchBounds = null;
			touchLock = true;
			return true;
		}
		
		
		protected boolean isTouched() {
			return false;
		}
		
		protected boolean isNextTouched() {
			if(touchBounds != null && touchLock) {
				//touchpoint = new Rectangle(touchBounds.x, touchBounds.y, 1, 1);
				touchLock = false;
				Vector3 unproject = upCamera.unproject(new Vector3(touchBounds.x, touchBounds.y, 0));
				touchpoint = new Rectangle(unproject.x,unproject.y,1,1);
				return boundsNext.overlaps(touchpoint);
			}
			return false;
		}
		
		protected boolean isPrevTouched() {
			if(touchBounds != null && touchLock && step > Constants.LOW_HELP_STEP) {
				touchLock = false;
				//touchpoint = new Rectangle(touchBounds.x, touchBounds.y, 1, 1);
				Vector3 unproject = upCamera.unproject(new Vector3(touchBounds.x, touchBounds.y, 0));
				touchpoint = new Rectangle(unproject.x,unproject.y,1,1);
				return bounds.overlaps(touchpoint);
			}
			return false;
		}
		
		public int stepUp() {
			return step < Constants.MAX_HELP_STEP ? ++step : step;
		}
		
		public int stepDown() {
			return step > Constants.LOW_HELP_STEP ? --step : step;
		}
	}

