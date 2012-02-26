package com.epic.framework.Ui;

import com.admob.android.ads.AdManager;
import com.admob.android.ads.AdView;

import android.view.View;

public class EpicAdWidget extends EpicWidgetBase {
	AdView adView;
	static {
		AdManager.setTestDevices(new String[] { AdManager.TEST_EMULATOR });
	}

	@Override
	View getAndroidView() {
		return adView;
	}

	public EpicAdWidget(EpicScreen context, int backgroundColor, int primaryTextColor, int secondaryTextColor) {
		adView = new AdView(context.getAndroidActivity());
		adView.setBackgroundColor(backgroundColor);
		adView.setPrimaryTextColor(primaryTextColor);
		adView.setSecondaryTextColor(secondaryTextColor);
	}
}
