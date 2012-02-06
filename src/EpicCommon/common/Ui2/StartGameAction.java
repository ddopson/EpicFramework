package com.epic.framework.common.Ui2;

import com.epic.framework.common.Ui.EpicPlatform;
import com.realcasualgames.words.DailyOffer;
import com.realcasualgames.words.PlayerState;
import com.realcasualgames.words.ScreenDailySpecials;
import com.realcasualgames.words.ScreenGameLoading;
import com.realcasualgames.words.ScreenTutorial;

public class StartGameAction extends EpicAction {
	public static final StartGameAction singleton = new StartGameAction();
	
	@Override
	public void run() {
		if(EpicPlatform.isIos()) {
			if(!PlayerState.tutorialStarted()) {
				EpicPlatform.changeScreen(new ScreenTutorial());
			} else {
				DailyOffer[] offers = DailyOffer.getDailyOffers();
				if(offers == null || PlayerState.getState().upcomingSeed >= 0) {
					EpicPlatform.changeScreen(new ScreenGameLoading());
				} else {
					EpicPlatform.changeScreen(new ScreenDailySpecials(offers));
				}
			}
		} else {
			EpicPlatform.changeScreen(new ScreenGameLoading());
		}
	}
}
