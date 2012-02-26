package com.epic.framework.common.types;

public class Symmetry
{

	public static final Symmetry None = new Symmetry(0);
	public static final Symmetry Single = new Symmetry(1);
	public static final Symmetry Double = new Symmetry(2);

	public final int index;
	Symmetry(int index) {
		this.index = index;
	}
}
