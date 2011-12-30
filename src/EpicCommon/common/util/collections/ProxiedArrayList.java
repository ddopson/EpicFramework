package com.epic.framework.common.util.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ProxiedArrayList<T> implements List<T> {
	ArrayList<T> proxy = new ArrayList<T>();
	
	@Override
	public int size() {
		return proxy.size();
	}

	@Override
	public boolean isEmpty() {
		return proxy.isEmpty();
	}

	@Override
	public boolean contains(Object paramObject) {
		return proxy.contains(paramObject);
	}

	@Override
	public Iterator<T> iterator() {
		return proxy.iterator();
	}

	@Override
	public Object[] toArray() {
		return proxy.toArray();
	}

	@Override
	public <T> T[] toArray(T[] paramArrayOfT) {
		return proxy.toArray(paramArrayOfT);
	}

	@Override
	public boolean add(T paramE) {
		return proxy.add(paramE);
	}

	@Override
	public boolean remove(Object paramObject) {
		return proxy.remove(paramObject);
	}

	@Override
	public boolean containsAll(Collection<?> paramCollection) {
		return proxy.containsAll(paramCollection);
	}

	@Override
	public boolean addAll(Collection<? extends T> paramCollection) {
		return proxy.addAll(paramCollection);
	}

	@Override
	public boolean addAll(int paramInt, Collection<? extends T> paramCollection) {
		return proxy.addAll(paramCollection);
	}

	@Override
	public boolean removeAll(Collection<?> paramCollection) {
		return proxy.removeAll(paramCollection);
	}

	@Override
	public boolean retainAll(Collection<?> paramCollection) {
		return proxy.retainAll(paramCollection);
	}

	@Override
	public void clear() {
		proxy.clear();
	}

	@Override
	public T get(int paramInt) {
		return proxy.get(paramInt);
	}

	@Override
	public T set(int paramInt, T paramE) {
		return proxy.set(paramInt, paramE);
	}

	@Override
	public void add(int paramInt, T paramE) {
		proxy.add(paramInt, paramE);
	}

	@Override
	public T remove(int paramInt) {
		return proxy.remove(paramInt);
	}

	@Override
	public int indexOf(Object paramObject) {
		return proxy.indexOf(paramObject);
	}

	@Override
	public int lastIndexOf(Object paramObject) {
		return proxy.lastIndexOf(paramObject);
	}

	@Override
	public ListIterator<T> listIterator() {
		return proxy.listIterator();
	}

	@Override
	public ListIterator<T> listIterator(int paramInt) {
		return proxy.listIterator(paramInt);
	}

	@Override
	public List<T> subList(int paramInt1, int paramInt2) {
		return proxy.subList(paramInt1, paramInt2);
	}

}
