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


package core.september.foundation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Disposable;

import core.september.foundation.util.Constants;

public class Assets implements Disposable, AssetErrorListener {

	public static final String TAG = Assets.class.getName();

	public static final Assets instance = new Assets();

	private AssetManager assetManager;

//	public AssetFonts fonts;
//	public AssetBunny bunny;
//	public AssetRock rock;
//	public AssetGoldCoin goldCoin;
//	public AssetFeather feather;
//	public AssetLevelDecoration levelDecoration;
	
	public AssetButton button;
	public AssetBackground  background;
	public AssetCountDown countdown;
	public AssetPowerButton powerButton;
	public AssetLed led;
	public AssetFonts fonts;
	public AssetSoundKnob knob;
	public AssetSounds sounds;
	public AssetUi assetUi;
	
	private static final String font = "fonts/impact.fnt";

	// singleton: prevent instantiation from other classes
	private Assets () {
	}
	
	public class AssetSounds {
//		/home/trim/git/pushathon/android/assets/sounds/engine_on.wav
//		/home/trim/git/pushathon/android/assets/sounds/level_switch.wav
//		/home/trim/git/pushathon/android/assets/sounds/push_button.wav
		public final Sound engine_on;
		public final Sound level_switch;
		public final Sound push_button;

		public AssetSounds (AssetManager am) {
			engine_on = am.get("sounds/engine_on.wav", Sound.class);
			level_switch = am.get("sounds/level_switch.wav", Sound.class);
			push_button = am.get("sounds/push_button.wav", Sound.class);
		}
	}
	
	public class AssetUi {
		public final AtlasRegion next;
		public final AtlasRegion prev;
		public final AtlasRegion play;
		//public final AtlasRegion btnUp;
		public AssetUi (TextureAtlas atlas) { 
			next = atlas.findRegion("next");
			prev = atlas.findRegion("prev");
			play = atlas.findRegion("play");
		}
	}

	public class AssetFonts {
		public final BitmapFont defaultNormal;

		public AssetFonts () {
			// create three fonts using Libgdx's 15px bitmap font
			defaultNormal = new BitmapFont(Gdx.files.internal(font), false);
			defaultNormal.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
	}


	public class AssetPowerButton{
		public final AtlasRegion on;
		public final AtlasRegion off;
		public AssetPowerButton (TextureAtlas atlas) {
			on = atlas.findRegion("powerOnAlpha");
			off = atlas.findRegion("powerOffAlpha");
		}
	}
	public class AssetLed{
		public final AtlasRegion on;
		public final AtlasRegion off;
		public AssetLed (TextureAtlas atlas) {
			on = atlas.findRegion("ledOn");
			off = atlas.findRegion("ledOff");
		}
	}
	
	public class AssetSoundKnob{
		public final AtlasRegion on;
		public final AtlasRegion off;
		public AssetSoundKnob (TextureAtlas atlas) {
			on = atlas.findRegion("sound_on");
			off = atlas.findRegion("sound_off");
		}
	}
	
	public class AssetButton {
		public final AtlasRegion up;
		public final AtlasRegion down;

		public AssetButton (TextureAtlas atlas) {
			up = atlas.findRegion("buttonup");
			down = atlas.findRegion("buttondown");
		}
	}
	
	public class AssetBackground {
		public final AtlasRegion background;

		public AssetBackground (TextureAtlas atlas) {
			background = atlas.findRegion("background");
		}
	}

	public class AssetCountDown {
		public final AtlasRegion off;
		public final AtlasRegion zero;
		public final AtlasRegion uno;
		public final AtlasRegion due;
		public final AtlasRegion tre;
		public final AtlasRegion quattro;
		public final AtlasRegion cinque;
		public final AtlasRegion sei;
		public final AtlasRegion sette;
		public final AtlasRegion otto;
		public final AtlasRegion nove;

		public AssetCountDown (TextureAtlas atlas) {
			off = atlas.findRegion("off");
			zero =   atlas.findRegion("0");
			uno = atlas.findRegion("1");  
			due = atlas.findRegion("2");    
			tre  = atlas.findRegion("3");     
			quattro  = atlas.findRegion("4"); 
			cinque  = atlas.findRegion("5"); 
			sei  = atlas.findRegion("6");  
			sette  = atlas.findRegion("7");  
			otto  = atlas.findRegion("8");    
			nove  = atlas.findRegion("9");    
		}
	}

	public void init (AssetManager assetManager) {
		this.assetManager = assetManager;
		// set asset manager error handler
		assetManager.setErrorListener(this);
		// load texture atlas
		assetManager.load(Constants.TEXTURE_ATLAS_OBJECTS, TextureAtlas.class);
		assetManager.load("sounds/engine_on.wav", Sound.class);
		assetManager.load("sounds/level_switch.wav", Sound.class);
		assetManager.load("sounds/push_button.wav", Sound.class);
		// start loading assets and wait until finished
		assetManager.finishLoading();

		Gdx.app.debug(TAG, "# of assets loaded: " + assetManager.getAssetNames().size);
		for (String a : assetManager.getAssetNames()) {
			Gdx.app.debug(TAG, "asset: " + a);
		}

		TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLAS_OBJECTS);

		// enable texture filtering for pixel smoothing
		for (Texture t : atlas.getTextures()) {
			t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}

		// create game resource objects
		sounds = new AssetSounds(assetManager);
		button = new AssetButton(atlas);
		background = new AssetBackground(atlas);
		countdown = new AssetCountDown(atlas);
		powerButton = new AssetPowerButton(atlas);
		led = new AssetLed(atlas);
		assetUi = new AssetUi(atlas);
		knob = new AssetSoundKnob(atlas);
		fonts = new AssetFonts();
		
		
		
	}

	@Override
	public void dispose () {
		assetManager.dispose();
		fonts.defaultNormal.dispose();
//		fonts.defaultSmall.dispose();
//		fonts.defaultNormal.dispose();
//		fonts.defaultBig.dispose();
	}

	//@Override
	public void error (String filename, Class type, Throwable throwable) {
		Gdx.app.error(TAG, "Couldn't load asset '" + filename + "'", (Exception)throwable);
	}

	@Override
	public void error(AssetDescriptor asset, Throwable throwable) {
		error(asset.fileName,null,throwable);
	}

}
