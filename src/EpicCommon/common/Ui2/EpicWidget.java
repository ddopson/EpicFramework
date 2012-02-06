package com.epic.framework.common.Ui2;

import com.epic.framework.common.Ui.EpicCanvas;

public abstract class EpicWidget extends EpicObject {
	int x, y, width, height;
	EpicAction onClick;
	public abstract void onPaint(EpicCanvas canvas);
	
	public interface Clickable {
		public void onClick(int x, int y);
	}
}
