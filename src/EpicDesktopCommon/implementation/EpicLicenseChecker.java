package com.epic.framework.implementation


public class EpicLicenseChecker {
	private static final byte[] SALT = new byte[] {
		-40, 25, 39, -118, -102, -57, 74, -64, 51, 88, -95, -45, 77, -117, -36, -113, -11, 32, -64, 75
	};
	private static final String BASE64_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDE6d8c/5Uw1ccsMfbCp8NGi9rkiTHGaK8oZc4x8wlDeb5xnc1uh5YY9efh7RIY4bFy4V/vhF2e+Q623geCz1+eElf1UFZJFhMFNGBmwNtEzY8ACdCYsG5HlC3orH+U+S2L+yKN06+aqHN5t07jMvhMAGIc8jv3xQvEo+A717cvYwIDAQAB";

//	private LicenseChecker checker;
//	private EpicLicenseChecker(LicenseChecker checker) {
//		this.checker = checker;
//	}

	public static EpicLicenseChecker checkLicense() {
//		// Try to use more data here. ANDROID_ID is a single point of attack.
//		String deviceId = Secure.getString(EpicApplication.getAndroidContext().getContentResolver(), Secure.ANDROID_ID);
//
//		AESObfuscator obfuscator = new AESObfuscator(SALT, EpicApplication.getAndroidContext().getPackageName(), deviceId);
//		ServerManagedPolicy policy = new ServerManagedPolicy(EpicApplication.getAndroidContext(), obfuscator);
//
//		// Library calls this when it's done.
//		EpicLicenseCheckerCallback callback = new EpicLicenseCheckerCallback(screen);
//
//		// Construct the LicenseChecker with a policy.
//		LicenseChecker checker = new LicenseChecker(EpicApplication.getAndroidContext(), policy, BASE64_PUBLIC_KEY);
//
//		EpicLog.i("Doing license check on " + EpicApplication.getAndroidContext().getPackageName() + " for " + deviceId);
//		checker.checkAccess(callback);
//		EpicLog.i("License check initiated...");
//		return new EpicLicenseChecker(checker);
		return new EpicLicenseChecker();
	}

	public void onDestroy() {
//		checker.onDestroy();
	}

//	private static class EpicLicenseCheckerCallback implements LicenseCheckerCallback {
//		public EpicScreen screen;
//
//		public EpicLicenseCheckerCallback(EpicScreen screen) {
//			this.screen = screen;
//		}
//
//		public void allow() {
//			// Should allow user access.
//			EpicLog.i("License valid.");
//		}
//
//		public void dontAllow() {
//			// Should not allow access. In most cases, the app should assume
//			// the user has access unless it encounters this. If it does,
//			// the app should inform the user of their unlicensed ways
//			// and then either shut down the app or limit the user to a
//			// restricted set of features.
//			// In this example, we show a dialog that takes the user to Market.
//			EpicLog.w("License is not valid. Closing...");
//			AlertDialog.Builder builder = new AlertDialog.Builder(screen.getAndroidActivity());
//			builder.setMessage("This copy of Humans vs. Aliens is not registered, or there was a problem contacting the licensing server. If you have not purchased this app, please do so from the market. If you have, please contact androidsupport@epicapplications.com.")
//			.setCancelable(false)
//			.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
//				public void onClick(DialogInterface dialog, int id) {
//					EpicLicenseCheckerCallback.this.screen.dismiss();
//				}
//			})
//			.setNegativeButton("Buy in Market", new DialogInterface.OnClickListener() {
//				public void onClick(DialogInterface dialog, int id) {
//					dialog.cancel();
//					EpicLicenseCheckerCallback.this.screen.dismiss();
//					Uri uri = Uri.parse("market://search?q=humans%20vs%20aliens");
//					Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//					EpicApplication.getAndroidContext().startActivity(intent);
//				}
//			})
//			.setNegativeButton("Ignore(Dave'sHack)", new DialogInterface.OnClickListener() {
//				public void onClick(DialogInterface dialog, int which) {
//					dialog.cancel();
//				}
//			});
//			AlertDialog alert = builder.create();
//			alert.show();
//		}
//
//		public void applicationError(ApplicationErrorCode errorCode) {
//			if (this.screen.isFinishing()) {
//				// Don't update UI if Activity is finishing.
//				return;
//			}
//			// This is a polite way of saying the developer made a mistake
//			// while setting up or calling the license checker library.
//			// Please examine the error code and fix the error.
//			EpicLog.e("Error getting license: " + errorCode.toString());
//			dontAllow();
//		}
//	}
}
