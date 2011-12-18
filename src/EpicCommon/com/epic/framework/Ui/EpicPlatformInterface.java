package com.epic.framework.Ui;

import com.epic.framework.Ui.EpicPercentLayout.LayoutChild;

public interface EpicPlatformInterface {
	void requestRepaint();
	void requestRelayout();
	void clear();
	void layoutChild(LayoutChild child, int l, int r, int t, int b, boolean firstLayout);
}
