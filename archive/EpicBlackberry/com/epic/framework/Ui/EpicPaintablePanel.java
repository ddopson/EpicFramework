package com.epic.framework.Ui;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.Characters;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.TouchEvent;

import com.epic.framework.util.EpicFail;
import com.epic.framework.util.EpicLog;
import com.epic.framework.util.EpicStopwatch;

public abstract class EpicPaintablePanel extends EpicNativeWidget {
	EpicBlackberryPaintablePanel realPanel = new EpicBlackberryPaintablePanel();

	private static MouseTrail mouseTrail = new MouseTrail();
	abstract protected void onPaint(EpicCanvas canvas, MouseTrail mouseTrail);
	abstract protected boolean onClick(int x, int y);


	private PanelBackgroundImplementation backgroundImplementation = null;

	protected void setBackgroundImplementation(PanelBackgroundImplementation backgroundImplementation) {
		this.backgroundImplementation = backgroundImplementation;
	}

	Field getBlackberryField() {
		return realPanel;
	}

	class EpicBlackberryPaintablePanel extends Field {
		private int touching_x;
		private int touching_y;

		public EpicBlackberryPaintablePanel() {
			super();
		}

		private Bitmap _cachedBackground = null;
		private void doBackgroundBitmap(Graphics screenGraphics, int width, int height) {
			if(EpicPaintablePanel.this.backgroundImplementation != null) {
				if(_cachedBackground == null || _cachedBackground.getWidth() != width || _cachedBackground.getHeight() != height) {
					_cachedBackground = newBitmap(width, height);
					Graphics bitmapGraphics = Graphics.create(_cachedBackground);
					backgroundImplementation.paintCachedBackground(EpicCanvas.get(bitmapGraphics), width, height);
				}
				screenGraphics.drawBitmap(0, 0, width, height, _cachedBackground, 0, 0);
			}
		}

		private Bitmap _screenBufferBitmap = null;
		private Bitmap getScreenBufferBitmap(int width, int height) {
			if(_screenBufferBitmap == null || _screenBufferBitmap.getWidth() != width || _screenBufferBitmap.getHeight() != height) {
				_screenBufferBitmap = newBitmap(width, height);
			}
			return _screenBufferBitmap;
		}

		private Bitmap newBitmap(int width, int height) {
			return new Bitmap(width, height);
		}

		int n = 0;
		protected void paint(Graphics screenGraphics) {
			int width = this.getWidth();
			int height = this.getHeight();

			if(n++ == 10) {
				EpicStopwatch.reset();
			}

			EpicStopwatch.paintStart();
			EpicStopwatch.attribute(EpicStopwatch.BUCKET_OTHER);

			boolean shouldBuffer = false;
			Graphics graphics = null;
			Bitmap screenBufferBitmap = null;

			if(shouldBuffer) {
				screenBufferBitmap = getScreenBufferBitmap(width, height);
				graphics = Graphics.create(screenBufferBitmap);
			}
			else {
				graphics = screenGraphics;
			}
			// Do the background
			doBackgroundBitmap(graphics, width, height);
			EpicStopwatch.attribute(EpicStopwatch.BUCKET_BG_CLEAR);

			// Do the App Level Rendering
			EpicPaintablePanel.this.onPaint(EpicCanvas.get(graphics), mouseTrail);
			EpicStopwatch.attribute(EpicStopwatch.BUCKET_APP_RENDER);

			// Overlay the debug strings
//			if(backgroundImplementation != null && !EpicProjectConfig.isReleaseMode) {
//				// TODO: total hack, testing backgroundImplementation here.  Should have more robust logic, even, perhaps, a diff flow for "simple" button panels
//				String[] texts = EpicStopwatch.getDebugStringArray();
//				graphics.setColor(EpicColor.WHITE);
//				graphics.setGlobalAlpha(255);
//				for(int i = 0; i < texts.length; i++) {
//					graphics.drawText(texts[i], 0, i*30);
//				}
//				EpicStopwatch.attribute(EpicStopwatch.BUCKET_DEBUG_RENDER);
//			}

			if(shouldBuffer) {
				// Blit the Screen
				screenGraphics.rop(Graphics.ROP_SRC_COPY, 0, 0, width, height,  screenBufferBitmap, 0, 0);
				EpicStopwatch.attribute(EpicStopwatch.BUCKET_BLIT_SCREEN);
			}
			EpicStopwatch.paintFinish();
		}
		//		protected boolean trackwheelClick(int status, int time) {
		//		// TODO Auto-generated method stub
		//		epicButton.onClick();
		//		return super.trackwheelClick(status, time);
		//	}

		protected void layout(int width, int height) {
			this.setExtent(width, height);
		}

		public void fieldChanged(Field arg0, int arg1) {
			EpicPaintablePanel.this.onClick(-1, -1);
		}

		protected boolean keyChar( char character, int status, int time )
		{
			if( character == Characters.ENTER ) {
				EpicPaintablePanel.this.onClick(-1, -1);
				return true;
			}
			return super.keyChar( character, status, time );
		}


		protected boolean touchEvent(TouchEvent message) {
			int x = message.getX(1);
			int y = message.getY(1);
			switch(message.getEvent()) {
			case TouchEvent.DOWN:
			case TouchEvent.MOVE:
				mouseTrail.add(x, y);
				break;

			case TouchEvent.UP:
				mouseTrail.clear();
				return true;

			case TouchEvent.CANCEL:
				mouseTrail.clear();
				break;

			case TouchEvent.CLICK:
				EpicLog.w("Tracking click at " + x + ", " + y);
				EpicPaintablePanel.this.onClick(x, y);
				break;
			case TouchEvent.UNCLICK:
			case TouchEvent.GESTURE:
				// wtf are these?  Should we do ANYTHING here?
				break;
			default:
				throw EpicFail.unhandled_case();

			}
			return false;
		}
		//
		//		protected boolean trackwheelClick(int arg0, int arg1) {
		//			EpicPaintablePanel.this.onClick(-1, -1);
		//			return true;
		//		}
	}
}
