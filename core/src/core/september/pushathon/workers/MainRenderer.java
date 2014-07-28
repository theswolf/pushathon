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
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import core.september.foundation.Assets;
import core.september.foundation.util.Constants;
import core.september.pushathon.shaders.Shader;


public class MainRenderer extends BatchRenderer implements Disposable {

	private static final String TAG = MainRenderer.class.getName();
	private Skin buttonSkin; //** images are used as skins of the button **//
	private TextButton buttonNewGame,buttonHelp,buttonScore; //** the button - the only actor in program **//
	private TextButtonStyle style;
	private Stage stage;


	public MainRenderer(GameController gameController) {
		super(gameController);
	}

	@Override
	protected boolean isTouched() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void init() {
		super.init();
		buttonSkin = new Skin();
		buttonSkin.add("buttonTextUp", Assets.instance.assetSkin.buttonTextUp, NinePatch.class);
		buttonSkin.add("buttonTextDown", Assets.instance.assetSkin.buttonTextDown, NinePatch.class);
		stage = new Stage();//** window is stage **//
		stage.clear();
		Gdx.input.setInputProcessor(stage); //** stage is responsive **//

		style = new TextButtonStyle(); //** Button properties **//
		style.up = buttonSkin.getDrawable("buttonTextUp");
		style.down = buttonSkin.getDrawable("buttonTextDown");
		BitmapFont font = Assets.instance.fonts.defaultNormal;
		font.setScale(0.5f);
		style.font = font;


		buttonNewGame = new TextButton("new game", style); //** Button text and style **//
		
		buttonNewGame.setHeight(stage.getViewport().getViewportHeight()*0.10f); //** Button Height **//
		buttonNewGame.setWidth(stage.getViewport().getViewportWidth()*0.30f); //** Button Width **//
		buttonNewGame.setPosition(stage.getViewport().getViewportWidth()/2-buttonNewGame.getWidth()/2, 
				stage.getViewport().getViewportHeight()- buttonNewGame.getHeight() - stage.getViewport().getViewportHeight()*0.10f); //** Button location **//
		buttonNewGame.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				//Gdx.app.log("my app", "Pressed"); //** Usually used to start Game, etc. **//
				((MainController)gameController).play();
				return true;
			}

		});
		
		buttonHelp = new TextButton("help", style); //** Button text and style **//
		
		buttonHelp.setHeight(stage.getViewport().getViewportHeight()*0.10f); //** Button Height **//
		buttonHelp.setWidth(stage.getViewport().getViewportWidth()*0.30f); //** Button Width **//
		buttonHelp.setPosition(stage.getViewport().getViewportWidth()/2-buttonHelp.getWidth()/2
				, stage.getViewport().getViewportHeight() - buttonNewGame.getHeight() -buttonHelp.getHeight() - stage.getViewport().getViewportHeight()*0.20f); //** Button location **//
		buttonHelp.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				//Gdx.app.log("my app", "Pressed"); //** Usually used to start Game, etc. **//
				((MainController)gameController).help();
				return true;
			}

		});
		
		buttonScore = new TextButton("score", style); //** Button text and style **//
		
		buttonScore.setHeight(stage.getViewport().getViewportHeight()*0.10f); //** Button Height **//
		buttonScore.setWidth(stage.getViewport().getViewportWidth()*0.30f); //** Button Width **//
		buttonScore.setPosition(stage.getViewport().getViewportWidth()/2-buttonScore.getWidth()/2
				, stage.getViewport().getViewportHeight() - buttonNewGame.getHeight() -buttonHelp.getHeight() -buttonScore.getHeight() 
				- stage.getViewport().getViewportHeight()*0.30f); //** Button location **//
		buttonScore.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				//Gdx.app.log("my app", "Pressed"); //** Usually used to start Game, etc. **//
				((MainController)gameController).score();
				return true;
			}

		});
		

		stage.addActor(buttonNewGame);
		stage.addActor(buttonHelp);
		stage.addActor(buttonScore);
	}


	protected void renderGame (SpriteBatch batch) {
		batch.setProjectionMatrix(camera.combined);
		batch.setShader(Shader.grayscaleShader);
		batch.begin();
		renderPowerUnit(batch);
		renderButton(batch);
		renderCounter(batch);
		renderScorer(batch);

		batch.end();
		stage.draw();

	}
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		stage.getViewport().update(width, height, true);
		style.font.setScale(0.5f*scale);
//		stage.getCamera().viewportHeight = viewport.getHeight();
//		stage.getCamera().viewportWidth = viewport.getWidth();
	};

	@Override
	public void dispose () {
		super.dispose();
		stage.dispose();
	}


}	

