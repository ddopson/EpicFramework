package com.epic.framework.common.Ui;

public class MouseTrail {
	public static final int MIN_DRAG_LENGTH = 3;
	private int[] x = new int[10240];
	private int[] y = new int[10240];
	private int[] t = new int[10240];
	private long startTime;
	int l = 0;
	public MouseTrail() {	
	}
	public void clear() {
		l = 0;
	}
	public int size() {
		return l;
	}
	public int x(int i) {
		return x[i];
	}
	public int y(int i) {
		return y[i];
	}

	public int age(int i) {
		return age(i, System.currentTimeMillis());
	}
	public int age(int i, long currentMillis) {
		return (int)(currentMillis - startTime) - t[i];
	}
	public void add(int x, int y) {
		if(l == 0 || this.x[l-1] != x || this.y[l-1] != y) {
			if(l == 0) {
				startTime = System.currentTimeMillis();
			}
			if(l < this.x.length) {
				this.x[l] = x;
				this.y[l] = y;
				this.t[l] = (int)(System.currentTimeMillis() - startTime);
				l++;	
			}
		}
	}
	public boolean isADrag() {
		return l > MIN_DRAG_LENGTH;
	}
}