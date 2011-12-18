package com.epic.framework.Ui;

import net.rim.device.api.ui.Field;

public abstract class EpicAbstractButton extends EpicPaintablePanel {
	public EpicAbstractButton() { }

	abstract void onClick();
	abstract void onDraw(EpicCanvas canvas);

	public boolean isEnabled() {
		return this.realPanel.isFocusable();
	}

	public boolean isPressed() {
		return this.realPanel.getVisualState() == Field.VISUAL_STATE_ACTIVE;
	}

	public boolean isFocused() {
		return this.realPanel.isFocus();
	}
	protected boolean onClick(int x, int y) {
		onClick();
		return true;
	}
	protected void onPaint(EpicCanvas canvas, MouseTrail mouseTrail) {
		onDraw(canvas);
	}
}
