package com.epic.framework.util;

public class Product {
	String name;
	String sku;
	public String title;
	public String priceString;
	public int tokens;
	
	public Product(String sku, String name, String title, String priceString, int tokens) {
		this.name = name;
		this.sku = sku;
		this.title = title;
		this.priceString = priceString;
		this.tokens = tokens;
	}
}
