package com.epic.framework.Ui;

import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.JLabel;

public class EpicNativeAdWidget extends EpicNativeWidget {

	public static final int TYPE_BANNER = 0;
	public static final int TYPE_IAB_BANNER = 1;
	public static final int TYPE_LEADERBOARD = 2;
	
	JLabel label = new JLabel("Ad Goes Here");
	
	public EpicNativeAdWidget(int ad_type) { 
		label.setBackground(Color.CYAN);
	}

	@Override
	JComponent getDesktopComponent() {
		return label;
	}

}
