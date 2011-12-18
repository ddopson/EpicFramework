package com.epic.framework.util;

import java.util.Iterator;

public class ArrayIterator<T> implements Iterator<T> {
	T[] array;
	int i = 0;
	
	public ArrayIterator(T[] array) {
		this.array = array;
	}
	
	public boolean hasNext() {
		return i < array.length;
	}

	public T next() {
		return array[i++];
	}

	public void remove() {
	}
}
