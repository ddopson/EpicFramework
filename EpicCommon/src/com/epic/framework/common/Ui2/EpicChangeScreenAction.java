package com.epic.framework.common.Ui2;

import com.epic.framework.common.Ui.EpicPlatform;
import com.epic.framework.common.Ui.EpicScreen;

public class EpicChangeScreenAction extends EpicAction {
	String screenName;
	EpicScreen screenObject;

	@Override
	public void run() {
		EpicPlatform.changeScreen(screenObject);
	}
}
