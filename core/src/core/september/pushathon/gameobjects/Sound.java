package core.september.pushathon.gameobjects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import core.september.foundation.AbstractGameObject;
import core.september.foundation.Assets;
import core.september.foundation.util.GamePreferences;

public class Sound extends AbstractGameObject{
	
	private TextureRegion on;
	private TextureRegion off;
	
	public Sound(float x, float y, float width, float height) {
		super( x,  y,  width,  height);
		init();
	}
	
	public void init () {
		on = Assets.instance.knob.on;
		off = Assets.instance.knob.off;
	}
	
	public TextureRegion getActualRegion() {
		return GamePreferences.instance.sound ? on : off;
	} 
	
	
	
	

	

	
	

}
