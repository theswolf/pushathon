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
import core.september.pushathon.gameobjects.Sound;


public abstract class BatchRenderer extends InputAdapter implements Disposable {
	private static final String TAG = BatchRenderer.class.getName();

	protected OrthographicCamera camera;
	protected SpriteBatch batch;
	protected ShapeRenderer shape;
	protected GameController gameController;
	protected Rectangle bounds,touchpoint,viewport;
	public Vector2 touchBounds;
	float scale = 1f;
	boolean increasable = true;
	
	public BatchRenderer() {
		super();
	}

	public BatchRenderer(GameController gameController) {
		Gdx.input.setInputProcessor(this);
		this.gameController = gameController;
		init();
	}

	protected void init () {
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
    	Gdx.gl.glClearColor(Color.DARK_GRAY.r,Color.DARK_GRAY.g,Color.DARK_GRAY.b,Color.DARK_GRAY.a);
    	 Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    	 Gdx.gl.glEnable(GL20.GL_ARRAY_BUFFER_BINDING);
		// Clears the screen
    	// update camera
		
		camera.position.set(Gdx.app.getGraphics().getWidth()/2, Gdx.app.getGraphics().getHeight()/2, 0);
        camera.update();

        // set viewport
        Gdx.gl.glViewport((int) viewport.x, (int) viewport.y,
                          (int) viewport.width, (int) viewport.height);

        // clear previous frame
       
        renderGame(batch);
        //renderMask(shape);
        // DRAW EVERYTHING
    }

    protected void renderGame (SpriteBatch batch) {
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		//gameController.level.render(batch);
		//renderBack(batch);
		bounds = gameController.resources.started? gameController.resources.button.getScaled(scale) :
			gameController.resources.powerButton.getScaled(scale);
		renderPowerUnit(batch);
		renderButton(batch);
		renderCounter(batch);
		renderScorer(batch);
		renderSound (batch);
		//renderText(batch);
		
		batch.end();
	}
	
    protected void renderSound (SpriteBatch batch) {
    	TextureRegion  reg = null;
		reg = gameController.resources.soundUi.getActualRegion();
		Rectangle myBounds = gameController.resources.soundUi.getScaled(scale);
		batch.draw(reg, 
				myBounds.x,
				myBounds.y,
				myBounds.width,
				myBounds.height
				);
		
		batch.setColor(1, 1, 1, 1);
	}
    
    protected void renderText (SpriteBatch batch) {
		BitmapFont font = Assets.instance.fonts.defaultNormal;
		font.setColor(Color.ORANGE);
		font.draw(batch, "This is a test!!!!", 1, 2);
	}
	
    protected void renderMask (ShapeRenderer shape) {
		shape.setProjectionMatrix(camera.combined);
		shape.begin(ShapeType.Line);
		shape.setColor(Color.DARK_GRAY);
		Rectangle boundsD = gameController.resources.counterD.getScaled(scale);
		shape.rect(boundsD.x,boundsD.y,boundsD.width*2,boundsD.height);
		boundsD = gameController.resources.scoreU.getScaled(scale);
		shape.rect(boundsD.x,boundsD.y,boundsD.width*4,boundsD.height);
		
		Gdx.gl.glLineWidth(5);
		shape.end();
	}
	
	
	
    protected abstract boolean isTouched() ;

    protected void renderPowerUnit(SpriteBatch batch) {
		TextureRegion  reg = null;
		reg = isTouched() || gameController.resources.started ? gameController.resources.powerButton.on :  gameController.resources.powerButton.off;
		Rectangle myBounds = gameController.resources.powerButton.getScaled(scale);
		batch.draw(reg, 
				myBounds.x,
				myBounds.y,
				myBounds.width,
				myBounds.height
				);
		TextureRegion ledReg =  gameController.resources.started ? gameController.resources.powerLed.on : gameController.resources.powerLed.off;
		myBounds = gameController.resources.powerLed.getScaled(scale);
		
		batch.draw(ledReg, 
				myBounds.x,
				myBounds.y,
				myBounds.width,
				myBounds.height
				);
		
		batch.setColor(1, 1, 1, 1);
	}
	
    protected void renderButton(SpriteBatch batch) {
		TextureRegion  reg = null;
		reg = isTouched() &&  gameController.resources.started ? gameController.resources.button.down :  gameController.resources.button.up;
		Rectangle myBounds = gameController.resources.button.getScaled(scale);
		batch.draw(reg, 
				myBounds.x,
				myBounds.y,
				myBounds.width,
				myBounds.height
				);
		
		batch.setColor(1, 1, 1, 1);
	}
	
    protected void renderBack(SpriteBatch batch) {

		Rectangle bounds = gameController.resources.box.getScaled(scale);
		
		batch.draw(gameController.resources.box.back, 
				bounds.x,
				bounds.y,
				bounds.width,
				bounds.height
				);
		
		batch.setColor(1, 1, 1, 1);
	}
	
    protected void renderCounter(SpriteBatch batch) {

		Rectangle boundsU = gameController.resources.counterU.getScaled(scale);
		Rectangle boundsD = gameController.resources.counterD.getScaled(scale);
		
		batch.draw(gameController.resources.counterU.selected,
				boundsU.x,
				boundsU.y,
				boundsU.width,
				boundsU.height
				);
		
		batch.draw(gameController.resources.counterD.selected,
				boundsD.x,
				boundsD.y,
				boundsD.width,
				boundsD.height
				);
		
		batch.setColor(1, 1, 1, 1);
	}
	
    protected void renderScorer(SpriteBatch batch) {

		Rectangle boundsU = gameController.resources.scoreU.getScaled(scale);
		Rectangle boundsD = gameController.resources.scoreD.getScaled(scale);
		Rectangle boundsM = gameController.resources.scoreM.getScaled(scale);
		Rectangle boundsDM = gameController.resources.scoreDM.getScaled(scale);
		
		batch.draw(gameController.resources.scoreU.selected,
				boundsU.x,
				boundsU.y,
				boundsU.width,
				boundsU.height
				);
		
		batch.draw(gameController.resources.scoreD.selected,
				boundsD.x,
				boundsD.y,
				boundsD.width,
				boundsD.height
				);
		
		batch.draw(gameController.resources.scoreM.selected,
				boundsM.x,
				boundsM.y,
				boundsM.width,
				boundsM.height
				);
		batch.draw(gameController.resources.scoreDM.selected,
				boundsDM.x,
				boundsDM.y,
				boundsDM.width,
				boundsDM.height
				);
		
		batch.setColor(1, 1, 1, 1);
	}
    protected void resize (int width, int height,OrthographicCamera selectedCamera) {
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
        selectedCamera.viewportHeight = h;
        selectedCamera.viewportWidth = w;
    }
    public void resize (int width, int height) {
    	resize(width,height,camera);
	}

	@Override
	public void dispose () {
		batch.dispose();
		shape.dispose();
	}
	
	protected boolean checkSound(Vector2 touchedBounds, OrthographicCamera ortoCamera) {
		if(touchedBounds != null) {
			Vector3 unproject = ortoCamera.unproject(new Vector3(touchedBounds.x, touchedBounds.y, 0));
			return gameController.resources.soundUi.getScaled(scale).overlaps(new Rectangle(unproject.x,unproject.y,1,1));
		}
		return false;
	}
}
