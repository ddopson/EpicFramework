package com.epic.framework.Ui;

import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class EpicNativeListWidget extends EpicNativeWidget {
	ListView listView = new ListView(EpicApplication.getAndroidContext());
	private final int rowHeight;
	public ClickListener clickListener;

	View getAndroidView() {
		return listView;
	}

	private class RowView extends View {
		EpicRowType epicRowType;
		private int index;
		public RowView(EpicRowType epicRowType, int index) {
			super(EpicApplication.getAndroidContext());
			this.epicRowType = epicRowType;
			this.index = index;
		}
		
		protected void onDraw(Canvas canvas) {
			epicRowType.paintRow(EpicCanvas.get(canvas), index, 0, 0, this.getWidth(), this.getHeight());
		}

		protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
			this.setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), rowHeight);
//			int specMode = MeasureSpec.getMode(heightMeasureSpec);
//			int specSize = MeasureSpec.getSize(heightMeasureSpec);
//
//			int measuredHeight;
//			if (specMode == MeasureSpec.EXACTLY) {
//				measuredHeight = specSize;
//			} else {
//				if (specMode == MeasureSpec.AT_MOST) {
//					measuredHeight = Math.min(rowHeight, specSize);
//				}
//				else {
//					measuredHeight = rowHeight;
//				}
//			}
//			this.setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), measuredHeight);
		}
	}
	
	public interface ClickListener {
		public void onClick(int x, int y);
	}
	
	public EpicNativeListWidget(Object[] array, int rowHeight, final EpicRowType rowType, final EpicRowSelectListener rowSelectListener) {
		this.rowHeight = rowHeight;
		listView.setAdapter(
				new ArrayAdapter<Object>(EpicApplication.getAndroidContext(), -1, array) {
					public View getView(int position, View convertView, ViewGroup parentLayout) {
						if(convertView != null) {
							RowView rowView = (RowView)convertView;
							rowView.index = position;
							return rowView;
						}
						else {
							return new RowView(rowType, position);
						}
					}
				}
		);
		listView.setDrawingCacheEnabled(false);
		listView.setOnItemClickListener(
				new AdapterView.OnItemClickListener() {
					public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
						rowSelectListener.onSelectRow(position);
					}
				}
		);
		listView.setEnabled(true);
		listView.setFocusable(true);
		listView.setClickable(true);
		listView.setCacheColorHint(0);
		listView.setVerticalFadingEdgeEnabled(false);
		listView.setDividerHeight(0);
		listView.setFadingEdgeLength(0);
	}

	public static interface EpicRowType {
		public void paintRow(EpicCanvas epicCanvas, int index, int x, int y, int width, int height);
	}

	public static abstract class EpicRowSelectListener {
		public abstract void onSelectRow(int row);
	}

	public void setSelectedIndex(int index) {
		listView.setSelection(index);
	}

	public void setClickListener(ClickListener listener) {
		final ClickListener listener_copy = listener;
		listView.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				listener_copy.onClick(EpicPlatform.scaleRealToLogicallX((int) event.getX()), EpicPlatform.scaleRealToLogicalY((int) event.getY()));
				return false;
			}
		});
		
	}
	
	public int getSelectedIndex() {
		return listView.getSelectedItemPosition();
	}
}
