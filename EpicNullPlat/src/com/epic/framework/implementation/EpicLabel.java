//package com.epic.framework.implementation;
//
//import net.rim.device.api.ui.Field;
//import net.rim.device.api.ui.Font;
//import net.rim.device.api.ui.component.LabelField;
//
//
//public class EpicLabel extends EpicWidgetBase {
//	int labelColor = EpicColor.BLACK;
//	LabelField l = new LabelField() {
//		protected void paint(net.rim.device.api.ui.Graphics graphics) {
//			graphics.setColor(EpicLabel.this.labelColor);
//			super.paint(graphics);
//		};
//	};
//
//	public EpicLabel() { }
//	public EpicLabel(String value) {
//		l.setText(value);
//	}
//
//	Field getBlackberryField() {
//		return l;
//	}
//
//
//	public void setText(String value) {
//		l.setText(value);
//		l.setDirty(true);
//	}
//
//	public void setTextFromInt(int value) {
//		setText(String.valueOf(value));
//	}
//
//	public void setTextFromTime(int time) {
//	}
//
//	public void setTextSize(int size) {
//		l.setFont(Font.getDefault().derive(Font.PLAIN, size));
//		l.setDirty(true);
//	}
//
//	public void setTextColor(int color) {
//		labelColor = color;
//	}
//}
