package com.epic.framework.Ui;

public interface EpicClickListener {
	abstract void onClick();
	public static EpicClickListener NoOp = new EpicClickListener() {
		public void onClick() { }
	};
}
