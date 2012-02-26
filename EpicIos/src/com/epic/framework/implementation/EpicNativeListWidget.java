package com.epic.framework.implementation;

import com.epic.framework.common.Ui.EpicCanvas;

public class EpicNativeListWidget extends EpicNativeWidget {
	public EpicNativeListWidget(Object[] array, int rowHeight, final EpicRowType rowType, final EpicRowSelectListener rowSelectListener) { }

	public static abstract class EpicRowType {
		public abstract void paintRow(EpicCanvas canvas, int position, int x, int y, int width, int height);
	}

	public interface ClickListener {
		public void onClick(int x, int y);
	}

	public void setClickListener(ClickListener listener) {
	}

	public static abstract class EpicRowSelectListener {
		public abstract void onSelectRow(int row);
	}

	public int getSelectedIndex() {
		return 0;
	}

	public void setSelectedIndex(int index) {
	}
}
