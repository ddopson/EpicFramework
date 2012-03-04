package com.example.HelloUIv2;

import com.epic.framework.common.Ui.EpicCanvas;
import com.epic.framework.common.Ui.EpicColor;
import com.epic.framework.build.EpicInflatableClass;
import com.epic.framework.common.Ui2.EpicWidget;

@EpicInflatableClass
public class SampleWidget extends EpicWidget {
	int size;
	
	@Override
	public void onPaint(EpicCanvas canvas) {
		canvas.applyFullscreenFill(EpicColor.YELLOW);
		canvas.drawCircle(EpicColor.RED, 255, 400, 200, size);
		ClassSampleWidget.register();
	}
}