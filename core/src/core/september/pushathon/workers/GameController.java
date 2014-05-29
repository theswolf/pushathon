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

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Rectangle;

import core.september.foundation.util.Constants;
import core.september.pushathon.gameobjects.Level;


public class GameController extends InputAdapter {

	private static final String TAG = GameController.class.getName();

	private Game game;
	public Level level;

	// Rectangles for collision detection
	private Rectangle r2 = new Rectangle();

	private float timeLeftGameOverDelay;

	public GameController (Game game) {
		this.game = game;
		init();
	}

	private void init () {
		Gdx.input.setInputProcessor(this);
		initLevel();
	}

	private void initLevel () {
		level = new Level("no file");
	}

	public void update (float deltaTime) {
		level.update(deltaTime);
	}

	public boolean increaseScore() {
		level.score+=1;
		return false;
	}
	
	

	

}
