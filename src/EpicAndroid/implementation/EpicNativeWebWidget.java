package com.epic.framework.implementation;

import android.view.View;
import android.webkit.WebView;

public class EpicNativeWebWidget extends EpicNativeWidget {
	WebView webView = new WebView(EpicApplication.getAndroidContext());
	@Override
	View getAndroidView() {
		return webView;
	}

	public void loadUrl(String url) {
		webView.loadUrl(url);
	}
}
