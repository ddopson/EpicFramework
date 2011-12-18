package java.util;

public interface ListIterator<T> extends Iterator<T> {
	void add(Object o);
	boolean hasNext();
	boolean hasPrevious();
	T next();
	int nextIndex();
	T previous();
	int previousIndex();
	void remove();
	void set(Object o);
}
