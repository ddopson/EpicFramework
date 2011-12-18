package com.epic.framework.common.templates;

import com.epic.framework.common.serialization.EpicClass;
import com.epic.framework.common.types.Direction;

public abstract class Grid <T> implements EpicClass {
	public abstract int getNFromXY(int x, int y);
	public abstract int getWidth();
	public abstract int getHeight();
	public abstract int getSize();

	public abstract T get(int n);
	public abstract T get(int x, int y);
	public abstract T get(int x, int y, Direction d);
}
