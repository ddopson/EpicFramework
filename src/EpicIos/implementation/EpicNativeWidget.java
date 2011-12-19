package com.epic.framework.implementation;

public abstract class EpicNativeWidget {
	public void requestFocus() { }

	public void invalidate() { }

	public void setFocusable(boolean focusable) { }

	public Object getNativeObject() {
		return null;
	}
}
