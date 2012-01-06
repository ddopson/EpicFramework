package com.epic.framework.implementation;

import org.xmlvm.iphone.UITableView;
import org.xmlvm.iphone.UITableViewController;

import com.epic.framework.common.Ui.EpicBitmap;

public class EpicChallengesTableView extends UITableViewController {
	public EpicChallengesTableView(String[] titles, String[] subtitles, EpicBitmap[] images) {
		UITableView table = getTableView();
		table.setDataSource(new ChallengeDataSource(titles, subtitles, images));
	}
	
	public void viewWillDisappear(boolean animated) {
		super.viewWillDisappear(animated);
		Main.navc.setNavigationBarHidden(true, true);
	}
	
	public boolean shouldAutorotateToInterfaceOrientation(int uiInterfaceOrientation) {
		return true;
	}
}
