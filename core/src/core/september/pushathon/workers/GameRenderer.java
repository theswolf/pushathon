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
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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


public class GameRenderer extends BatchRenderer implements Disposable {

	private static final String TAG = GameRenderer.class.getName();

	

	
	

		
		public GameRenderer(GameController gameController) {
			super(gameController);
		}

		@Override
		public boolean touchDown (int screenX, int screenY, int pointer, int button) {
			touchBounds = new Vector2(screenX, screenY);
			if(!gameController.resources.started && isTouched()) {
				gameController.resources.started = true;
			}
			else if(increasable && isTouched()) {
				increasable = ((GamePlayController) gameController).increaseScore();
			}
			return true;
		}

		@Override
		public boolean touchUp (int screenX, int screenY, int pointer, int button) {
			touchBounds = null;
			increasable = true;
			return true;
		}
		
		protected boolean isTouched() {
			if(touchBounds != null && ((GamePlayController)gameController).timeLeft > Constants.TIME_GONE ) {
				//touchpoint = new Rectangle(touchBounds.x, touchBounds.y, 1, 1);
				Vector3 unproject = camera.unproject(new Vector3(touchBounds.x, touchBounds.y, 0));
				touchpoint = new Rectangle(unproject.x,unproject.y,1,1);
				return bounds.overlaps(touchpoint);
			}
			return false;
		}
	}

	

