package com.epic.framework.implementation

import javax.swing.JComponent;
import javax.swing.JPanel;


public class EpicNativeWebWidget extends EpicNativeWidget {
	JPanel panel = new JPanel();
	public void loadUrl(String url) {
	}

	JComponent getDesktopComponent() {
		return panel;
	}
}
