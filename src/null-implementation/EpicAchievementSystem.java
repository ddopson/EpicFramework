package com.epic.framework.implementation;

import com.epic.framework.common.Ui.EpicAchievement;


public class EpicAchievementSystem {
	public static void initialize() {
	}

	static boolean feintIsSafe() {
		return false;
	}

	public static boolean isUserLoggedIn() {
		return false;
	}

//	public static void unlockAchievement(final EpicAchievement achievement, final EpicAchievementFallbackMethod fallback) {
//		fallback.popupAchievement(achievement, false);
//	}

	public static void submitScore(String leaderboardId, int value) {
		// No high scores on BB
	}

	public static void updateAchievement(EpicAchievement achievement, float progress) {
		// No incremental achievements on BB
	}

	public static void openDashboard() {
		// No OF Dashboard on BB
	}
}
