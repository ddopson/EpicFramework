package com.epic.framework.implementation;

import org.xmlvm.iphone.CGAffineTransform;
import org.xmlvm.iphone.CGRect;
import org.xmlvm.iphone.NSIndexPath;
import org.xmlvm.iphone.UIColor;
import org.xmlvm.iphone.UIImage;
import org.xmlvm.iphone.UIImageView;
import org.xmlvm.iphone.UILabel;
import org.xmlvm.iphone.UITableView;
import org.xmlvm.iphone.UITableViewCell;
import org.xmlvm.iphone.UITableViewDataSource;

import com.epic.framework.common.Ui.EpicImage;
import com.epic.framework.common.Ui.EpicPlatform;

class ChallengeDataSource extends UITableViewDataSource {
	private String[] titles;
	private String[] subtitles;
	private EpicImage[] images;
	private boolean[] complete;

	public ChallengeDataSource(String[] titles, String[] subtitles, boolean[] complete, EpicImage[] images) {
		this.titles = titles;
		this.subtitles = subtitles;
		this.images = images;
		this.complete = complete;
	}
	
	public UITableViewCell cellForRowAtIndexPath(UITableView table, NSIndexPath idx) {
		int padding = 5;
		int imageDims = (int)table.getRowHeight() - (padding * 2);
		int textWidth = (int) table.getFrame().size.width - imageDims - (padding * 3);
		UITableViewCell cell = new UITableViewCell();
		
		// if(complete[idx.getRow()]) cell.getContentView().setBackgroundColor(completedBgColor);
		
		UILabel label = new UILabel(new CGRect(imageDims + (2*padding), padding, textWidth, 26));
		label.setText(titles[idx.getRow()]);
		label.setFont(label.getFont().fontWithSize(EpicPlatform.getPlatformWidth() <= 480 ? 18 : 24));
//		UILabel subtitle = cell.getDetailTextLabel();
//		subtitle.setText(subtitles[idx.getRow()]);
		
		// if(complete[idx.getRow()]) label.setBackgroundColor(UIColor.clearColor);
		cell.addSubview(label);
		
		UIImageView img = cell.getImageView();
		img.setTransform(CGAffineTransform.makeScale(1, -1));
		img.setLocation(padding, padding);
		img.setImage((UIImage) images[idx.getRow()].getInstance(imageDims, imageDims).platformObject);
		int height = 28;
		
		UILabel subtitle = new UILabel(new CGRect(imageDims + (2*padding), padding + (EpicPlatform.getPlatformWidth() <= 480 ? 21 : 25), textWidth, height));
		subtitle.setText(subtitles[idx.getRow()]);
		subtitle.setTextColor(UIColor.lightGrayColor);
		subtitle.setFont(subtitle.getFont().fontWithSize(EpicPlatform.getPlatformWidth() <= 480 ? 14 : 18));
		// if(complete[idx.getRow()]) subtitle.setBackgroundColor(UIColor.clearColor);
		cell.addSubview(subtitle);
		
		return cell;
	}

	public int numberOfRowsInSection(UITableView table, int section) {
		return titles.length;
	}
}