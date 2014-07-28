package core.september.pushathon.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import core.september.foundation.ActionResolver;
import core.september.pushathon.PushathonGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "PushBox";
		config.width = 800;
		config.height = 480;
		new LwjglApplication(new PushathonGame(new ActionResolver() {
			
			@Override
			public void unlockAchievementGPGS(String achievementId) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void submitScoreGPGS(int score) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void loginGPGS() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean getSignedInGPGS() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void getLeaderboardGPGS() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void getAchievementsGPGS() {
				// TODO Auto-generated method stub
				
			}
		}), config);
	}
}
