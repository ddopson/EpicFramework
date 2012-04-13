package com.epic.framework.common.Ui2;

import com.epic.framework.common.Ui.EpicCanvas;
import com.epic.framework.common.Ui.EpicMenu;
import com.epic.framework.common.Ui.EpicPercentLayout;
import com.epic.framework.common.Ui.EpicScreen;
import com.epic.framework.common.Ui.MouseTrail;
import com.epic.framework.common.Ui2.EpicObject;
import com.epic.framework.common.util.EpicLog;

public class EpicScreenObject extends EpicScreen {
	EpicWidget[] widgets;

	protected void onPaint(EpicCanvas canvas, int width, int height, MouseTrail mouseTrail) {
		for(int i = 0; i < widgets.length; i++) {
			if(widgets[i].getWidgetType() == EpicWidget.WidgetTypeVirtual) {
				EpicVirtualWidget casted = (EpicVirtualWidget)widgets[i];
				casted.onPaint(canvas);
			}
		}
	}

	protected void onClick(int x, int y) {
		for(int i = 0; i < widgets.length; i++) {
			EpicWidget w = widgets[i];
			if(widgets[i].onClick != null) {
				if(x >= w.x && x < (w.x + w.width)
				&& y >= w.y && y < (w.y + w.height)) {
					widgets[i].onClick.run();
				}
			}
		}
	}

	public void onCreateUi(EpicPercentLayout bgLayout) {
		for(int i = 0; i < widgets.length; i++) {
			if(widgets[i].getWidgetType() == EpicWidget.WidgetTypeNative) {
				EpicLog.i("Adding Native Widget");
				EpicNativeWidget casted = (EpicNativeWidget)widgets[i];
				bgLayout.addChild(casted.x, casted.y, casted.width, casted.height, casted);
			}
		}
	}

	public void onShow() { }
	protected void onHide() { }
	public boolean onBackKey() { return true; }
	public boolean onKeyPress(int c) { return false; }
	public boolean onNavigationMovement(int x, int y) { return true; }
	protected void onMenuOpened(EpicMenu menu, EpicMenu debugMenu) { }
	protected void onMenuClosed() { }
	protected void onTimerTick() { }
}
