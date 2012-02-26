package java.util;

import com.epic.framework.util.EpicFail;

public class LinkedList<T> extends ArrayList<T> implements Queue<T>,  List<T> {
// Note, this class is NOT efficient.  but for queues of 3 items, who cares?
	
	private T throwIfNull(T element) {
		if(element == null) {
			EpicFail.noSuchElement();
		}
		return element;	
	}
	
	public T element() {
		return throwIfNull( get(0) );
	}

	public boolean offer(T o) {
		this.add(o);
		return true;
	}

	public T peek() {
		return this.size() == 0 ? null : this.get(0);
	}

	public T poll() {
		return this.size() == 0 ? null : this.remove(0);
	}

	public T remove() {
		return throwIfNull( this.remove() );
	}

//	public void add(int index, Object element) {
//	}
//
//	public boolean addAll(int index, Collection<T> c) {
//	}
//
//	public int indexOf(Object o) {
//	}
//
//	public int lastIndexOf(Object o) {
//	}
//
//	public ListIterator<T> listIterator() {
//	}
//
//	public ListIterator<T> listIterator(int index) {
//	}
//
//	public List<T> subList(int fromIndex, int toIndex) {
//	}

}
