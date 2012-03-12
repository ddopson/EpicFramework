package com.epic.framework.common.Ui2;

import com.epic.framework.common.Ui.EpicImage;
import com.epic.framework.common.Ui.EpicCanvas;

public class EpicImageWidget extends EpicWidget {
	EpicImage image;
	
	public void onPaint(EpicCanvas canvas) {
		canvas.drawBitmap(image, x, y, width, height);
	}
}
