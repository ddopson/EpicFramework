package com.epic.framework.implementation;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.HashMap;
import java.util.Vector;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.epic.framework.cfg.EpicProjectConfig;
import com.epic.framework.common.Ui.EpicMenu;
import com.epic.framework.common.Ui.EpicMenu.EpicMenuItem;
import com.epic.framework.common.Ui.EpicPlatform;
import com.epic.framework.common.util.EpicFail;
import com.epic.framework.common.util.EpicLog;
import com.epic.framework.common.util.EpicSocial;
import com.epic.framework.implementation.facebook.Facebook;
import com.epic.framework.implementation.tapjoy.TapjoyConnect;
import com.epic.framework.implementation.tapjoy.TapjoyEarnedPointsNotifier;
import com.epic.framework.implementation.tapjoy.TapjoyLog;
import com.realcasualgames.words.PlayerState;

public class EpicAndroidActivity extends Activity {
	public static final int CONTACT_PICK = 5;
	private static EpicAndroidActivity currentActivity = null;
	public Facebook facebook = new Facebook("172905469435543");
	public static boolean initialized;

	public static TapjoyEarnedPointsNotifier tepn = new TapjoyEarnedPointsNotifier() {
		public void earnedTapPoints(int amount) {
			final int a = amount;
			EpicPlatform.runOnUiThread(new Runnable() {
				public void run() {
					PlayerState.updateLocalTokens(a, true);		
				}				
			});
		}
	};
	
	public EpicAndroidActivity() {
		super();
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if(data == null) {
			EpicLog.w("Intent result data empty");
			return;
		}
		
		if(requestCode == CONTACT_PICK && resultCode == RESULT_OK) {
			Cursor cursor =  managedQuery(data.getData(), null, null, null, null);
			Vector<String> emailList = new Vector<String>();
			   while (cursor.moveToNext()) 
			   {           
			       String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));

			       // Find Email Addresses
			       Cursor emails = getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,null,ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + contactId,null, null);
			       while (emails.moveToNext()) 
			       {
			    	   emailList.add(emails.getString(emails.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA)));
			       }
			       emails.close();
			  }  //while (cursor.moveToNext())        
			   cursor.close();
			   
			   String[] emailStrings = new String[emailList.size()];
			   for(int i = 0; i < emailList.size(); ++i) {
				   emailStrings[i] = emailList.get(i);
			   }
		
    		   EpicSocial.onContactEmailReturned(emailStrings);
    		   EpicLog.i("Returned contact info.");
	   
		}
		
		if(requestCode == 6969) {
			// Facebook SSO
			facebook.authorizeCallback(requestCode, resultCode, data);
		}
		
	}
	
	public static EpicAndroidActivity getCurrentAndroidActivity() {
		return currentActivity;
	}
	
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		
		String screenName = null;
		screenName = intent.getStringExtra("launchScreen");
		if(screenName == null) {
			EpicLog.w("launchScreen null");
		} else {
			EpicLog.w("launchScreen=" + screenName);
		}
		
		String extra = null;
		extra = intent.getStringExtra("challengeId");
		if(extra != null) {
			EpicLog.w("extra=" + extra);
		}
		
		EpicPlatform.changeScreen(EpicProjectConfig.getInitialScreenObject(screenName, extra));
	}

	protected void onCreate(Bundle savedInstanceState) {
		EpicLog.i("EpicAndroidActivity.onCreate() this=" + this);
		super.onCreate(savedInstanceState);
		if(!initialized) {
			EpicLog.w("Starting init");
			String screenName = null;
			Intent n = getIntent();
			screenName = n.getStringExtra("launchScreen");
			if(screenName == null) {
				EpicLog.w("launchScreen null");
			} else {
				EpicLog.w("launchScreen=" + screenName);
			}
			
			String extra = null;
			extra = n.getStringExtra("challengeId");
			if(extra != null) {
				EpicLog.w("extra=" + extra);
			}
			
			EpicPlatform.initialize(EpicPlatformImplementation.get(), screenName, extra);
			EpicProjectConfig.onApplicationStart();
			if(!EpicProjectConfig.isReleaseMode) TapjoyLog.enableLogging(true);
			TapjoyConnect.requestTapjoyConnect(EpicApplication.getAndroidContext(), "6517efe1-8c2f-4218-ada9-1f56a76361d5", "HpW9tbSmVBf4ZfvfLqpj");		
			TapjoyConnect.getTapjoyConnectInstance().setEarnedPointsNotifier(tepn);

			Intent intent = new Intent("com.google.android.c2dm.intent.REGISTER");
			intent.putExtra("app", PendingIntent.getBroadcast(this, 0, new Intent(), 0));
			intent.putExtra("sender", "android@realcasualgames.com");
			startService(intent);
			initialized = true;
		} else {
			EpicLog.w("Skipping re-init");
		}
		
		
		try {
			EpicLog.i("Setting exception handler.");
			final UncaughtExceptionHandler previousUncaughtExceptionHandler = Thread.currentThread().getUncaughtExceptionHandler();
			Thread.currentThread().setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
				public void uncaughtException(Thread thread, Throwable ex) {
					EpicLog.e("UNCAUGHT_EXCETPION: ", ex);
					StringWriter stringWriter = new StringWriter();
					ex.printStackTrace(new PrintWriter(new StringWriter()));
					EpicLog.e("ExceptionStackTrace: " + stringWriter.toString());
					if(previousUncaughtExceptionHandler != null) {
						EpicLog.e("Calling The Default UncaughtExceptionHandler: '" + previousUncaughtExceptionHandler + "'  " + previousUncaughtExceptionHandler.toString());
						previousUncaughtExceptionHandler.uncaughtException(thread, ex);
					}
				}
			});
			EpicLog.i("Exception handler set, binding to activity...");
		} catch(Exception e) {
			EpicLog.e("Exception while setting exception handler: " + e.toString());
		}
		
		if(EpicProjectConfig.getIsTitlebarDisabled()) {
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
			getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		}


		currentActivity = this;


		View platformUiObject = (View)EpicPlatformImplementation.get();
		if(platformUiObject.getParent() != null) {
			//DDOPSON-2011-06-20 - This can be cast to ViewGroup.  only known way to remove from old activity and avoid a crash. (http://code.google.com/p/android/issues/detail?id=5492)
			ViewGroup parent = (ViewGroup)platformUiObject.getParent();
			parent.removeView(platformUiObject);
			EpicPlatform.onPlatformShow();
		}
		EpicFail.assertNotNull(platformUiObject);
		this.setContentView(platformUiObject);
		EpicLog.i("Screen created...");
		
