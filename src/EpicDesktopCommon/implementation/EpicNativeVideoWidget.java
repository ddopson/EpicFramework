package com.epic.framework.implementation

import javax.swing.JComponent;
import javax.swing.JPanel;

public class EpicNativeVideoWidget extends EpicNativeWidget {
	JComponent realComponent = new JPanel();
	public EpicNativeVideoWidget(String videoUrl, EpicClickListener epicClickListener) {
	}
	JComponent getDesktopComponent() {
		return realComponent;
	}
}
