package com.epic.framework.common.Ui;

public class EpicAchievement {
	public final String name;
	public final String description;
	public final String openFeintId;
	public final int points;

	public EpicAchievement(String name, String description, int points, int openFeintId) {
		this.name = name;
		this.description = description;
		this.points = points;
		this.openFeintId = String.valueOf(openFeintId);
	}
}
