package java.util;

public interface Iterator<T> {
	boolean	hasNext();
	T next();
	void remove();
}
