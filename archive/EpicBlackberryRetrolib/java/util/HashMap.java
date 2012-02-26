package java.util;

import java.util.Hashtable;
import java.util.CollectionBase;
import com.epic.framework.util.EpicFail;

public class HashMap<K, V> implements Map<K, V> {
	Hashtable hashtable = new Hashtable();
	public V get(K key) {
		return (V)hashtable.get(key);
	}

	public V put(K key, V value) {
		return (V) hashtable.put(key, value);
	}

	public int size() {
		return hashtable.size();
	}

	public void clear() {
		hashtable.clear();
	}

	public boolean containsKey(K key) {
		return hashtable.containsKey(key);
	}

	public boolean containsValue(V value) {
		return hashtable.contains(value);
	}

	public int hashCode() {
		return hashtable.hashCode();
	}

	public V remove(K key) {
		return (V)hashtable.remove(key);
	}
	
	private class HashMapKeySet extends CollectionBase<K> implements Set<K> {
		public boolean add(K o) {
			throw EpicFail.not_supported();
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

		public Iterator<K> iterator() {
			return new EnumerationIterator<K>(hashtable.keys());
		}

		public boolean remove(Object o) {
			if(hashtable.containsKey(o)) {
				hashtable.remove(o);
				return true;
			}
			return false;
		}

		public int size() {
			return hashtable.size();
		}
	}
	
	public Set<K> keySet() {
		return new HashMapKeySet();
	}
	
	private class HashMapValuesCollection extends CollectionBase<V> {
		public boolean add(V o) {
			throw EpicFail.not_supported();
		}

		public void clear() {
			hashtable.clear();
		}

		public boolean contains(Object o) {
			return hashtable.contains(o);
		}

		public boolean isEmpty() {
			return hashtable.isEmpty();
		}

		public Iterator<V> iterator() {
			return new EnumerationIterator<V>(hashtable.elements());
		}

		public boolean remove(Object o) {
//			if(hashtable.contains(o)) {
				// this doesn't work easily.  Hard to look-up an element by value
				throw EpicFail.not_implemented();
//			}
//			return false;
		}

		public int size() {
			return hashtable.size();
		}
	}

	public Collection<V> values() {
		return new HashMapValuesCollection();
	}

//	public Set<java.util.Map.Entry<K, V>> entrySet() {
//		hashtable.DD
//		// TODO Auto-generated method stub
//		return null;
//	}

	// TODO: keySet, values, entrySet - requires shimming other java.util interfaces
}
