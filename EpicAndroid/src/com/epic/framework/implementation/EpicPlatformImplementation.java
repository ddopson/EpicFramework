package com.epic.framework.implementation;

import java.util.Timer;
import java.util.TimerTask;

import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
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

import com.epic.framework.common.Ui.EpicCanvas;
import com.epic.framework.common.Ui.EpicPercentLayout.LayoutChild;
import com.epic.framework.common.Ui.EpicPlatform;
import com.epic.framework.common.util.EpicLog;

public class EpicPlatformImplementation extends ViewGroup {
	private static EpicPlatformImplementation singleton = new EpicPlatformImplementation();
	public static EpicPlatformImplementation get() {
		return singleton;
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

	public static void clear() {
		singleton.removeAllViews();
	}

	public static void layoutChild(LayoutChild child, int l, int r, int t, int b, boolean firstLayout) {
		View childView = child.child.getAndroidView();
		if(firstLayout) {
			singleton.addView(childView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
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

	public static void requestRepaint() {
		singleton.invalidate();
	}

	public static void requestRelayout() {
		singleton.invalidate();
	}

	//	public void draw(Canvas canvas) {
	//		super.draw(canvas);
	//		
	//		EpicPlatform.onPlatformPaint(EpicCanvas.get(canvas));
	////		super.draw(canvas);
	//	}
	protected void onDraw(Canvas canvas) {
		EpicPlatform.onPlatformPaint(EpicCanvas.get(canvas));			
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
        
// WF_COMPAT       PlayerState.resetClicked();
	}
	
	public static boolean isTouchEnabledDevice() {
		return true;  // Always true on Android
	}
	
	public static String getListingId() {
		return "15";
	}

	public static String getApplicationVersion() {
		String mVersionNumber = "Unknown";

		try {
            String pkg = EpicApplication.theApplication.getPackageName();
            mVersionNumber = EpicApplication.theApplication.getPackageManager().getPackageInfo(pkg, 0).versionName;
        } catch (NameNotFoundException e) {}

        EpicLog.i("Detected version " + mVersionNumber);
        return mVersionNumber;
	}
	
	public static String getDeviceName() {
		return "deviceName";
	}

}
