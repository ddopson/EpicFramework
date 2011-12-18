package com.epic.framework.implementation.Ui;

import android.view.View;

import com.epic.framework.common.types.Direction;

public abstract class EpicNativeWidget {
	abstract View getAndroidView();
	private static int auto_increment_id = 0;

	public int getWidth() {
		return getAndroidView().getWidth();
	}

	public int getHeight() {
		return getAndroidView().getHeight();
	}

	public boolean isEnabled() {
		return getAndroidView().isEnabled();
	}

	public boolean isPressed() {
		return getAndroidView().isPressed();
	}

	public boolean isFocused() {
		return getAndroidView().isFocused();
	}

	public void setPadding(int left, int top, int right, int bottom) {
		getAndroidView().setPadding(left, top, right, bottom);
	}

	public void requestFocus() {
		getAndroidView().requestFocus();
		getAndroidView().requestFocusFromTouch();
	}

	public void setEnabled(boolean enabled) {
		getAndroidView().setEnabled(enabled);
	}

	public void invalidate() {
		getAndroidView().invalidate();
	}

	public void setFocusable(boolean focusable) {
		getAndroidView().setFocusable(focusable);
		getAndroidView().setFocusableInTouchMode(focusable);
	}

	public void hideView() {
		getAndroidView().setVisibility(View.GONE);
	}

	public void showView() {
		getAndroidView().setVisibility(View.VISIBLE);
	}

	int getAndroidId() {
		int id = getAndroidView().getId();
		if(id == View.NO_ID) {
			id = auto_increment_id++;
			getAndroidView().setId(id);
		}
		return id;
	}

	public Object getNativeObject() {
		return getAndroidView();
	}
}
