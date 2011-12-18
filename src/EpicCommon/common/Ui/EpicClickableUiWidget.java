package com.epic.framework.common.Ui;


public abstract class EpicClickableUiWidget extends EpicUiWidget {
	public EpicClickableUiWidget(int left, int top, int right, int bottom) {
		super(left, top, right, bottom);
	}
	public abstract void onClick(int x, int y);
	public boolean processCLick(int x, int y) {
		if(
				x >= left && x < right
				&& y >= top && y < bottom
		) {
			onClick(x, y);
			return true;
		}
		else {
			return false;
		}
	}
}
