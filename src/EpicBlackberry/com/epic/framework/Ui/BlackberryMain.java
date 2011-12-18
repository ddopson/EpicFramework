package com.epic.framework.Ui;

import java.util.Timer;
import java.util.TimerTask;

import net.rim.device.api.system.Backlight;
import net.rim.device.api.system.Display;
import net.rim.device.api.system.KeyListener;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.FontFamily;
import net.rim.device.api.ui.FontManager;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Keypad;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.TouchEvent;
import net.rim.device.api.ui.Ui;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.Menu;
import net.rim.device.api.ui.container.MainScreen;

import com.epic.cfg.EpicProjectConfig;
import com.epic.framework.Ui.EpicMenu.EpicMenuItem;
import com.epic.framework.Ui.EpicPercentLayout.LayoutChild;
import com.epic.framework.Ui.EpicPlatform.EpicPlatformInterface;
import com.epic.framework.util.EpicLog;
import com.epic.framework.util.EpicStopwatch;
import com.epic.framework.util.StringHelper;

public class BlackberryMain extends UiApplication implements EpicPlatformInterface {
	public static BlackberryMain main = new BlackberryMain();
	private static BlackberryMainScreen mainScreen = new BlackberryMainScreen();
	private static BlackberryManager manager = new BlackberryManager();

	public static void main(String[] args) throws Throwable {
		EpicLog.i("BlackberryMain.main()");
		if(!EpicProjectConfig.isReleaseMode) {
			Timer killTimer = new Timer();
			killTimer.schedule(new TimerTask() {
				public void run() {
					EpicPlatform.onKillTimerTick();
				}
			}, 0, 1000);
		}

		try {
			FontFamily fontFam = FontFamily.forName("BBAlpha Sans");
			Font font = fontFam.getFont(FontFamily.SCALABLE_FONT, 16).derive(Font.PLAIN);            
			FontManager.getInstance().setApplicationFont(font);
		}
		catch(Exception e) {
			EpicLog.e("Error setting default font: " + e.toString());
		} 

		EpicProjectConfig.onApplicationStart();

		try {
			EpicPlatform.initialize(main);
			Ui.getUiEngineInstance().setAcceptableDirections(Display.ORIENTATION_LANDSCAPE);
			mainScreen.add(manager);
			
			main.pushScreen(mainScreen);
			main.enterEventDispatcher();
		}
		catch(Throwable e) {
			EpicLog.e("CAUGHT_UNHANDLED_EXCEPTION(in BlackberryMain) = " + e);
			throw e;
		}
	}

	private static class BlackberryManager extends Manager {

		protected BlackberryManager(){
			super(NO_VERTICAL_SCROLL | NO_VERTICAL_SCROLLBAR);
		}

		@Override
		protected void sublayout(int width, int height) {
			EpicLog.i("BlackberryManager.sublayout()");
			this.setExtent(width, height);
			EpicPlatform.onPlatformLayoutRequest(width, height, false);
		}

		public void doChildLayout(LayoutChild child, int l, int r, int t, int b, boolean firstLayout) {
			EpicLog.v("EpicBlackberryMainScreen.layoutChild() - " + StringHelper.namedArgList("l", l, "t", t, "r", r, "b", b, "first", firstLayout));

			Field childField = child.child.getBlackberryField();
			if(firstLayout) {
				EpicLog.v("Adding child to screen: " + child);
				this.add(childField);
			}

			setPositionChild(childField, l, t);
			layoutChild(childField, r - l, b - t);
		}
		protected void subpaint(Graphics graphics) {
//			EpicLog.i("BlackberryManager.subpaint()");
			EpicPlatform.onPlatformPaint(EpicCanvas.get(graphics));
			super.subpaint(graphics);
		}
//		@Override
//		protected void paint(Graphics graphics) {
//			super.paintChild(graphics, field)
//			super.paint(graphics);
//		}

		protected boolean touchEvent(TouchEvent t) {
			EpicStopwatch.attribute(EpicStopwatch.BUCKET_OTHER);

//			EpicLog.i("BlackberryManager.touchEvent(" + t.getEvent() + ", " + t.getX(1) + ", " + t.getY(1) + ")");
			if(t.getEvent() == TouchEvent.UP) {
				EpicPlatform.onPlatformTouchFinished(t.getX(1), t.getY(1));
			} else {
				EpicPlatform.onPlatformTouchEvent(t.getX(1), t.getY(1));
			}
			EpicStopwatch.attribute(EpicStopwatch.BUCKET_APP_CODE);
			return true;
//			return super.touchEvent(t);
		}
	}

	private static class BlackberryMainScreen extends MainScreen implements KeyListener {
		public BlackberryMainScreen() {
			super(NO_VERTICAL_SCROLL | NO_VERTICAL_SCROLLBAR);
			Ui.getUiEngineInstance().setAcceptableDirections(Display.DIRECTION_LANDSCAPE);
			Backlight.setTimeout(255);
		}

