package com.epic.framework.Ui;

import java.io.IOException;

import javax.microedition.io.HttpConnection;

import net.rim.blackberry.api.browser.Browser;
import net.rim.blackberry.api.browser.BrowserSession;
import net.rim.device.api.io.transport.ConnectionDescriptor;
import net.rim.device.api.io.transport.ConnectionFactory;
import net.rim.device.api.system.DeviceInfo;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.Dialog;

import com.epic.framework.util.EpicIO;
import com.epic.framework.util.EpicLog;

public class EpicLicenseChecker {
	public static EpicLicenseChecker checkLicense() {
		if(!EpicIO.isExistsFile("hva_auth_001")) {
			Thread t = new EpicLicenseCheckerThread();
			t.start();
		}

		return new EpicLicenseChecker();
	}

	public void onDestroy() {
	}

	private static class EpicLicenseCheckerThread extends Thread {

		public void run() {
			ConnectionFactory factory = new ConnectionFactory();
			ConnectionDescriptor c = factory.getConnection("http://epicapplications.com/register/registration/register_hva?pin=" + Integer.toHexString(DeviceInfo.getDeviceId()));
			HttpConnection conn = (HttpConnection) c.getConnection();
			try {
				String response = conn.getHeaderField("epic_hva_license");
				EpicLog.i("License header: " + response);
				if(response.equals("pow")) {
					allow();
				} else if(response.equals("smc")) {
					dontAllow();
				} else {
					applicationError();
				}
			} catch (IOException e) {
				EpicLog.e("Error getting license header: " + e.toString());
				applicationError();
			}
		}

		public void allow() {
			EpicIO.touchFile("hva_auth");
			EpicLog.i("License valid.");
		}

		public void dontAllow() {
			EpicLog.w("License is not valid. Closing...");
			UiApplication.getUiApplication().invokeLater(new Runnable() {
				public void run() {
					Dialog.alert("This copy of Humans vs. Aliens is not registered, or there was a problem contacting the licensing server. If you have not purchased this app, please do so from the App World. If you have, please contact support@epicapplications.com.");
					// Get the default sessionBrowserSession
					BrowserSession browserSession = Browser.getDefaultSession();
					// now launch the URL
					browserSession.displayPage("http://appworld.blackberry.com/webstore/clientlaunch/40278/");
					System.exit(1);
				}
			});
		}

		public void applicationError() {
			EpicLog.e("Application error getting license");
			dontAllow();
		}
	}
}
