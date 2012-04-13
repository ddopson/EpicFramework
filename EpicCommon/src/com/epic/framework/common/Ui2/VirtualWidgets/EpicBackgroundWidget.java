package com.epic.framework.common.Ui2.VirtualWidgets;

import com.epic.framework.common.EpicInflatableClass;
import com.epic.framework.common.Ui.EpicImage;
import com.epic.framework.common.Ui.EpicCanvas;
import com.epic.framework.common.Ui2.EpicVirtualWidget;

@EpicInflatableClass(ignoreSuperclass=true)
public class EpicBackgroundWidget extends EpicVirtualWidget {
	EpicImage bitmap;
	public void onPaint(EpicCanvas canvas) {
		canvas.drawFullscreenBitmap(bitmap);
	}
}
