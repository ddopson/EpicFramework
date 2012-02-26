package com.epic.framework.common.Ui;

import java.util.ArrayList;

public class EpicMenu {
	public static final int MENU_GAME = 0;
	public static final int MENU_DEBUG = 1;
	public static final int MENU_SIMULATOR = 2;
	public static final int MENU_ALL = 99;
	
	ArrayList<EpicMenuItem> items = new ArrayList<EpicMenu.EpicMenuItem>();
	boolean dirty;
	
	public static abstract class EpicMenuItem {
		public String name;
		public abstract void onClicked();
	}
	
	public void addItem(String name, EpicMenuItem item) {
		item.name = name;
		items.add(item);
		this.dirty = true;
	}
}
