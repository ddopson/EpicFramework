package com.epic.framework.common.Ui;

public class EpicBitmapInstance {
	public final EpicBitmap parent;
	public final int width;
	public final int height;
	public final int iwidth;
	public final int iheight;
	public final int lpad;
	public final int tpad;
	public final int rpad;
	public final int bpad;
	public Object platformObject = null; // load on demand
	public EpicBitmapInstance(
			EpicBitmap parent,
			int width, 
			int height, 
			int lpad,
			int tpad,
			int rpad,
			int bpad
	) {
		this.parent = (parent == null) ? (EpicBitmap)this : parent; // hack, since EpicBitmap can't pass "this" in the call to super()
		this.width = width;
		this.height = height;
		this.iwidth = width - lpad - rpad;
		this.iheight = height - tpad - bpad;
		this.lpad = lpad;
		this.tpad = tpad;
		this.rpad = rpad;
		this.bpad = bpad;
	}
}