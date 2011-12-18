//package com.epic.framework.Ui;
//
//public class EpicLabel extends EpicPaintablePanel {
//	private String text;
//	private EpicFont font = EpicFont.fromSize(12);
//	private int color = EpicColor.BLACK;
//	private int halign = EpicFont.HALIGN_LEFT;
//	private int valign = EpicFont.VALIGN_TOP;
//	
//	public EpicLabel(String text) {
//		this.text = text;
//	}
//
//	public EpicLabel(String text, int color) {
//		this.text = text;
//		this.color = color;
//	}
//	
//	protected void onPaint(EpicCanvas canvas, MouseTrail mouseTrail) {
//		canvas.drawText(text, 0, 0, this.getWidth(), this.getHeight(), font, color, halign, valign);
//	}
//
//	protected boolean onClick(int x, int y) {
//		return false;
//	}
//	
//	public void setText(String text) {
//		this.text = text;
//		this.invalidate();
//	}
//
//	public void setTextSize(int size) {
//		this.font = EpicFont.fromSize(size);
//	}
//
//	public void setTextColor(int color) {
//		this.color = color;
//	}
//}