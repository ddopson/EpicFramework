package com.epic.framework.implementation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;

import javax.swing.Action;
import javax.swing.JButton;

import com.epic.framework.common.Ui2.NativeWidgets.EpicNativeButtonWidget;

public class EpicNativeButtonWidgetImplementation {
	public static Object createNativeButton(final EpicNativeButtonWidget epicNativeButtonWidget) {
		JButton button = new JButton(epicNativeButtonWidget.text);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(epicNativeButtonWidget.onClick != null) {
					epicNativeButtonWidget.onClick.run();
				}
			}
		});
		return button;
	}
}
