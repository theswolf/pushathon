package core.september.pushathon.android;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.games.Games;

import core.september.foundation.ActionResolver;
import core.september.pushathon.PushathonGame;
import core.september.pushathon.android.play.GameHelper;
import core.september.pushathon.android.play.GameHelper.GameHelperListener;

public class AndroidLauncher extends AndroidApplication implements ActionResolver{
	
	protected GameHelper mHelper;
	private static final String AD_UNIT_ID =  "ca-app-pub-0131970786644605/3354463465";
	  private static final String GOOGLE_PLAY_URL = "https://play.google.com/store/apps/developer?id=TheInvader360";
	  protected AdView adView;
	  protected View gameView;
	
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
	
	protected Payload<Integer> payload;
	
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
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useAccelerometer = false;
		config.useCompass = false;
//		
//		initialize(new PushathonGame(this), config);
//		initHelper(false);
//		
//		AdView adView = (AdView) this.findViewById(R.id.adView);
//	    AdRequest adRequest = new AdRequest.Builder().build();
//	    adView.loadAd(adRequest);
		
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

	    RelativeLayout layout = new RelativeLayout(this);
	    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
	    layout.setLayoutParams(params);

	    View gameView = createGameView(config);
	    AdView admobView = createAdView();
	    layout.addView(gameView);
	    layout.addView(admobView);
	    
	   

	    setContentView(layout);
	    initHelper(false);
	    startAdvertising(admobView);

	}
	
	private AdView createAdView() {
	    adView = new AdView(this);
	    adView.setAdSize(AdSize.SMART_BANNER);
	    adView.setAdUnitId(AD_UNIT_ID);
	    adView.setId(12345); // this is an arbitrary id, allows for relative positioning in createGameView()
	    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
//	    params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
//	    params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
	    params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
	    params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
	    params.addRule(RelativeLayout.BELOW, gameView.getId());
	    adView.setLayoutParams(params);
	    adView.setBackgroundColor(Color.BLACK);
	    return adView;
	  }

	  private View createGameView(AndroidApplicationConfiguration cfg) {
	    gameView = initializeForView(new PushathonGame(this), cfg);
	    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
	    params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
	    params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
//	    params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
//	    params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
//	    params.addRule(RelativeLayout.BELOW, adView.getId());
	    gameView.setLayoutParams(params);
	    return gameView;
	  }
	  
	  private void startAdvertising(AdView adView) {
		    AdRequest adRequest = new AdRequest.Builder().build();
		    adView.loadAd(adRequest);
		  }
	
	private void initHelper(boolean connectOnStart) {
	    mHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
	    mHelper.setConnectOnStart(connectOnStart);
//	    // enable debug logs (if applicable)
//	    if (DEBUG_BUILD) {
	        mHelper.enableDebugLog(true);
//	    }

	    GameHelperListener listener = new GameHelper.GameHelperListener() {
	        @Override
	        public void onSignInSucceeded() {
	            // handle sign-in succeess
	        	if(AndroidLauncher.this.payload != null) {
	        		switch (AndroidLauncher.this.payload.type) {
					case SUBMIT_SCORE:
						AndroidLauncher.this.submitScoreGPGS(AndroidLauncher.this.payload.extraData);
						break;
					case GET_LEADERBOARD:
						AndroidLauncher.this.getLeaderboardGPGS();
						break;
					case GET_ACHIEVEMENTS:
						AndroidLauncher.this.getAchievementsGPGS();
						break;
					default:
						break;
					}
	        		
	        		AndroidLauncher.this.payload = null;
	        	}
	        }
	        @Override
	        public void onSignInFailed() {
	            // handle sign-in failure (e.g. show Sign In button)
	        }

	    };
	    
	    
	    mHelper.setup(listener);

	}



	@Override
	public void onStart(){
		super.onStart();
		mHelper.onStart(this);
	}

	@Override
	public void onStop(){
		super.onStop();
		mHelper.onStop();
	}
	
	 @Override
	  public void onResume() {
	    super.onResume();
	    if (adView != null) adView.resume();
	  }

	  @Override
	  public void onPause() {
	    if (adView != null) adView.pause();
	    super.onPause();
	  }

	  @Override
	  public void onDestroy() {
	    if (adView != null) adView.destroy();
	    super.onDestroy();
	  }
	
	@Override
	protected void onActivityResult(int request, int response, Intent data) {
	    super.onActivityResult(request, response, data);
	    mHelper.onActivityResult(request, response, data);
	}

	
	@Override
	public boolean getSignedInGPGS() {
		return mHelper.isSignedIn();
	}

	@Override
	public void loginGPGS() {
	}

	@Override
	public void submitScoreGPGS(int score) {
		submitScoreAsync(score);
	}
	
	private void submitScoreAsync(int score) {
		if(getSignedInGPGS()) {
			submitScoreSync(score);
		}
		else {
			mHelper.beginUserInitiatedSignIn();
			payload = new Payload<Integer>(score, Payload.Type.SUBMIT_SCORE);
		}
	}
	
	private void submitScoreSync(int score) {
		Games.Leaderboards.submitScore(mHelper.getApiClient(), LeadebordsID.TOP_PUSHBOXER.getId(), score);
		AchievemntsID ach = AchievemntsID.getAchievemntByScore(score);
		if(ach != null) {
			unlockAchievementGPGS(ach.getId());
		}
	}
	
	@Override
	public void unlockAchievementGPGS(String achievementId) {
		Games.Achievements.unlock(mHelper.getApiClient(), achievementId);
	}
	
	@Override
	public void getLeaderboardGPGS() {
		getLeaderboardAsync();

	}
	
	public void getLeaderboardSync() {
		startActivityForResult(Games.Leaderboards.getLeaderboardIntent(mHelper.getApiClient(),
		        LeadebordsID.TOP_PUSHBOXER.getId()), 9002);

	}
	
	public void getLeaderboardAsync() {
		if(getSignedInGPGS()) {
			getLeaderboardSync();
		}
		else {
			mHelper.beginUserInitiatedSignIn();
			payload = new Payload<Integer>(null, Payload.Type.GET_LEADERBOARD);
		}

	}

	@Override
	public void getAchievementsGPGS() {
	}
	
	public void getAchievementSync() {
		startActivityForResult(
				Games.Achievements.getAchievementsIntent(mHelper.getApiClient()),9002);

	}
	
	public void getAchievementAsync() {
		if(getSignedInGPGS()) {
			getAchievementSync();
		}
		else {
			mHelper.beginUserInitiatedSignIn();
			payload = new Payload<Integer>(null, Payload.Type.GET_ACHIEVEMENTS);
		}

	}
	
	private static class Payload<T> {
		public enum Type {
			SUBMIT_SCORE,
			GET_LEADERBOARD,
			GET_ACHIEVEMENTS
		}
		
		public T extraData;
		public Type type;
		public Payload(T extraData, Type type) {
			super();
			this.extraData = extraData;
			this.type = type;
		}
		
		
		
		
	}
}
