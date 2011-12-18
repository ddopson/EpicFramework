package java.util;

public interface Queue<T> extends Collection<T> {
	T element();
	boolean offer(T o);
	T peek();
	T poll();
	T remove();
}
