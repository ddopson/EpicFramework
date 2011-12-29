package com.epic.framework.implementation;

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.epic.framework.common.Ui.EpicCanvas;
import com.epic.framework.common.util.EpicLog;


@SuppressWarnings("serial")
public class EpicNativeListWidget extends EpicNativeWidget {
	private final JList nativeListWidget;
	private final EpicRowType epicRowType;
	
	private class EpicCellRenderer extends JPanel implements ListCellRenderer {
		int index;
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			this.index = index;
			return this;
		}
		public void paint(Graphics g) {
//			EpicLog.d("Painting Row[" + index + "] - " + this.getWidth() + "x" + this.getHeight());
			epicRowType.paintRow(EpicCanvas.get(g), index, 0, 0, this.getWidth(), this.getHeight());
		}
	};
		
	public EpicNativeListWidget(Object[] array, int rowHeight, final EpicRowType epicRowType, final EpicRowSelectListener rowSelectListener) {
		EpicLog.d("Newing up a list widget.  array.length=" + array.length + ", rowHeight=" + rowHeight);
		this.nativeListWidget = new JList(array);
		this.epicRowType = epicRowType;
		nativeListWidget.setFixedCellHeight(rowHeight);
		nativeListWidget.setCellRenderer(new EpicCellRenderer());
		nativeListWidget.setVisibleRowCount(array.length);
		nativeListWidget.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				rowSelectListener.onSelectRow(e.getFirstIndex());
			}
		});
		nativeListWidget.setVisible(true);
		nativeListWidget.setFocusable(true);
		nativeListWidget.setEnabled(true);
	}

	public static interface EpicRowType {
		public void paintRow(EpicCanvas epicCanvas, int index, int x, int y, int width, int height);
	}
	
	public interface ClickListener {
		public void onClick(int x, int y);
	}

	public static abstract class EpicRowSelectListener {
		public abstract void onSelectRow(int row);
	}
	
	public void setClickListener(ClickListener listener) {
		// TODO: this is just broken in so many ways.  Really, we need to finally port the list crap out of native UI
	}

	JComponent getDesktopComponent() {
			return nativeListWidget;
	}

	public int getSelectedIndex() {
		return nativeListWidget.getSelectedIndex();
	}

	public void setSelectedIndex(int index) {
		nativeListWidget.setSelectedIndex(index);
	}
}
