package com.epic.framework.common.types;

public class Direction
{

	public static final int left = 0;
	public static final int top = 1;
	public static final int right = 2;
	public static final int bottom = 3;

	public static final Direction Left 		= new Direction(0, -1, 0);
	public static final Direction Top 		= new Direction(1, 0, -1);
	public static final Direction Right 	= new Direction(2, 1, 0);
	public static final Direction Bottom 	= new Direction(3, 0, 1);

	public static Direction values[] = new Direction[] {
		Direction.Left,
		Direction.Top,
		Direction.Right,
		Direction.Bottom
	};

	public final int index;
	public final int x_delta;
	public final int y_delta;

	private Direction(int index, int x_delta, int y_delta) {
		this.index = index;
		this.x_delta = x_delta;
		this.y_delta = y_delta;
	}

	public Direction getRotated(Orientation orientation) {
		int i = (this.index + orientation.index) % 4;
		return Direction.values[i];
	}


	public Direction getReverse() {
		return Direction.values[(this.index + 2) % 4];
	}
}
//public enum Direction
//{
//	Left(0, -1, 0),
//	Top(1, 0, -1),
//	Right(2, 1, 0),
//	Bottom(3, 0, 1);
//
//	public static Direction values[] = new Direction[] {
//		Direction.Left,
//		Direction.Top,
//		Direction.Right,
//		Direction.Bottom
//	};
//
//	public final int index;
//	public final int x_delta;
//	public final int y_delta;
//
//	private Direction(int index, int x_delta, int y_delta) {
//		this.index = index;
//		this.x_delta = x_delta;
//		this.y_delta = y_delta;
//	}
//
//	public Direction getReverse() {
//		int i = (this.index + 2) % 4;
//		return Direction.values[i];
//	}
//
//	public Direction getRotated(Orientation orientation) {
//		int i = (this.index + orientation.index) % 4;
//		return Direction.values[i];
//	}
//}
