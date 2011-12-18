package com.epic.framework.Ui;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.TouchEvent;
import net.rim.device.api.ui.component.ListField;
import net.rim.device.api.ui.component.ListFieldCallback;
import net.rim.device.api.ui.container.VerticalFieldManager;

public class EpicNativeListWidget extends EpicNativeWidget {
	EpicBlackberryListField listField;
	EpicRowSelectListener rowSelectListener;
	String[] options;
	public ClickListener clickListener;
		
	EpicRowType rowType;

	VerticalFieldManager fm = new VerticalFieldManager(VerticalFieldManager.VERTICAL_SCROLL);
	
	public EpicNativeListWidget(Object[] array, int rowHeight, final EpicRowType rowType, final EpicRowSelectListener rowSelectListener) {
		listField = new EpicBlackberryListField(array.length);
		options = (String[]) array;
		this.rowSelectListener = rowSelectListener;
		fm.add(listField);
		this.rowType = rowType;
		listField.setRowHeight(rowHeight);
	}

	public static abstract class EpicRowType {
		public abstract void paintRow(EpicCanvas canvas, int position, int x, int y, int width, int height);
	}

	public interface ClickListener {
		public void onClick(int x, int y);
	}

	public void setClickListener(ClickListener listener) {
		this.clickListener = listener;
	}

	public static abstract class EpicRowSelectListener {
		public abstract void onSelectRow(int row);
	}

	public int getSelectedIndex() {
		return listField.getSelectedIndex();
	}

	public void setSelectedIndex(int index) {
		listField.setSelectedIndex(index);
	}

	Field getBlackberryField() {
//		return this.listField;
		return this.fm;
	}

	class EpicBlackberryListField extends ListField {
		private void tryFireEvent() {
			if(getSelectedIndex() >= 0) {
				EpicNativeListWidget.this.rowSelectListener.onSelectRow(getSelectedIndex());
			}
		}
		protected boolean touchEvent(TouchEvent message) {
			boolean ret = super.touchEvent(message);
			tryFireEvent();
			return ret;
		}
		protected boolean navigationClick(int status, int time) {
			boolean ret = super.navigationClick(status, time);
			tryFireEvent();
			return ret;
		}
		public EpicBlackberryListField(int size) {
			super(size);
			this.setChangeListener(new FieldChangeListener() {
				public void fieldChanged(Field field, int context) {
					EpicBlackberryListField.this.tryFireEvent();
				}
			});

			this.setCallback(new ListFieldCallback() {
				public int indexOfList(ListField listField, String prefix, int start) {
					for(int i = 0; i < EpicNativeListWidget.this.options.length; i++) {
						if(EpicNativeListWidget.this.options[i].startsWith(prefix)) {
							return i;
						}
					}
					return listField.getSelectedIndex();
				}

				public int getPreferredWidth(ListField listField) {
					return 300;
				}

				public Object get(ListField listField, int index) {
					return EpicNativeListWidget.this.options[index];
				}

				public void drawListRow(ListField listField, Graphics graphics, int index, int y, int width) {
					rowType.paintRow(EpicCanvas.get(graphics), index, 0, y, width, EpicBlackberryListField.this.getRowHeight());
				}
			});
		}
	}
}
