package java.util;

public interface Map<K, V> {
	public abstract V remove(K key);
	public abstract int hashCode();
	public abstract boolean containsValue(V value);
	public abstract boolean containsKey(K key);
	public abstract Set<K> keySet();
	public abstract Collection<V> values();
	public abstract void clear();
	public abstract int size();
	public abstract V put(K key, V value);
	public abstract V get(K key);
//	public abstract Set<Entry<K, V>> entrySet();
	public interface Entry<K,V> {
		K getKey();
		V getValue();
	}
}
