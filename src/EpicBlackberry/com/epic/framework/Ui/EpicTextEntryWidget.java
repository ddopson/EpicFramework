package com.epic.framework.Ui;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.component.EditField;

public class EpicTextEntryWidget extends EpicNativeWidget {
	EditField field = new EditField();
	
	public String getText() {
		return field.getText();
	}

	public Field getBlackberryField() {
		return field;
	}

}
