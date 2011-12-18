package com.epic.framework.common.Ui;

import com.epic.framework.common.Ui.EpicPercentLayout.LayoutChild;

public interface EpicPlatformInterface {
	void requestRepaint();
	void requestRelayout();
	void clear();
	void layoutChild(LayoutChild child, int l, int r, int t, int b, boolean firstLayout);
}
