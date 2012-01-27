package com.epic.framework.common.templates;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import com.epic.framework.common.util.ArrayIterator;
import com.epic.framework.common.util.EpicFail;
import com.realcasualgames.words.WordsCell;

public abstract class HexGrid<T> implements Iterable<T> {
	// The grid looks like this:
	//      x         x       
	// x         12        x   
	//      10        2       
	// x        [P]        x     
	//      8         4       
	// x         6         x  
	//      x         x       
	// x         x         x  
	ArrayList<T> array;
//	T[] array;
	int width;
	int height; // note that Y is double the height ...
	
	public HexGrid(int width, int height) {
		this.width = width;
		this.height = height;
		this.array = new ArrayList<T>(width*height);
//		array = (T[])new WordsGame.WordsCell[width*height];
		initializeCells();
	}
	
	protected abstract T newCellInstance(HexGridPosition pos);
	
	public void initializeCells() {
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
//				array[y*width + x] = this.newCellInstance(HexGridPosition.logical(x, y));
				array.add(this.newCellInstance(new HexGridPosition(x, y, y*width + x)));
			}
		}
	}
	
	public T getByIndex(int i) {
		return array.get(i);
	}
	
	public T getNeighbor(int x, int y, HexDirection d) {
		return get(x + d.delta_x, (HexGridPosition.getRenderY(x, y) + d.delta_y) / 2);
	}


	public T get(HexGridPosition pos) {
		return get(pos.x, pos.y);
	}
	
	public T getNeighbor(HexGridPosition pos, HexDirection d) {
		return get(pos.x + d.delta_x, (pos.getRenderY() + d.delta_y) / 2);
	}

	public Collection<T> getNeighbors(HexGridPosition pos) {
		return getNeighbors(pos.x, pos.y, new ArrayList<T>(6));
	}
	
	public Collection<T> getNeighbors(HexGridPosition pos, Collection<T> neighbors) {
		return getNeighbors(pos.x, pos.y, neighbors);
	}
	
	public Collection<T> getNeighbors(int x, int y) {
		return getNeighbors(x, y, new ArrayList<T>(6));
	}
	
	public Collection<T> getNeighbors(int x, int y, Collection<T> neighbors) {
		int logical_y = y*2 + (x%2);
		for(HexDirection d : HexDirection.all) {
			T neighbor = get(x + d.delta_x, (logical_y+d.delta_y)/2);
			if(neighbor != null) {
				neighbors.add(neighbor);
			}
		}
		return neighbors;
	}
	
	public T get(int x, int y) {
		if(x >= width || x < 0 || y >= height || y < 0 ) {
			return null;
		}
		else {
//			return array[y * width + x];
			return array.get(y * width + x);
		}
	}
	
	public static class HexGridPosition {
		public final int x;
		public final int y;
		public final int i;
		protected HexGridPosition(int x, int y, int i) {
			this.x = x;
			this.y = y;
			this.i = i;
		}
		public HexGridPosition(HexGridPosition other) {
			this.x = other.x;
			this.y = other.y;
			this.i = other.i;
		}
		
		public int getRenderY() {
			return 2*y + (x % 2);
		}

		public static int getRenderY(int x, int y) {
			return 2*y + (x % 2);
		}
//		public static HexGridPosition logical(int x, int y) {
//			return new HexGridPosition(x, y, y*);
////			int index = x*x + 2*y*y; // guaranteed to be unique
////			if(!indexMap.containsKey(index)) {
////				indexMap.put(index, new HexGridPosition(x, y));
////			}
////			return indexMap.get(index);
//		}
	}
	
	public static class HexGridCell extends HexGridPosition {
		public HexGridCell(HexGridPosition pos) {
			super(pos);
		}
	}

	public Iterator<T> iterator() {
		return this.array.iterator();
	}
//	public Iterator<T> iterator() {
//		return new ArrayIterator<T>(array);
//	}
	
	public static class HexDirection {
		public final int delta_x;
		public final int delta_y;

		public static final HexDirection two = new HexDirection(1, -1);
		public static final HexDirection four = new HexDirection(1, 1);
		public static final HexDirection six = new HexDirection(0, 2);
		public static final HexDirection eight = new HexDirection(-1, 1);
		public static final HexDirection ten = new HexDirection(-1, -1);
		public static final HexDirection twelve = new HexDirection(0, -2);

		public static final HexDirection[] all = new HexDirection[] {
			two, four, six, eight, ten, twelve
		};
		
		public HexDirection(int delta_x, int delta_y) {
			this.delta_x = delta_x;
			this.delta_y = delta_y;
		}
	}
	
	public int size() {
		return width*height;
	}
}
