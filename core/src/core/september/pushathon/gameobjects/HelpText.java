package core.september.pushathon.gameobjects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import core.september.foundation.AbstractGameObject;
import core.september.foundation.Assets;
import core.september.foundation.util.GamePreferences;

public class HelpText extends AbstractGameObject{
	
	private static TextureRegion help01 = Assets.instance.assetHelp.help01;
	private static  TextureRegion help02 = Assets.instance.assetHelp.help02;
	private static TextureRegion help03= Assets.instance.assetHelp.help03;
	
	public HelpText(float x, float y, float width, float height) {
		super( x,  y,  width,  height);
		//init();
	}
	
//	public void init () {
//		help01 = Assets.instance.assetHelp.help01;
//		help02 = Assets.instance.assetHelp.help02;
//		help03 = Assets.instance.assetHelp.help03;
//	}
	
	public static TextureRegion getHelp(int help) {
		switch (help) {
		case 1:
			return help01;
		case 2:
			return help02;
		case 3:
			return help03;
		default:
			return null;
		}
	} 
	
	
	
	

	

	
	

}
