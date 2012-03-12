package com.epic.framework.common.Ui2;

import com.epic.framework.build.EpicInflatableClass;
import com.epic.framework.common.Ui.EpicImage;
import com.epic.framework.common.Ui.EpicCanvas;

@EpicInflatableClass
public class EpicButtonWidget extends EpicWidget implements EpicWidget.Clickable {
	EpicAction action;
	EpicImage bitmap_default;
	EpicImage bitmap_focused;
	EpicImage bitmap_pressed;
	static EpicImage foo;
	public void onPaint(EpicCanvas canvas) {
		EpicImage bitmap = bitmap_default;
		canvas.drawBitmap(bitmap, x, y, width, height);
	}
	
	@Override
	public void onClick(int x, int y) {
		if(action != null) {
			action.run();
		}
	}
}
