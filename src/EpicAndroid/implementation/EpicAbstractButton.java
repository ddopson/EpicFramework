package com.epic.framework.implementation;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.StateListDrawable;
import android.view.View;
import android.widget.Button;

import com.epic.framework.common.Ui.EpicCanvas;

public abstract class EpicAbstractButton extends EpicNativeWidget {
	private EpicAndroidButton realButton = new EpicAndroidButton(EpicApplication.getAndroidContext());

	public EpicAbstractButton() { }

	protected void playClickSound() {
//		EpicSoundManager.playSound(EpicSounds.sound_button_click);
	}

	View getAndroidView() {
		return realButton;
	}

	abstract void onDraw(EpicCanvas canvas);
	abstract void onClick();

	class EpicAndroidButton extends Button {
		public EpicAndroidButton(Context context) {
			super(context);
			StateListDrawable stateList = new StateListDrawable() {
				protected boolean onStateChange(int[] stateSet) {
					EpicAndroidButton.this.invalidate();
					return true;
				}
			};
			this.setBackgroundDrawable(stateList);
			this.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					EpicAbstractButton.this.onClick();
				}
			});
		}

		public void draw(Canvas canvas) {
			EpicAbstractButton.this.onDraw(EpicCanvas.get(canvas));
		}
	}
}
