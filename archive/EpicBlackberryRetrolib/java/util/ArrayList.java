package java.util;

import java.util.Vector;
import java.util.Iterator;

public class ArrayList<T> extends CollectionBase<T> implements List<T> {
	Vector vector = null;

	public ArrayList() {
		vector = new Vector();
	}

	public ArrayList(int size) {
		vector = new Vector(size);
	}

	public boolean add(T element) {
		vector.addElement(element);
		return true;
	}

	public void clear() {
		vector.removeAllElements();
	}

	public boolean remove(Object element) {
		return vector.removeElement(element);
	}

	public T remove(int position) {
		T element = (T) vector.elementAt(position);
		vector.removeElementAt(position);
		return element;
	}

	public T get(int position) {
		return (T) vector.elementAt(position);
	}

	public void set(int position, T element) {
		vector.setElementAt(element, position);
	}

	private class ArrayListIterator implements Iterator<T> {
		int i = 0;
		public boolean hasNext() {
			return i < ArrayList.this.vector.size();
		}
		public T next() {
			return ArrayList.this.get(i++);
		}
		public void remove() {
			ArrayList.this.remove(i-1);
		}
	}

	public Iterator<T> iterator() {
		return new ArrayListIterator();
	}

	public boolean contains(Object entityType) {
		return vector.contains(entityType);
	}

	public void ensureCapacity(int capacity) {
		vector.ensureCapacity(capacity);
	}

	public int size() {
		return vector.size();
	}

	public boolean isEmpty() {
		return vector.isEmpty();
	}

	public int hashCode() {
		return vector.hashCode();
	}

	public int indexOf(T o) {
		for(int i = 0; i < vector.size(); i++) {
			if(vector.elementAt(i).equals(o)) {
				return i;
			}
		}
		return -1;
	}

	public int lastIndexOf(T o) {
		for(int i = vector.size() - 1; i >= 0; i--) {
			if(vector.elementAt(i).equals(o)) {
				return i;
			}
		}
		return -1;
	}
}
