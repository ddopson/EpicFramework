package com.epic.framework.templates;

import com.epic.framework.serialization.EpicSerializableClass;
import com.epic.framework.serialization.EpicSerializableClassType;
import com.epic.framework.serialization.EpicSerializationStream;
import com.epic.framework.types.Direction;

public class GridImplementation extends Grid<T> implements EpicSerializableClass {
	private static final long serialVersionUID = 6674908412104717202L;
	private T[] items;
	private int width;
	private int height;
	private T outOfBoundsValue;

	public final static T WRAPPING_BEHAVIOR = null;

	public GridImplementation() {}

	public GridImplementation(int width, int height, T outOfBoundsValue, GridSquareFactory factory) {
		this.width = width;
		this.height = height;
		this.items = new T[width * height];
		this.outOfBoundsValue = outOfBoundsValue;

		// create squares
		for (int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				int i = y * this.width + x;
				this.items[i] = factory.createSquare(this, x, y);
			}
		}
	}

	public int getNFromXY(int x, int y) {
		return y * this.width + x;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getSize() {
		return width * height;
	}

	public interface GridSquareFactory {
		T createSquare(GridImplementation grid, int x, int y);
	}

	public T get(int n) {
		return this.items[n];
	}

	public T get(int x, int y) {
		if(x >= 0 && x < this.width && y >= 0 && y < this.height) {
			int n = y * width + x;
			return get(n);
		}
		else {
			if(outOfBoundsValue == null) {
				return get((x + width) % width, (y + height) % height);
			}
			else {
				return outOfBoundsValue;
			}
		}
	}

	public T get(int x, int y, Direction d) {
		return this.get(x + d.x_delta, y + d.y_delta);
	}

	public static interface GridOutOfBoundsBehavior {
		T getOutOfBoundsValue(GridImplementation grid, int x, int y);
	}

	public EpicSerializableClassType getClassType() {
		return classType;
	}
	public static EpicSerializableClassType classType = new EpicSerializableClassType() {
		public EpicSerializableClass serialize(EpicSerializationStream stream, EpicSerializableClass object) {
			GridImplementation grid = stream.isInput() ? new GridImplementation() : (GridImplementation)object;
			grid.width = stream.serializeInt16(grid.width);
			grid.height = stream.serializeInt16(grid.height);
			if(stream.isInput()) {
				grid.items = new T[grid.width * grid.height];
			}
			for(int i = 0; i < grid.width * grid.height; i++) {
				grid.items[i] = (T)stream.serializeObject(grid.items[i], T.classType);
			}
			grid.outOfBoundsValue = (T)stream.serializeObject(grid.outOfBoundsValue, T.classType);
			return grid;
		}
	};

}
