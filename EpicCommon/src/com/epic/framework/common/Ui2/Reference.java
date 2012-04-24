package com.epic.framework.common.Ui2;

public class Reference<T> {
	public final String path;
	public Reference(String path) {
		this.path = path;
	}
	public T get() {
		Object o = Registry.get(path);
		return (T)o;
	}
}
