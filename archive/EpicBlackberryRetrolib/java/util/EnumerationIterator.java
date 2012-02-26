package java.util;

import com.epic.framework.util.EpicFail;
import java.util.Enumeration;

public class EnumerationIterator<T> implements Iterator<T> {
	Enumeration enumeration;
	public EnumerationIterator(Enumeration enumeration) {
		this.enumeration = enumeration;
	}

	public boolean hasNext() {
		return enumeration.hasMoreElements();
	}

	public T next() {
		return (T)enumeration.nextElement();
	}

	public void remove() {
		throw EpicFail.not_implemented("Blackberry enumerations don't support the 'remove()' method.");
	}
}
