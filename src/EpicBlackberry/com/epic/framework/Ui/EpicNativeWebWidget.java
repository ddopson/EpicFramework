package com.epic.framework.Ui;

import net.rim.device.api.browser.field2.BrowserField;
import net.rim.device.api.ui.Field;

public class EpicNativeWebWidget extends EpicNativeWidget {
	BrowserField webView = new BrowserField();

	public void loadUrl(String url) {
		throw new RuntimeException("Need to implement load url");
	}

	Field getBlackberryField() {
		return webView;
	}
}
