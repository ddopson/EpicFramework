package java.util;

import java.util.Hashtable;

public class HashSet<T> extends CollectionBase<T> implements Set<T> {
	Hashtable hashtable = null;
	private static final Object PRESENT = new Object();

	public HashSet() {
		hashtable = new Hashtable();
	}
	
	public HashSet(int initialCapacity) {
		hashtable = new Hashtable(initialCapacity);
	}

	public HashSet(Collection<T> c) {
		hashtable = new Hashtable(c.size());
		Iterator<?> iterator = c.iterator();
		while(iterator.hasNext()) {
			Object o = iterator.next();
			hashtable.put(o, PRESENT);
		}
	}

	public boolean add(T o) {
		Object prev = hashtable.put(o, PRESENT);
		return (prev == null);
	}

	public void clear() {
		hashtable.clear();
	}

	public boolean contains(Object o) {
		return hashtable.containsKey(o);
	}

	public boolean isEmpty() {
		return hashtable.isEmpty();
	}

	public Iterator<T> iterator() {
		return new EnumerationIterator<T>(hashtable.keys());
	}

	public boolean remove(Object o) {
		return hashtable.remove(o) == PRESENT;
	}

	public int size() {
		return hashtable.size();
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
	
	public boolean retainAll(Collection<?> c) {
		Hashtable newHash = new Hashtable();
		Iterator<?> iterator = c.iterator();
		while(iterator.hasNext()) {
			Object o = iterator.next();
			if(contains(o)) {
				newHash.put(o, PRESENT);
			}
		}
		boolean same = (newHash.size() == hashtable.size());
		this.hashtable = newHash;
		return !same;
	}
}
