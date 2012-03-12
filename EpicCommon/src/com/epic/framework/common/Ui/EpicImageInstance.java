package com.epic.framework.common.Ui;

public class EpicImageInstance extends EpicImage {
	// Since we can't pass "this" in the call to super(), we pass 'null' as a special value when parent should be self-referential
	public static final EpicImage SELF_PARENT = null;

	public final EpicImage parent;
	public final boolean hasAlpha;
	public final int width;
	public final int height;
	public final int iwidth;
	public final int iheight;
	public final int lpad;
	public final int tpad;
	public final int rpad;
	public final int bpad;

	public Object platformObject = null; // load on demand
	public EpicImageInstance(
			EpicImage parent,
			boolean hasAlpha,
			int width, 
			int height, 
			int lpad,
			int tpad,
			int rpad,
			int bpad)
	{
		super(parent.name);
		this.parent = parent;
		this.hasAlpha = hasAlpha;
		this.width = width;
		this.height = height;
		this.iwidth = width - lpad - rpad;
		this.iheight = height - tpad - bpad;
		this.lpad = lpad;
		this.tpad = tpad;
		this.rpad = rpad;
		this.bpad = bpad;
	}

	protected EpicImageInstance(
			String name, 
			boolean hasAlpha, 
			int width,
			int height, 
			int lpad, 
			int tpad, 
			int rpad, 
			int bpad)
	{
		super(name);
		this.parent = this;
		this.hasAlpha = hasAlpha;
		this.width = width;
		this.height = height;
		this.iwidth = width - lpad - rpad;
		this.iheight = height - tpad - bpad;
		this.lpad = lpad;
		this.tpad = tpad;
		this.rpad = rpad;
		this.bpad = bpad;
	}

	@Override
	public EpicImageInstance getInstance(int desiredWidth, int desiredHeight) {
		return parent.getInstance(desiredWidth, desiredHeight);
	}
}