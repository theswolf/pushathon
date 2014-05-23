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

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Rectangle;

import core.september.foundation.util.CameraHelper;
import core.september.foundation.util.Constants;
import core.september.pushathon.gameobjects.Level;


public class GameController extends InputAdapter {

	private static final String TAG = GameController.class.getName();

	private Game game;
	public Level level;

	public CameraHelper cameraHelper;

	// Rectangles for collision detection
	private Rectangle r1 = new Rectangle();
	private Rectangle r2 = new Rectangle();

	private float timeLeftGameOverDelay;

	public GameController (Game game) {
		this.game = game;
		init();
	}

	private void init () {
		Gdx.input.setInputProcessor(this);
		cameraHelper = new CameraHelper();
		initLevel();
	}

	private void initLevel () {
		level = new Level("no file");
		cameraHelper.setTarget(level.button);
	}

	public void update (float deltaTime) {
		level.update(deltaTime);
	}
	
	@Override
	public boolean touchDown (int screenX, int screenY, int pointer, int button) {
		Rectangle touch = new Rectangle(
				-Constants.VIEWPORT_WIDTH / 2 +screenX,
				-Constants.VIEWPORT_HEIGHT /2 +screenY,
				1.0f,
				1.0f
				);
		level.touched = cameraHelper.getTarget().collide(touch);
		return true;
	}

	@Override
	public boolean touchUp (int screenX, int screenY, int pointer, int button) {
		level.touched = false;
		return true;
	}

	

}
