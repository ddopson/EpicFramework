package com.epic.framework.implementation

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JComponent;

import com.epic.framework.types.Direction;

public abstract class EpicNativeWidget {
	abstract JComponent getDesktopComponent();
//
//	protected void drawBackground(EpicCanvas canvas) {
//		this.drawBackground(canvas.graphics);
//	}
//
//	void drawBackground(Graphics graphics) {
//		if(this.background != null) {
//			graphics.drawImage((BufferedImage)this.background.getPlatformObject(), 0, 0, this.getWidth(), this.getHeight(), null);
//		}
//	}

//	public void setBackground(String imageName) {
//
//	}

	
	public int getWidth() {
		return getDesktopComponent().getWidth();
	}

	public int getHeight() {
		return getDesktopComponent().getHeight();
	}

	public boolean isEnabled() {
		return true;
	}

	public boolean isPressed() {
		JComponent component = getDesktopComponent();
		if(component instanceof JButton) {
			JButton button = (JButton)component;
			return button.getModel().isPressed();
		}
		else {
			return false;
		}
	}

	public boolean isFocused() {
		return getDesktopComponent().isFocusOwner();
	}

	public void setPadding(int left, int top, int right, int bottom) {

	}

	public void requestFocus() {
		getDesktopComponent().requestFocus();
	}

	public void setEnabled(boolean enabled) {
		getDesktopComponent().setEnabled(enabled);
	}

	public void invalidate() {
		getDesktopComponent().repaint();
	}

	public void setFocusable(boolean focusable) {
		getDesktopComponent().setFocusable(focusable);
	}

	public Object getNativeObject() {
		return getDesktopComponent();
	}

	public void hideView() {
		getDesktopComponent().hide();
	}
	
	public void showView() {
		getDesktopComponent().show();
	}
}
