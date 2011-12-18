package com.epic.framework.common.types;

public class Dimension
{
	public int width;
	public int height;
	
	public Dimension(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public Dimension(Dimension d) {
		this.width = d.width;
		this.height = d.height;
	}
	
	public void set(int width, int height) {
		this.width = width;
		this.height = height;
	}
}