package com.epic.framework.types;

public class Ref <T> {
	public T value;
	public Ref() {
		this.value = null;
	}
	public Ref(T value) {
		this.value = value;
	}
	public void set(T value) {
		this.value = value;
	}
	public T get() {
		return value;
	}
}
