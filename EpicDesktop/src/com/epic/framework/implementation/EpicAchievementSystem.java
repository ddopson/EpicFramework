package com.epic.framework.implementation;

import com.epic.framework.common.Ui.EpicAchievement;

public class EpicAchievementSystem {

	public static interface EpicAchievementFallbackMethod {
		void popupAchievement(EpicAchievement achievement, boolean alreadyHandled);
	}

	public static void initialize() { }

	public static boolean isUserLoggedIn() { return false; }

	public static void unlockAchievement(final EpicAchievement achievement, final EpicAchievementFallbackMethod fallback) { }

	public static void submitScore(String leaderboardId, int value) { }

	public static void updateAchievement(EpicAchievement achievement, float progress) { }

	public static void openDashboard() { }
}
