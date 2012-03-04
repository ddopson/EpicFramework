package com.epic.framework.common.Ui2;

import com.epic.framework.build.EpicInflatableClass;
import com.epic.framework.common.Ui.EpicBitmap;
import com.epic.framework.common.Ui.EpicCanvas;

@EpicInflatableClass
public class EpicBackgroundWidget extends EpicWidget {
	EpicBitmap bitmap;
	public void onPaint(EpicCanvas canvas) {
		canvas.drawFullscreenBitmap(bitmap);
	}
}
