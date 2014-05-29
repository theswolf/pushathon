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
import com.badlogic.gdx.math.Rectangle;

import core.september.foundation.Assets;
import core.september.foundation.util.Constants;


public class Level {

	public static final String TAG = Level.class.getName();
	

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
	public int score = 0;

	public Level (String filename) {
		init(filename);
	}

	private void init (String filename) {
		// player character
		box = new MetalBox(0,0,Assets.instance.background.background.getRegionWidth(),Assets.instance.background.background.getRegionHeight());
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
				square.x - Assets.instance.countdown.off.getRegionWidth(),
				square.y, 
				square.width, 
				square.height,
				10);
		
		scoreU = new Scorer(
				10,
				10, 
				Assets.instance.countdown.off.getRegionWidth()*0.4f, 
				Assets.instance.countdown.off.getRegionHeight()*0.4f,
				1);
		Rectangle squareCounter = scoreU.getScaled(1f);
		scoreD = new Scorer(
				squareCounter.x + squareCounter.width,
				squareCounter.y,
				squareCounter.width, 
				squareCounter.height,
				10);
		squareCounter = scoreD.getScaled(1f);
		scoreM = new Scorer(
				squareCounter.x + squareCounter.width,
				squareCounter.y,
				squareCounter.width, 
				squareCounter.height,
				100);
		squareCounter = scoreM.getScaled(1f);
		scoreDM = new Scorer(
				squareCounter.x + squareCounter.width,
				squareCounter.y,
				squareCounter.width, 
				squareCounter.height,
				1000);
		
		Gdx.app.debug(TAG, "level '" + filename + "' loaded");
	}


//	public void render (SpriteBatch batch) {
//		button.touched = touched;
//		box.render(batch);
//		button.render(batch);
//	}
	
	public void update(float deltaTime) {
		counterD.update(deltaTime);
		counterU.update(deltaTime);
		scoreU.score = scoreD.score = scoreM.score = scoreDM.score = score;		
		scoreU.update(deltaTime);
		scoreD.update(deltaTime);
		scoreM.update(deltaTime);
		scoreDM.update(deltaTime);
	}

}
