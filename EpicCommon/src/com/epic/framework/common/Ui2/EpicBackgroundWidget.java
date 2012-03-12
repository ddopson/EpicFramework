package com.epic.framework.common.Ui2;

import com.epic.framework.build.EpicInflatableClass;
import com.epic.framework.common.Ui.EpicImage;
import com.epic.framework.common.Ui.EpicCanvas;

@EpicInflatableClass
public class EpicBackgroundWidget extends EpicWidget {
	EpicImage bitmap;
	public void onPaint(EpicCanvas canvas) {
		canvas.drawFullscreenBitmap(bitmap);
	}
}
