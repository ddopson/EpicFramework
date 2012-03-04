package com.epic.framework.common.Ui2;

import com.epic.framework.build.EpicInflatableClass;
import com.epic.framework.common.Ui.EpicBitmap;
import com.epic.framework.common.Ui.EpicCanvas;

@EpicInflatableClass
public class EpicButtonWidget extends EpicWidget implements EpicWidget.Clickable {
	EpicAction action;
	EpicBitmap bitmap_default;
	EpicBitmap bitmap_focused;
	EpicBitmap bitmap_pressed;
	static EpicBitmap foo;
	public void onPaint(EpicCanvas canvas) {
		EpicBitmap bitmap = bitmap_default;
		canvas.drawBitmap(bitmap, x, y, width, height);
	}
	
	@Override
	public void onClick(int x, int y) {
		if(action != null) {
			action.run();
		}
	}
}
