package com.epic.framework.implementation;

import org.xmlvm.iphone.UITableView;
import org.xmlvm.iphone.UITableViewController;

import com.epic.framework.common.Ui.EpicBitmap;
import com.epic.framework.common.util.EpicLog;

public class EpicChallengesTableView extends UITableViewController {
	public EpicChallengesTableView(String[] titles, String[] subtitles, boolean[] complete, EpicBitmap[] images) {
		UITableView table = getTableView();
		table.setRowHeight(70.0f);
		table.setDataSource(new ChallengeDataSource(titles, subtitles, complete, images));
	}
	
	@Override
	public void viewWillDisappear(boolean animated) {
		EpicLog.i("viewWillDisappear in challenge table view");
		super.viewWillDisappear(animated);
		//Main.navc.setNavigationBarHidden(true, true);
	}
	
	@Override
	public void viewDidUnload() {
		EpicLog.i("viewDidUnload in challenge table view");
		super.viewDidUnload();
		//Main.navc.setNavigationBarHidden(true, true);
	}
	
	@Override
	public boolean shouldAutorotateToInterfaceOrientation(int uiInterfaceOrientation) {
		return false;
	}
}