		protected void paint(Graphics graphics) {
			EpicStopwatch.attribute(EpicStopwatch.BUCKET_OTHER);
//			EpicLog.v("EpicBlackberryMainScreen.paint()");
			super.paint(graphics);
			EpicStopwatch.attribute(EpicStopwatch.BUCKET_DEBUG_RENDER);
		}
		protected void sublayout(int width, int height) {
			EpicLog.v("EpicBlackberryMainScreen.sublayout("+width+", "+height+")");
			super.sublayout(width, height);
		}
		protected void subpaint(Graphics graphics) {
			EpicLog.v("EpicBlackberryMainScreen.subpaint (noop)");
			super.subpaint(graphics);
		}
		protected void onDisplay() {
			EpicLog.v("EpicBlackberryMainScreen.onDisplay()");
			EpicPlatform.onPlatformShow();
			super.onDisplay();
		}

		protected void onExposed() {
			EpicLog.v("EpicBlackberryMainScreen.onExposed()");
			EpicPlatform.onPlatformShow();
			super.onExposed();
		}

		protected void onObscured() {
			EpicLog.v("EpicBlackberryMainScreen.onObscured()");
			EpicPlatform.onPlatformHide();
			super.onObscured();
		}

		public boolean onClose() {
			EpicLog.v("EpicBlackberryMainScreen.onClose()");
			EpicPlatform.onPlatformDestroy();
			Backlight.setTimeout(Backlight.getTimeoutDefault());
			return true;
		}

		public boolean onMenu(int instance) {
			EpicLog.v("EpicBlackberryMainScreen.onMenu()");
			this.removeAllMenuItems();
			for(final EpicMenuItem epicMenuItem : EpicPlatform.getMenuItems(EpicMenu.MENU_ALL)) {
				this.addMenuItem(new MenuItem(epicMenuItem.name, 1, 0) {
					public void run() {
						epicMenuItem.onClicked();
					}
				});
			}
			super.onMenu(instance);
			return true;
		}

		protected void onMenuDismissed(Menu menu) {
			EpicLog.v("EpicBlackberryMainScreen.onMenuDismissed()");
			EpicPlatform.onPlatformMenuDismissed();
		}

		public boolean keyChar(char c, int x, int y) {
			EpicStopwatch.attribute(EpicStopwatch.BUCKET_OTHER);
			EpicLog.v("EpicBlackberryMainScreen.keyChar(" + c + ")");
			boolean ret = EpicPlatform.onPlatformKeyPress(c) ? true :  super.keyChar(c, x, y);
			EpicStopwatch.attribute(EpicStopwatch.BUCKET_APP_CODE);
			return ret;
		}

		public boolean keyDown(int keycode, int time) {
			if(Keypad.key(keycode) == Keypad.KEY_ESCAPE) {
				EpicPlatform.onPlatformBackKey();
				return true;
			}
			return false;
			//			return epicScreen.onKeyPress(c) ? true :  super.keyChar(c, x, y);
			//
			//			// TODO: may not use these
			//			switch(Keypad.key(keycode)) {
			//			case Keypad.KEY_ESCAPE:
			//				if(epicScreen.onBackKey()) {
			//					return true;
			//				}
			//				// fallthrough
			//			case Keypad.KEY_SPEAKERPHONE:
			//				// fallthrough
			//			default:
			//				return super.keyDown(keycode, time);
			//			}
		}

		public boolean keyRepeat(int keycode, int time) { return false; }
		public boolean keyStatus(int keycode, int time) { return false; }
		public boolean keyUp(int keycode, int time) { return false; }

		protected boolean navigationMovement(int dx, int dy, int status, int time) {
			EpicLog.i("BlackberryMainScreen.navigationMovement()");
			return EpicPlatform.onPlatformNavigationMovement(dx, dy) ? true : super.navigationMovement(dx, dy, status, time);
		}

		protected boolean navigationClick(int status, int time) {
			EpicStopwatch.attribute(EpicStopwatch.BUCKET_OTHER);
			EpicLog.i("BlackberryMainScreen.navigationClick()");
			boolean ret = EpicPlatform.onPlatformNavigationClick() ? true : super.navigationClick(status, time);
			EpicStopwatch.attribute(EpicStopwatch.BUCKET_APP_CODE);
			return ret;
		}
	}

	@Override
	public void requestRepaint() {
//		EpicLog.v("BlackberryMain.requestRepaint()");
		manager.invalidate();
	}
	@Override
	public void requestRelayout() {
		EpicLog.v("BlackberryMain.requestRelayout()");
		manager.invalidate();
	}
	@Override
	public void clear() {
		EpicLog.v("BlackberryMain.clear()");
		manager.deleteAll();
	}
	@Override
	public void layoutChild(LayoutChild child, int l, int r, int t, int b, boolean firstLayout) {
		manager.doChildLayout(child, l, r, t, b, firstLayout);
	}
}



