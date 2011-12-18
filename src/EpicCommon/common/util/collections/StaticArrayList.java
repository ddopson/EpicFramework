package com.epic.framework.common.util.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.epic.framework.common.util.EpicFail;

public class StaticArrayList<T> extends CollectionBase<T> implements List<T> {
	protected final T[] elements;
	protected int size;
	public StaticArrayList(T[] array, int size) {
		this.elements = array;
		this.size = size;
	}
	public boolean add(T o) {
		elements[size++] = o;
		return true;
	}

	public void clear() {
		size = 0;
	}

	public boolean contains(Object o) {
		for(int i = 0; i < size; i++) {
			if(elements[i] == o) {
				return true;
			}
		}
		return false;
	}
	
	private class StaticArrayIterator implements Iterator<T> {
		private int i = 0;
		public boolean hasNext() {
			return i < size;
		}

		public T next() {
			return elements[i++];
		}

		public void remove() {
			throw EpicFail.not_implemented();
		}
	}
	
	public Iterator<T> iterator() {
		return new StaticArrayIterator();
	}
	
	public int size() {
		return size;
	}

	public T get(int i) {
		if(i > size) {
			throw new IndexOutOfBoundsException("Index " + i + " is greater than array length of " + size);
		}
		return elements[i];
	}
	public T getLast() {
		return size > 0 ? get(size - 1) : null;
	}


	public boolean remove(Object o) {
		throw EpicFail.not_implemented();
	}
	
	public void add(int arg0, T arg1) {
		throw EpicFail.not_implemented();
	}
	
	public boolean addAll(int arg0, Collection<? extends T> arg1) {
		throw EpicFail.not_implemented();
	}
	
	public int indexOf(Object o) {
		for(int i = 0; i < size; i++) {
			if(elements[i] == o) {
				return i;
			}
		}	
		
		return -1;
	}

	public int lastIndexOf(Object o) {
		throw EpicFail.not_implemented();
	}

	public ListIterator<T> listIterator() {
		throw EpicFail.not_implemented();
	}

	public ListIterator<T> listIterator(int arg0) {	
		throw EpicFail.not_implemented();
	}

	public T remove(int i) {
		throw EpicFail.not_implemented();
	}

	public T set(int i, T o) {
		throw EpicFail.not_implemented();
	}

	public List<T> subList(int arg0, int arg1) {
		throw EpicFail.not_implemented();
	}
}
