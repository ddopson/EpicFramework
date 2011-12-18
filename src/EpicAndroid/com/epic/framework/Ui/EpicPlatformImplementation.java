package com.epic.framework.Ui;

import java.util.Timer;
import java.util.TimerTask;

import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.epic.cfg.EpicProjectConfig;
import com.epic.framework.Ui.EpicPercentLayout.LayoutChild;
import com.epic.framework.Ui.EpicPlatform.EpicPlatformInterface;
import com.epic.framework.Ui.EpicTimer.PlatformTimerInterface;
import com.epic.framework.types.Dimension;
import com.epic.framework.util.EpicLog;
import com.realcasualgames.words.PlayerState;

public class EpicPlatformImplementation extends ViewGroup implements EpicPlatformInterface {
	private static EpicPlatformImplementation thePercentLayout = new EpicPlatformImplementation();
	public static EpicPlatformImplementation get() {
		return thePercentLayout;
	}
	private EpicPlatformImplementation() {
		super(EpicApplication.getAndroidContext());
		// DDOPSON-2011-06-10 - if we don't set a transparent background here then Android does retarded things and draws on top of us, making the whole screen black.
		this.setBackgroundDrawable(new Drawable() {
			public void setColorFilter(ColorFilter paramColorFilter) { }
			public void setAlpha(int paramInt) { }
			public int getOpacity() { return PixelFormat.OPAQUE; }
			public void draw(Canvas paramCanvas) { }
		});
	}

	public void clear() {
		this.removeAllViews();
	}

	public void layoutChild(LayoutChild child, int l, int r, int t, int b, boolean firstLayout) {
		View childView = child.child.getAndroidView();
		if(firstLayout) {
			this.addView(childView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
		}
		childView.measure(
				MeasureSpec.makeMeasureSpec(r - l, MeasureSpec.EXACTLY), 
				MeasureSpec.makeMeasureSpec(b - t, MeasureSpec.EXACTLY)
		);
		childView.layout(l, t, r, b);
	}

	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		EpicPlatform.onPlatformLayoutRequest(r - l, b - t, false);
	}

	public void requestRepaint() {
		this.invalidate();
	}

	public void requestRelayout() {
		this.invalidate();
	}

	//	public void draw(Canvas canvas) {
	//		super.draw(canvas);
	//		
	//		EpicPlatform.onPlatformPaint(EpicCanvas.get(canvas));
	////		super.draw(canvas);
	//	}
	private static final boolean BUFFERED = EpicPlatform.RMODE_STRETCH;
	private static final Bitmap screenBuffer = BUFFERED ? Bitmap.createBitmap(EpicProjectConfig.designDimensions.width, EpicProjectConfig.designDimensions.height, Bitmap.Config.ARGB_8888) : null;
	private static final Canvas bufferCanvas = BUFFERED ? new Canvas(screenBuffer) : null;
	private static final Rect bufferSrcRect = BUFFERED ? new Rect(0, 0, EpicProjectConfig.designDimensions.width, EpicProjectConfig.designDimensions.height) : null; 
	private static final Rect screenRect = BUFFERED ? new Rect() : null;
	private static final Paint defaultPaint = BUFFERED ? new Paint() : null;
	protected void onDraw(Canvas canvas) {
		if(BUFFERED) {
			EpicPlatform.onPlatformPaint(EpicCanvas.get(bufferCanvas));
			screenRect.set(0, 0, this.getWidth(), this.getHeight());
			canvas.drawBitmap(screenBuffer, bufferSrcRect, screenRect, defaultPaint);
		} else {
			EpicPlatform.onPlatformPaint(EpicCanvas.get(canvas));			
		}
	}

	public boolean onTouchEvent(MotionEvent event) {
		int x = (int)event.getX();
		int y = (int)event.getY();
		switch(event.getAction()) {
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_MOVE:
			EpicPlatform.onPlatformTouchEvent(x, y);
			break;
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_OUTSIDE:
			EpicPlatform.onPlatformTouchFinished(x, y);
			break;
		case MotionEvent.ACTION_UP:
			EpicPlatform.onPlatformTouchFinished(x, y);
			break;
		default:
			return false;
		}

		return true;
	}

	public static void runOnUiThread(Runnable task) {
		EpicAndroidActivity.getCurrentAndroidActivity().runOnUiThread(task);
	}
	public static void runInBackground(Runnable runnable) {
		new Thread(runnable).start();
	}


	private static final Timer globalTimer = new Timer();
	private static final Handler globalTimerHandler = new Handler() {
		public void handleMessage(Message msg) {
			EpicPlatform.onPlatformTimerTick();
		}		
	};
	private static final TimerTask globalTimerTask = new TimerTask() {
		public void run() {
			globalTimerHandler.sendEmptyMessage(0);
		}
	};

	public static void scheduleGlobalTimer(int period) {
		globalTimer.schedule(globalTimerTask, 0, period);
	}
	public static void dismissNotifications() {
		String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager mNotificationManager = (NotificationManager) EpicApplication.getAndroidContext().getSystemService(ns);
        mNotificationManager.cancelAll();		
        
        PlayerState.resetClicked();
	}

}
