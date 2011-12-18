package com.epic.framework.Ui;


import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;

import com.epic.framework.util.EpicLog;
import com.epic.framework.util.StringHelper;

public abstract class EpicAbstractButton extends EpicNativeWidget {
	private EpicDesktopButton realButton = new EpicDesktopButton();

	public EpicAbstractButton() { }

	JComponent getDesktopComponent() {
		return realButton;
	}

	abstract void onDraw(EpicCanvas canvas);
	abstract void onClick();

	private static EpicDesktopButton hack = null;
	class EpicDesktopButton extends JButton implements ActionListener {
		private static final long serialVersionUID = 1L;
		public EpicDesktopButton() {
			this.addActionListener(this);
		}
		public void paint(Graphics graphics) {
//			if(hack == null || hack == this) {
//				EpicLog.logStack();
//				hack = this;
//			}
			EpicLog.d("EpicDesktopButton.paint() " + this + " = " + this.getWidth() + "x" + this.getHeight());
			EpicAbstractButton.this.onDraw(EpicCanvas.get(graphics));
//			this.setActionCommand("click");
		}
		public void actionPerformed(ActionEvent e) {
			EpicLog.d("EpicDesktopButton.click()");
			EpicAbstractButton.this.onClick();
		}
		
	}
}
