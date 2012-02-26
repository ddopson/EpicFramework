package com.epic.framework.common.Ui;

public abstract class EpicUiWidget {
	public int left;
	public int top;
	public int right;
	public int bottom;
	public EpicUiWidget(int left, int top, int right, int bottom) {
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
	}
	protected abstract void render(EpicCanvas canvas, int left, int top, int width, int height);
	public void render(EpicCanvas canvas) {
		render(
				canvas,
				left,
				top,
				right - left,
				bottom - top
		);
	}
}
