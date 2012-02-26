package com.epic.framework.Ui;

import net.rim.device.api.ui.Field;

public class EpicNativeAdWidget extends EpicNativeWidget {
	public static final int TYPE_BANNER = 0;
	public static final int TYPE_IAB_BANNER = 1;
	public static final int TYPE_LEADERBOARD = 2;
	
	Field field;
	
	public EpicNativeAdWidget(int size) {
		
	}

	Field getBlackberryField() {
		return field;
	}

}
