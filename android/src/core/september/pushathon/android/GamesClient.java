package core.september.pushathon.android;

import android.content.Intent;

import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.GameHelper;

public class GamesClient {

	private GameHelper helper;
	//private final int leaderBoardID = 100;
	public GamesClient(GameHelper gameHelper) {
		helper = gameHelper;
	}

	public void submitScore(String string, int score) {
		Games.Leaderboards.submitScore(helper.getApiClient(), string, score);
		
	}

	public void unlockAchievement(String achievementId) {
		Games.Achievements.unlock(helper.getApiClient(), achievementId);
		
	}

	public Intent getLeaderboardIntent(String string) {
		return Games.Leaderboards.getLeaderboardIntent(helper.getApiClient(), string);
	}

	public Intent getAchievementsIntent() {
		return Games.Achievements.getAchievementsIntent(helper.getApiClient());
	}

	

}
