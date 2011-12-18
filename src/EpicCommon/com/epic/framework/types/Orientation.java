package com.epic.framework.types;

public class Orientation
{
	private static final long serialVersionUID = -2820520027378793753L;
	public static final Orientation Orig   = new Orientation(0, "orig");
	public static final Orientation Rot90  = new Orientation(1, "rot90");
	public static final Orientation Rot180 = new Orientation(2, "rot180");
	public static final Orientation Rot270 = new Orientation(3, "rot270");

	public final int index;
	public final String name;

	Orientation(int index, String name) {
		this.index = index;
		this.name = name;
	}
}
