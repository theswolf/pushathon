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


package core.september.pushathon.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import core.september.foundation.Assets;
import core.september.foundation.Utils;
import core.september.foundation.util.Constants;


public class GameResources {

	public static final String TAG = GameResources.class.getName();
	

	public enum BLOCK_TYPE {
		EMPTY(0, 0, 0), // black
		ROCK(0, 255, 0), // green
		PLAYER_SPAWNPOINT(255, 255, 255), // white
		ITEM_FEATHER(255, 0, 255), // purple
		ITEM_GOLD_COIN(255, 255, 0); // yellow

		private int color;

		private BLOCK_TYPE (int r, int g, int b) {
			color = r << 24 | g << 16 | b << 8 | 0xff;
		}

		public boolean sameColor (int color) {
			return this.color == color;
		}

		public int getColor () {
			return color;
		}
	}

	// player character
	public PushButton button;
	public MetalBox box;
	public Counter counterD;
	public Counter counterU;
	
	public Scorer scoreU;
	public Scorer scoreD;
	public Scorer scoreM;
	public Scorer scoreDM;
	
	public PowerButton powerButton;
	public Led powerLed;
	
	public HelpNavigation next;
	public HelpNavigation prev;
	
	public Sound soundUi;
	
	public int score = 0;
	public boolean started = false;

	public GameResources (String filename) {
		init(filename);
	}

	private void init (String filename) {
		// player character
		//box = new MetalBox(0,0,Assets.instance.background.background.getRegionWidth(),Assets.instance.background.background.getRegionHeight());
		button = new PushButton(
				(Constants.VIEWPORT_WIDTH - Assets.instance.button.up.getRegionWidth())/2,
				(Constants.VIEWPORT_HEIGHT - Assets.instance.button.up.getRegionHeight())/2,
				Assets.instance.button.up.getRegionWidth()
				,Assets.instance.button.up.getRegionHeight());

		counterU = new Counter(
				Constants.VIEWPORT_WIDTH - Assets.instance.countdown.off.getRegionWidth() -20,
				Constants.VIEWPORT_HEIGHT - Assets.instance.countdown.off.getRegionHeight() -20,
				Assets.instance.countdown.off.getRegionWidth()
				,Assets.instance.countdown.off.getRegionHeight()
				,1);
		Rectangle square = counterU.getScaled(1f);
		
		counterD = new Counter(
				square.x - square.width,
				square.y, 
				square.width, 
				square.height,
				10);
		Rectangle buttonSquare = button.getScaled(1f);
		scoreU = new Scorer(
				10 + buttonSquare.x + buttonSquare.width,
				square.y - square.height, 
				Assets.instance.countdown.off.getRegionWidth()*0.8f, 
				Assets.instance.countdown.off.getRegionHeight()*0.8f,
				1000);
		Rectangle squareCounter = scoreU.getScaled(1f);
		scoreD = new Scorer(
				squareCounter.x + squareCounter.width,
				squareCounter.y,
				squareCounter.width, 
				squareCounter.height,
				100);
		squareCounter = scoreD.getScaled(1f);
		scoreM = new Scorer(
				squareCounter.x + squareCounter.width,
				squareCounter.y,
				squareCounter.width, 
				squareCounter.height,
				10);
		squareCounter = scoreM.getScaled(1f);
		scoreDM = new Scorer(
				squareCounter.x + squareCounter.width,
				squareCounter.y,
				squareCounter.width, 
				squareCounter.height,
				1);
		
		soundUi = new Sound(5
				,Constants.VIEWPORT_HEIGHT - Assets.instance.knob.off.getRegionHeight()*0.5f
				, Assets.instance.knob.off.getRegionWidth()*0.5f, 
				Assets.instance.knob.off.getRegionHeight()*0.5f);
		
		
		
		powerButton = new PowerButton(buttonSquare.x - Assets.instance.powerButton.off.getRegionWidth()*1.8f, buttonSquare.y, Assets.instance.powerButton.off.getRegionWidth(), Assets.instance.powerButton.on.getRegionHeight());
		square = powerButton.getScaled(1f);
		powerLed = new Led(square.x - Assets.instance.led.off.getRegionWidth()*0.5f*0.5f +square.getWidth()/2 ,square.y + square.height*1.2f,Assets.instance.led.off.getRegionWidth()*0.5f, Assets.instance.led.off.getRegionHeight()*0.5f);
		
		prev  = new HelpNavigation(
				10,
				(Constants.VIEWPORT_HEIGHT - Assets.instance.assetUi.prev.getRegionHeight())*0.1f,
				Assets.instance.assetUi.prev.getRegionWidth()
				,Assets.instance.assetUi.prev.getRegionHeight());
		
		next  = new HelpNavigation(
				(Constants.VIEWPORT_WIDTH - Assets.instance.assetUi.next.getRegionWidth() - 10),
				(Constants.VIEWPORT_HEIGHT - Assets.instance.assetUi.next.getRegionHeight())*0.1f,
				Assets.instance.assetUi.next.getRegionWidth()
				,Assets.instance.assetUi.next.getRegionHeight());
		Gdx.app.debug(TAG, "level '" + filename + "' loaded");
	}


//	public void render (SpriteBatch batch) {
//		button.touched = touched;
//		box.render(batch);
//		button.render(batch);
//	}
	
	public void update(float deltaTime) {
		if(started) {
			counterD.update(deltaTime);
			counterU.update(deltaTime);
			scoreU.score = scoreD.score = scoreM.score = scoreDM.score = score;		
			scoreU.update(deltaTime);
			scoreD.update(deltaTime);
			scoreM.update(deltaTime);
			scoreDM.update(deltaTime);
		}
		
	}

}
