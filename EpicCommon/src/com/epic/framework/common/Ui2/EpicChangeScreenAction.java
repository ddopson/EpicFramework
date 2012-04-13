package com.epic.framework.common.Ui2;

import com.epic.framework.common.EpicFieldInflation;
import com.epic.framework.common.Ui.EpicPlatform;
import com.epic.framework.common.Ui.EpicScreen;
import com.epic.framework.common.util.EpicFail;

public class EpicChangeScreenAction extends EpicAction {
	String screenName;

	@EpicFieldInflation(ignore=true)
	EpicScreen screenObject;

	@Override
	public void run() {
		if(screenObject == null) {
			screenObject = (EpicScreen)Registry.get(screenName);
			EpicFail.assertNotNull(screenObject);
		}
		EpicPlatform.changeScreen(screenObject);
	}
}
