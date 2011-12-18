package com.epic.framework.common.Ui;

import com.epic.framework.common.util.EpicHttpResponse;

public abstract class EpicScreen {
	protected abstract boolean onKeyPress(int c);
	protected abstract boolean onBackKey();
	protected abstract boolean onNavigationMovement(int x, int y);
	protected abstract void onShow();
	protected abstract void onHide();
	protected abstract void onCreateUi(EpicPercentLayout bgLayout);
	protected void onDestroyUi() {}
	protected abstract void onMenuOpened(EpicMenu menu, EpicMenu debugMenu);
	protected abstract void onMenuClosed();
	protected abstract void onClick(int x, int y);
	protected abstract void onPaint(EpicCanvas epicCanvas, int screenWidth, int screenHeight, MouseTrail mouseTrail);
	protected abstract void onTimerTick();

	protected void onDragFinished(MouseTrail mouseTrail) { }

// stuff that has been removed
	@Deprecated
	protected final void onEnterKey() { }
	@Deprecated
	protected final void onNavigationClick() { }
	@Deprecated
	protected final void onOptionsMenuOpened() { }
	@Deprecated
	protected final void networkRequestComplete(String taskName,	EpicHttpResponse response) {}
}
