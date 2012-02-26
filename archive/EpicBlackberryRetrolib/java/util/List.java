package java.util;

public interface List<T> extends Collection<T> {
//	void add(int index, Object element);
//	boolean addAll(int index, Collection<T> c);
	T get(int index);
	int indexOf(T o);
	int lastIndexOf(T o);
//	ListIterator<T> listIterator();
//	ListIterator<T> listIterator(int index);
//	Object remove(int index);
//	Object set(int index, Object element);
//	List<T> subList(int fromIndex, int toIndex);
}
