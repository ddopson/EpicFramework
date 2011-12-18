package com.epic.framework.implementation.Ui;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.net.Uri;
import android.provider.Settings;

import com.epic.framework.common.util.EpicLog;
import com.tapjoy.TapjoyNotifier;

public class EpicApplication extends Application {
	static Application theApplication;
	
	public static TapjoyNotifier tn = new TapjoyNotifier() {
		public void getUpdatePoints(String currencyName, int pointTotal) {
			EpicLog.w("Got " + pointTotal + " " + currencyName + " from getUpdatePoints()");
		}

		public void getUpdatePointsFailed(String error) {
			EpicLog.e("Failure getting update points: " + error);
		}
	};
	
	public void onCreate() {
		super.onCreate();
		theApplication = this;
		
		if(isProcess("com.realcasualgames.words")) {
			EpicLog.w("EpicApplication.onCreate() from main context");


		} else {
			EpicLog.w("EpicApplication.onCreate() from background context--skipping init");
		}
	
	}
	
    private boolean isProcess(String processName) {
        Context context = getApplicationContext();
        ActivityManager actMgr = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> appList = actMgr.getRunningAppProcesses();
        for (RunningAppProcessInfo info : appList) {
            if (info.pid == android.os.Process.myPid() && processName.equals(info.processName)) {
            	EpicLog.w("Process info: " + info.processName + ", " + info.importance + ", " + info.pid);
            	return true;
            }
        }
        return false;
    }

	public static Context getAndroidContext() {
		return theApplication.getApplicationContext();
	}

	static Resources getAndroidResources() {
		return theApplication.getResources();
	}
	
	public void onLowMemory() {
		EpicLog.w("LOW_MEMORY - Application.onLowMemory() was triggered");
		super.onLowMemory();
		
		if(isProcess(getAndroidContext().getPackageName())) {
			EpicBitmapImplementation.onLowMemory();
		}
	}

	public static void blackberryMain(Object whoGivesACrap) {
		// do nothing on Android.  only needed on BB.  Android starts app using an intent and a big gooey framework
	}

	public static void androidLaunchMarketplace(String url) {
		Intent webIntent = new Intent("android.intent.action.VIEW", Uri.parse(url));
		webIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		theApplication.startActivity(webIntent);
	}

	public static void killProcess() {
		System.exit(0);
	}

	public static String getDeviceName() {
		String device = Settings.System.getString(theApplication.getContentResolver(), "android.os.Build.DEVICE");
		if(device == null) {
			device = Settings.System.getString(theApplication.getContentResolver(), "android.os.Build.MODEL");
		}

		if(device == null) {
			device = "Not Detected";
		}

		EpicLog.i("Detected device: " + device);
		return device;
	}

	public static String getApplicationVersion() {
		String mVersionNumber = "Unknown";

		try {
            String pkg = theApplication.getPackageName();
            mVersionNumber = theApplication.getPackageManager().getPackageInfo(pkg, 0).versionName;
        } catch (NameNotFoundException e) {}

        EpicLog.i("Detected version " + mVersionNumber);
        return mVersionNumber;
	}
	
	public static String getListingId() {
		return "15";
	}

	public static boolean isTouchEnabledDevice() {
		return true;  // ALWAYS touch-enabled on Android ...
	}
}