//		if(facebook.getAccessToken() != null && facebook.getAccessExpires() > System.currentTimeMillis()) {
//			EpicLog.i("Facebook oAuth token: " + facebook.getAccessToken());
//			facebook.authorize(this, new String[] { "publish_stream" }, 999, new DialogListener() {
//		           @Override
//		           public void onComplete(Bundle values) {}
//	
//		           @Override
//		           public void onFacebookError(FacebookError error) {}
//	
//		           @Override
//		           public void onError(DialogError e) {}
//	
//		           @Override
//		           public void onCancel() {}
//		      });
//		}	
		
	}

	protected void onResume() {
		EpicLog.i("EpicAndroidActivity.onResume() this=" + this);
		super.onResume();
		EpicPlatform.onPlatformShow();
		EpicLog.i("Showing epicScreen");
	}

	protected void onPause() {
		EpicLog.i("EpicAndroidActivity.onPause() this=" + this);
		super.onPause();
		EpicPlatform.onPlatformHide();
		EpicLog.i("Hiding epicScreen");
		System.gc();
	}

	protected void onDestroy() {
		EpicLog.i("EpicAndroidActivity.onDestroy() this=" + this);
		super.onDestroy();
		EpicPlatform.onPlatformDestroy();
		EpicLog.i("Destroying epicScreen");
		System.gc();
	}

	private	HashMap<MenuItem, EpicMenuItem> mapMenuItems = new HashMap<MenuItem, EpicMenu.EpicMenuItem>();


	public boolean onPrepareOptionsMenu(Menu menu) {
		EpicLog.i("EpicAndroidActivity.onCreateOptionsMenu()");
		mapMenuItems.clear();
		menu.clear();
		for(EpicMenuItem item : EpicPlatform.getMenuItems(EpicMenu.MENU_ALL)) {
			mapMenuItems.put(menu.add(item.name), item);
		}
		return true;
	}

	public void onOptionsMenuClosed(Menu menu) {
		mapMenuItems.clear();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event)  {
		boolean ret;
		switch(keyCode) {
		case KeyEvent.KEYCODE_BACK:
			ret = EpicPlatform.onPlatformBackKey();
			break;
		default:
			ret = EpicPlatform.onPlatformKeyPress(keyCode);
			break;
		}

		return ret ? true : super.onKeyDown(keyCode, event);
	}
	
	protected void onStart() {
		super.onStart();
//	    UAirship.shared().getAnalytics().activityStarted(this);
	}

	protected void onStop() {
		super.onStop();
//		UAirship.shared().getAnalytics().activityStopped(this);
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		EpicLog.i("EpicAndroidActivity.onOptionsItemSelected(" + item.toString()  + ") - " + item.getItemId());
		EpicMenuItem epicMenuItem = mapMenuItems.get(item);
		if(epicMenuItem != null) {
			epicMenuItem.onClicked();
			return true;
		}
		else {
			return super.onOptionsItemSelected(item);				
		}
	}
}
