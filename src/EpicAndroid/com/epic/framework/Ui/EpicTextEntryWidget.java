package com.epic.framework.Ui;

import android.view.View;
import android.widget.EditText;

public class EpicTextEntryWidget extends EpicNativeWidget {

	EditText text = new EditText(EpicAndroidActivity.getCurrentAndroidActivity());
	
	View getAndroidView() {
		return text;
	}
	
	public String getText() {
		return text.getText().toString();
	}

}
