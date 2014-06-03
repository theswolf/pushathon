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
	public AssetFonts fonts;
	private static final String font = "fonts/impact.fnt";

	// singleton: prevent instantiation from other classes
	private Assets () {
	}

	public class AssetFonts {
		public final BitmapFont defaultNormal;

		public AssetFonts () {
			// create three fonts using Libgdx's 15px bitmap font
			defaultNormal = new BitmapFont(Gdx.files.internal(font), true);
			defaultNormal.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
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
		button = new AssetButton(atlas);
		background = new AssetBackground(atlas);
		countdown = new AssetCountDown(atlas);
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
