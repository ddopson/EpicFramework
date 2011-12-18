package com.epic.framework.Ui;

import javax.swing.JComponent;
import javax.swing.JLabel;

public class EpicTextEntryWidget extends EpicNativeWidget {

	JLabel label = new JLabel("This is a text entry field.");
	
	JComponent getDesktopComponent() {
		return label;
	}
	
	public String getText() {
		return "fake_text";
	}

}
