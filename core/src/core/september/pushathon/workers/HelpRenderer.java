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

import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;

import core.september.foundation.Assets;
import core.september.foundation.AudioManager;
import core.september.foundation.util.Constants;
import core.september.foundation.util.GamePreferences;
import core.september.pushathon.gameobjects.Counter;
import core.september.pushathon.gameobjects.HelpText;
import core.september.pushathon.gameobjects.Led;
import core.september.pushathon.gameobjects.PowerButton;
import core.september.pushathon.gameobjects.PushButton;
import core.september.pushathon.shaders.Shader;


public class HelpRenderer extends BatchRenderer implements Disposable {

	private static final String TAG = HelpRenderer.class.getName();
	
	protected OrthographicCamera upCamera;
	protected SpriteBatch upBatch;
	public Rectangle boundsNext;
	public int step;
	protected int lastStep;
	protected boolean touchLock = true;
	private TweenManager manager;
	
	private PowerButton powerButton;
	private Led powerLed;
	
	private PushButton pushButton;
	
	public Counter counterDH;
	public Counter counterUH;
	
	//private BitmapFont font;
	
	private HelpText help;
	
	
	private InputAdapter iAdapter;

	
	public HelpRenderer(GameController gameController,TweenManager manager,int step) {
		super(gameController);
		this.step = step;
		this.manager = manager;
	}
	
	protected void init () {
		super.init();
		prepareTweens();
		upBatch = new SpriteBatch();
		upCamera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
		//upCamera.setToOrtho(true);
//		font = Assets.instance.fonts.defaultNormal;
//		font.setColor(Color.ORANGE);
//		font.scale(0.1f);
		
		
	}
	
	private void prepareTweens() {
		//power Unit
		Rectangle dimension = gameController.resources.powerButton.getScaled(scale);
		powerButton = new PowerButton(dimension.x, dimension.y, dimension.width, dimension.height);
		dimension = gameController.resources.powerLed.getScaled(scale);
		powerLed = new Led(dimension.x, dimension.y, dimension.width, dimension.height);
		dimension = gameController.resources.button.getScaled(scale);
		pushButton = new PushButton(dimension.x, dimension.y, dimension.width, dimension.height);
		dimension = gameController.resources.counterD.getScaled(scale);
		counterDH = new Counter(dimension.x, dimension.y, dimension.width, dimension.height,10);
		dimension = gameController.resources.counterU.getScaled(scale);
		counterUH = new Counter(dimension.x, dimension.y, dimension.width, dimension.height,1);
		
		counterDH.update(81);
		counterUH.update(81);
		
		help = buildHelpText();
	}
	
	public void resize (int width, int height) {
		super.resize(width, height);
    	resize(width,height,upCamera);
	}
	
	 public void render() {
		 super.render();
		 
		 upCamera.position.set(Gdx.app.getGraphics().getWidth()/2, Gdx.app.getGraphics().getHeight()/2, 0);
		 upCamera.update();
		 upBatch.setProjectionMatrix(upCamera.combined);
		 upBatch.begin();
		 renderViewableObjects(upBatch);
		 renderUpControls(upBatch);
		 renderSound(upBatch);
		 upBatch.end();
	 }
	
	protected void renderViewableObjects (SpriteBatch batch) { 
		switch (step) {
		case 0:
			renderViewableObjects0(batch);
			break;

		case 1:
			renderViewableObjects1(batch);
			break;
		
		case 2:
			renderViewableObjects2(batch);
		break;
		
		}
	}
	
	private void renderViewableObjects0 (SpriteBatch batch) { 
		if(((HelpController) gameController).visible) {
			TextureRegion  reg = powerButton.on;
			Rectangle myBounds = powerButton.getScaled(scale);
			batch.draw(reg, 
					myBounds.x,
					myBounds.y,
					myBounds.width,
					myBounds.height
					);
			TextureRegion ledReg =  powerLed.on;
			myBounds = powerLed.getScaled(scale);
			batch.draw(ledReg, 
					myBounds.x,
					myBounds.y,
					myBounds.width,
					myBounds.height
					);
		}
		
		
		//font.drawMultiLine(batch, "Start up \nthe PushBox...", upCamera.viewportWidth*0.2f, upCamera.viewportHeight*0.9f);
		TextureRegion helpRegion = help.getHelp(step+1);
		Rectangle helpScaled = help.getScaled(scale);
		batch.draw(helpRegion, 
				helpScaled.x,
				helpScaled.y,
				helpScaled.width,
				helpScaled.height
				);
	}
	
