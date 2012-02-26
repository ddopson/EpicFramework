package com.epic.framework.Ui;

import net.rim.device.api.ui.Field;

public abstract class EpicNativeWidget {
	private boolean enabled;

	abstract Field getBlackberryField();

//	public void setBackground(EpicBitmap image) {
//		EpicLog.v(this.getClass().getName() + ".setBackground(" + image.name + ")");
//		((BlackberryFieldInterface)getBlackberryField()).setBackground(image);
//	}

//	public void setBackground(String imageName) {
//		setBackground(EpicImageCache.getImage(imageName));
//	}

	public int getWidth() {
		return getBlackberryField().getWidth();
	}

	public int getHeight() {
		return getBlackberryField().getHeight();
	}

	public boolean isEnabled() {
		return this.enabled;
	}

	public boolean isPressed() {
		// TODO: what does isPressed mean?
		return false;
	}

	public boolean isFocused() {
		return getBlackberryField().isFocus();
	}

	public void setPadding(int left, int top, int right, int bottom) {
	}

	public void requestFocus() {
		getBlackberryField().setFocus();
	}

	public void setEnabled(boolean enabled) {
		getBlackberryField().setEditable(enabled);
		this.enabled = enabled;
	}

	public void invalidate() {
		// TODO: this likely isnt the behavior you want--in BlackBerry you invalidate managers, not fields
		getBlackberryField().setDirty(true);
		getBlackberryField().getManager().invalidate();
	}

	public void setFocusable(boolean focusable) {
		// TODO: this is bitch in BlackBerry--not high enough
	}

	public Object getNativeObject() {
		return getBlackberryField();
	}
}
