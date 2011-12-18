package java.util;

import java.lang.Iterable;
import java.util.Iterator;

public interface Collection<T> extends Iterable<T> {
	boolean add(T o);
	boolean addAll(Collection<? extends T> c);
	void clear();
	boolean contains(Object o);
	boolean containsAll(Collection<?> c);
	boolean equals(Object o);
	int hashCode();
	boolean isEmpty();
	Iterator<T> iterator();
	boolean remove(Object o);
	boolean removeAll(Collection<?> c);
	boolean retainAll(Collection<?> c);
	int size();
	Object[] toArray();
	<A> A[] toArray(A[] a);
}
