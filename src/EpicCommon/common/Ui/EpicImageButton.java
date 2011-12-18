//package com.epic.framework.common.Ui;
//
//import com.epic.framework.common.util.EpicSoundManager;
//import com.epic.framework.common.util.StringHelper;
//
//public class EpicImageButton extends EpicAbstractButton {
//	
//	private static EpicSound defaultButtonClickSound = null;
//	EpicClickListener action;
//	EpicBitmap bitmap_default;
//	EpicBitmap bitmap_focused;
//	EpicBitmap bitmap_pressed;
//	public boolean focused;
//	String text;
//	int lp;
//	int rp;
//	int tp;
//	int bp;
//	
//	private boolean isPreloaded = false;
//
//	public EpicImageButton(EpicBitmap defaultImage, EpicClickListener action) {
//		this(defaultImage, null, 0,0,0,0, action);
//	}
//	
//	public EpicImageButton(EpicBitmap defaultImage, String text, int lp, int tp, int rp, int bp, EpicClickListener action) {
//		String baseName = StringHelper.stripSuffix(defaultImage.getName(), "_default");
//		bitmap_default = EpicBitmap.lookupByName(baseName + "_default");
//		bitmap_focused = EpicBitmap.lookupByName(baseName + "_pressed");
//		bitmap_pressed = EpicBitmap.lookupByName(baseName + "_pressed");
//		this.action = action;
//		this.text = text;
//		this.lp = lp;
//		this.tp = tp;
//		this.rp = rp;
//		this.bp = bp;
//	}
//
//	public void onDraw(EpicCanvas canvas) {
//		
//		if(!isPreloaded) {
////			if(bitmap_default != null) {
////				bitmap_default.preloadSoon(this.getWidth(), this.getHeight());
////			}
//			if(bitmap_focused != null) {
//				bitmap_focused.preloadSoon(this.getWidth(), this.getHeight());
//			}
//			if(bitmap_pressed != null) {
//				bitmap_pressed.preloadSoon(this.getWidth(), this.getHeight());
//			}
//			isPreloaded = true;
//		}
////		String state;
////		if(this.isPressed()) {
////			state = "isPressed";
////		}
////		else if (this.isFocused()) {
////			state = "isFocused";
////		}
////		else {
////			state = "isDefault";
////		}
//		//		EpicLog.d("EpicImageButton.onDraw('" + state + "', " + this.getWidth() + ", " + this.getHeight() + ")");
//
//		if(this.isPressed() && EpicImageButton.this.bitmap_pressed != null) {
//			canvas.absolute.drawBitmap(this.bitmap_pressed, 0, 0, this.getWidth(), this.getHeight());
//		}
//		else if ((this.isFocused() || focused) && EpicImageButton.this.bitmap_focused != null) {
//			canvas.absolute.drawBitmap(this.bitmap_focused, 0, 0, this.getWidth(), this.getHeight());
//		}
//		else {
//			canvas.absolute.drawBitmap(this.bitmap_default, 0, 0, this.getWidth(), this.getHeight());
//		}
//		
//		if(text != null) {
//			EpicFont font = EpicFont.FONT_MAIN.findBestFittingFont("All Time", this.getWidth() - lp - rp, this.getHeight() - tp - bp);
//			canvas.scaled.drawText(text, 0, 0, this.getWidth(), this.getHeight(), font, focused ? EpicColor.RED : EpicColor.DKGRAY, EpicFont.HALIGN_CENTER, EpicFont.VALIGN_CENTER);
//		}
//		
//		focused = false;
//	}
//
//	void onClick() {
//		if(EpicImageButton.defaultButtonClickSound != null) {
//			EpicSoundManager.playSound(EpicImageButton.defaultButtonClickSound);
//		}
//		synchronized (EpicPlatform.getSingleThreadingLock()) {			
//			this.action.onClick();
//		}
//	}
//
//	public static void setDefaultClickSound(EpicSound soundButtonClick) {
//		defaultButtonClickSound = soundButtonClick;
//	}
//}