	private void renderViewableObjects1 (SpriteBatch batch) { 
		
		if(((HelpController) gameController).visible) { 
			TextureRegion  reg = pushButton.up;
			Rectangle myBounds = pushButton.getScaled(scale);
			batch.draw(reg, 
					myBounds.x,
					myBounds.y,
					myBounds.width,
					myBounds.height
					);
		}
		
		TextureRegion helpRegion = help.getHelp(step+1);
		Rectangle helpScaled = help.getScaled(scale);
		batch.draw(helpRegion, 
				helpScaled.x,
				helpScaled.y,
				helpScaled.width,
				helpScaled.height
				);
		
	}
	
	private void renderViewableObjects2 (SpriteBatch batch) { 
		if(((HelpController) gameController).visible) {  
			TextureRegion  reg = counterDH.selected;
			Rectangle myBounds = counterDH.getScaled(scale);
			batch.draw(reg, 
					myBounds.x,
					myBounds.y,
					myBounds.width,
					myBounds.height
					);
			TextureRegion  reg2 = counterUH.selected;
			myBounds = counterUH.getScaled(scale);
			batch.draw(reg2, 
					myBounds.x,
					myBounds.y,
					myBounds.width,
					myBounds.height
					);
			
		}
		
		
		TextureRegion helpRegion = help.getHelp(step+1);
		Rectangle helpScaled = help.getScaled(scale);
		batch.draw(helpRegion, 
				helpScaled.x,
				helpScaled.y,
				helpScaled.width,
				helpScaled.height
				);
		
	}
	
//	private void manageTween(int step) {
//		Tween.from(powerButton, ElementAccessor.POSITION_XY, 1.0f)
//	    .target(0, 0)
//	    .start(manager);
//		
//		Tween.from(powerLed, ElementAccessor.POSITION_XY, 1.0f)
//	    .target(0, 0)
//	    .start(manager);
//	}
//	
	 
	protected void renderUpControls (SpriteBatch batch) { 
		 
		 
		 bounds = gameController.resources.prev.getScaled(scale);
		 boundsNext = gameController.resources.next.getScaled(scale);
		 renderUpControlsNavigation(batch);
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
			if (checkSound(touchBounds,upCamera)) {
				GamePreferences.instance.sound = !GamePreferences.instance.sound;
			}
			if(isNextTouched() && touchLock) {
				AudioManager.instance.play(Assets.instance.sounds.level_switch);
				if(step < Constants.MAX_HELP_STEP) {
					stepUp();
				}
				else {
					((HelpController)gameController).play();
				}
				
			}
			else if(isPrevTouched() && touchLock) {
				AudioManager.instance.play(Assets.instance.sounds.level_switch);
				stepDown();
			}
			touchLock = false;
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
				
				Vector3 unproject = upCamera.unproject(new Vector3(touchBounds.x, touchBounds.y, 0));
				touchpoint = new Rectangle(unproject.x,unproject.y,1,1);
				return boundsNext.overlaps(touchpoint);
			}
			return false;
		}
		
		
		protected boolean isPrevTouched() {
			if(touchBounds != null && touchLock && step > Constants.LOW_HELP_STEP) {
				
				//touchpoint = new Rectangle(touchBounds.x, touchBounds.y, 1, 1);
				Vector3 unproject = upCamera.unproject(new Vector3(touchBounds.x, touchBounds.y, 0));
				touchpoint = new Rectangle(unproject.x,unproject.y,1,1);
				return bounds.overlaps(touchpoint);
			}
			return false;
		}
		
		public int stepUp() {
			step = step < Constants.MAX_HELP_STEP ? ++step : step;
			help = buildHelpText();
			return step;
		}
		
		public int stepDown() {
			step = step > Constants.LOW_HELP_STEP ? --step : step;
			help = buildHelpText();
			return step;
		}
		
		private HelpText buildHelpText() {
			return new HelpText(Constants.VIEWPORT_WIDTH/4, 
					Constants.VIEWPORT_HEIGHT/2, 
					HelpText.getHelp(step+1).getRegionWidth(), 
					HelpText.getHelp(step+1).getRegionHeight());
		}
	}

