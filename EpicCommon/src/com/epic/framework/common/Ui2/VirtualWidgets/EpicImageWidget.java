package com.epic.framework.common.Ui2.VirtualWidgets;

import com.epic.framework.common.EpicInflatableClass;
import com.epic.framework.common.Ui.EpicImage;
import com.epic.framework.common.Ui.EpicCanvas;
import com.epic.framework.common.Ui2.EpicVirtualWidget;

@EpicInflatableClass
public class EpicImageWidget extends EpicVirtualWidget {
	EpicImage image;
	
	public void onPaint(EpicCanvas canvas) {
		canvas.drawBitmap(image, x, y, width, height);
	}
}
