package com.epic.framework.implementation;

import org.xmlvm.iphone.NSIndexPath;
import org.xmlvm.iphone.UILabel;
import org.xmlvm.iphone.UITableView;
import org.xmlvm.iphone.UITableViewCell;
import org.xmlvm.iphone.UITableViewDataSource;

class ListDataSource extends UITableViewDataSource {
	private String[][] items;
	private String[] sections;

	public ListDataSource(String[][] items, String[] sections) {
		this.items = items;
		this.sections = sections;
	}
	
	public UITableViewCell cellForRowAtIndexPath(UITableView table, NSIndexPath idx) {
		UITableViewCell cell = new UITableViewCell();
		UILabel label = cell.getTextLabel();
		label.setText(items[idx.getSection()][idx.getRow()]);
		return cell;
	}
	
	public String titleForHeaderInSection(UITableView table, int section) {
		return sections[section];
	}

	public int numberOfRowsInSection(UITableView table, int section) {
		return items[section].length;
	}
	
	public int numberOfSectionsInTableView(UITableView table) {
		return sections.length;
	}
}