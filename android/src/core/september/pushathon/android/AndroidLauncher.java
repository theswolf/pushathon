package core.september.pushathon.android;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.GameHelper;
import com.google.example.games.basegameutils.GameHelper.GameHelperListener;

import core.september.foundation.ActionResolver;
import core.september.pushathon.PushathonGame;

public class AndroidLauncher extends AndroidApplication implements GameHelperListener,ActionResolver{
	
	protected GameHelper gameHelper;
	
	
	private enum LeadebordsID {
		TOP_PUSHBOXER( "CgkI-OqqhLMdEAIQAQ");
		
		private String ID;
		private LeadebordsID(String id) {
			this.ID = id;
		}
		public String getId() {
			return ID;
		}
	}
	
	private enum AchievemntsID {
		MORE_THAN_100("CgkI-OqqhLMdEAIQAg"),	
		MORE_THAN_150( "CgkI-OqqhLMdEAIQAw"),
		MORE_THAN_200( "CgkI-OqqhLMdEAIQBA"),
		MORE_THAN_250( "CgkI-OqqhLMdEAIQBQ"),
		MORE_THAN_300( "CgkI-OqqhLMdEAIQBg");
		
		private static final int[] scoreLimits = new int[]{100,150,200,250,300};
		
		private String ID;
		private AchievemntsID(String id) {
			this.ID = id;
		}
		public String getId() {
			return ID;
		}
		
		public static AchievemntsID getAchievemntByScore(int score) {
			if(score > scoreLimits[scoreLimits.length-1]) return AchievemntsID.MORE_THAN_300;
			for (int scoreLimit: scoreLimits) {
				int counter = 0;
				if(score > scoreLimit && score < scoreLimit + 50) {
					return AchievemntsID.values()[counter];
				}
				counter ++;
			}
			return null;
		}
	}
	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
		gameHelper.enableDebugLog(true);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useAccelerometer = false;
		config.useCompass = false;
		
		initialize(new PushathonGame(this), config);
		gameHelper.setup(this);
		gameHelper.setConnectOnStart(false);
	}



	@Override
	public void onStart(){
		super.onStart();
		gameHelper.onStart(this);
	}

	@Override
	public void onStop(){
		super.onStop();
		gameHelper.onStop();
	}
	
	@Override
	public void onActivityResult(int request, int response, Intent data) {
		super.onActivityResult(request, response, data);
		gameHelper.onActivityResult(request, response, data);
	}
	
	@Override
	public boolean getSignedInGPGS() {
		return gameHelper.isSignedIn();
	}

	@Override
	public void loginGPGS() {
		try {
			runOnUiThread(new Runnable(){
				public void run() {
					gameHelper.beginUserInitiatedSignIn();
				}
			});
		} catch (final Exception ex) {
			android.util.Log.e(AndroidLauncher.class.getSimpleName(), ex.getMessage(),ex);
		}
	}

	@Override
	public void submitScoreGPGS(int score) {
		gameHelper.getGamesClient().submitScore(LeadebordsID.TOP_PUSHBOXER.getId(), score);
		AchievemntsID achievement = AchievemntsID.getAchievemntByScore(score);
		if(achievement != null) {
			unlockAchievementGPGS(achievement.getId());
		}
		
	}
	
	@Override
	public void unlockAchievementGPGS(String achievementId) {
		gameHelper.getGamesClient().unlockAchievement(achievementId);
	}
	
	@Override
	public void getLeaderboardGPGS() {
		startActivityForResult(gameHelper.getGamesClient().getLeaderboardIntent(LeadebordsID.TOP_PUSHBOXER.getId()), 100);
	}

	@Override
	public void getAchievementsGPGS() {
		startActivityForResult(gameHelper.getGamesClient().getAchievementsIntent(), 101);
	}
	
	@Override
	public void onSignInFailed() {
		 Toast.makeText(getApplicationContext(), "Error on signing in, please try later...", 
		    Toast.LENGTH_LONG).show();
	}

	@Override
	public void onSignInSucceeded() {
		//gameHelper.getGamesClient().doPostSigninOps();
	}

}
