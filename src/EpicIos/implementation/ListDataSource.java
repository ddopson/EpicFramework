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
		String text = items[getAdjustedSection(idx.getSection())][idx.getRow()];
		if(text == null) {
			text = "ERROR";
		}
		
		label.setText(text);
		return cell;
	}
	
	public String titleForHeaderInSection(UITableView table, int section) {
		return sections[getAdjustedSection(section)];
	}

	public int numberOfRowsInSection(UITableView table, int section) {
		return items[getAdjustedSection(section)].length;
	}
	
	public int numberOfSectionsInTableView(UITableView table) {
		int num = sections.length;
		
		for(int i = 0; i < sections.length; ++i) {
			if(items[i].length == 0) {
				--num;
			}
		}
		
		return num;
	}
	
	public int getAdjustedSection(int section) {
		for(int i = 0; i <= section; ++i) {
			if(items[i].length == 0) {
				section++;
			}
		}
		
		return section;
	}
}