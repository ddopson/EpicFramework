package com.epic.framework.Ui;

import android.view.View;

public class EpicAdWidget extends EpicWidgetBase {
	@Override
	View getAndroidView() {
		throw new RuntimeException("Ad Library NOT linked in.  Should not be calling _ANY_ Ad API's!!");
	}

	public EpicAdWidget(EpicScreen context, int backgroundColor, int primaryTextColor, int secondaryTextColor) {
		throw new RuntimeException("Ad Library NOT linked in.  Should not be calling _ANY_ Ad API's!!");
	}
}
