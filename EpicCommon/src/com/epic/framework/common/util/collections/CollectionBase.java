package com.epic.framework.common.util.collections;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import com.epic.framework.common.util.EpicFail;

public abstract class CollectionBase<T> implements Collection<T>{
	public boolean addAll(Collection<? extends T> c) {
		boolean changed = false;
		Iterator<? extends T> iterator = c.iterator();
		while(iterator.hasNext()) {
			T o = iterator.next();
			if(add(o)) {
				changed = true;
			}
		}
		return changed;
	}

	public boolean containsAll(Collection<?> c) {
		Iterator<?> iterator = c.iterator();
		while(iterator.hasNext()) {
			Object o = iterator.next();
			if(! contains(o)) {
				return false;
			}
		}
		return true;
	}

	public boolean removeAll(Collection<?> c) {
		boolean changed = false;
		Iterator<?> iterator = c.iterator();
		while(iterator.hasNext()) {
			Object o = iterator.next();
			if(remove(o)) {
				changed = true;
			}
		}
		return changed;
	}

	public boolean retainAll(Collection<?> c) {
		HashSet<T> toDelete = new HashSet<T>();
		Iterator<T> iterator = this.iterator();
		while(iterator.hasNext()) {
			T o = iterator.next();
			if(! c.contains(o)) {
				toDelete.add(o);
			}
		}

		boolean changed = false;
		Iterator<T> iterator2 = toDelete.iterator();
		while(iterator2.hasNext()) {
			T o = iterator2.next();
			this.remove(o);
			changed = true;
		}

		return changed;
	}
	
	public Object[] toArray() {
		Object[] array = new Object[size()];
		int i = 0;
		Iterator<T> iterator = this.iterator();
		while(iterator.hasNext()) {
			T o = iterator.next();
			array[i++] = o;
		}
		return array;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <A> A[] toArray(A[] array) {
		if(array.length != size()) {
			// Blackberry doesn't support dynamically creating arrays
			throw EpicFail.invalid_argument("ArrayList.toArray() called with an array that only has " + array.length + " elements.  expected " + size() + " elements");
		}
		int i = 0;
		Iterator<T> iterator = this.iterator();
		while(iterator.hasNext()) {
			T o = iterator.next();
			array[i++] = (A)o;
		}
		return array;
	}

	public boolean isEmpty() {
		return this.size() > 0;
	}
}
