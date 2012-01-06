package com.epic.framework.implementation;

import org.xmlvm.iphone.NSIndexPath;
import org.xmlvm.iphone.UIImage;
import org.xmlvm.iphone.UIImageView;
import org.xmlvm.iphone.UILabel;
import org.xmlvm.iphone.UITableView;
import org.xmlvm.iphone.UITableViewCell;
import org.xmlvm.iphone.UITableViewDataSource;

import com.epic.framework.common.Ui.EpicBitmap;

class ChallengeDataSource extends UITableViewDataSource {
	private String[] titles;
	private String[] subtitles;
	private EpicBitmap[] images;

	public ChallengeDataSource(String[] titles, String[] subtitles, EpicBitmap[] images) {
		this.titles = titles;
		this.subtitles = subtitles;
		this.images = images;
	}
	
	public UITableViewCell cellForRowAtIndexPath(UITableView table, NSIndexPath idx) {
		UITableViewCell cell = new UITableViewCell();
		UILabel label = cell.getTextLabel();
		label.setText(titles[idx.getRow()] + " - " + subtitles[idx.getRow()]);
//		UILabel subtitle = cell.getDetailTextLabel();
//		subtitle.setText(subtitles[idx.getRow()]);
		UIImageView img = cell.getImageView();
		img.setImage((UIImage) images[idx.getRow()].getPlatformObject((int)table.getRowHeight(), (int)table.getRowHeight()));
		return cell;
	}

	public int numberOfRowsInSection(UITableView table, int section) {
		return titles.length;
	}
}