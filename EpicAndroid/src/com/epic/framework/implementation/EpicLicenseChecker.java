package com.epic.framework.implementation;

public class EpicLicenseChecker {
	private static final byte[] SALT = new byte[] {
		-40, 25, 39, -118, -102, -57, 74, -64, 51, 88, -95, -45, 77, -117, -36, -113, -11, 32, -64, 75
	};
	private static final String BASE64_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA++CbrzOkS0g7XhBpN6OFzvNt4lPeTnMHr+CY0Y48SZq714K9Dk0d6Sg6BI2LOxmBrRknv8k08TUdeIBLH6Sg1NJ1uz09VVK3NLvnhpago4KWfEalAyMPdzc6+A5Ruu7Dl22gfjwjou9m8QiwxncJ+6q0bC+60ezFlM/98UFnJ1dkx5Z0qfT4xcHnOOZ2T/CxO03r5uYBY1X0DYgOc4lTcfagzJI5vACsG4EnzYK7FHNQnBNiIEP74qi0AR0LkV/22sfpIOhwB8cZYIq6vdMERjvV6AkLVAq/BzonRcyxVVhJlQckHr7GBkxJd9x+QMxA+Meww/Pw/JZklqKVRBD2BwIDAQAB";

	private EpicLicenseChecker() {
	}

	public static EpicLicenseChecker checkLicense() {
		return new EpicLicenseChecker();
	}

	public void onDestroy() {
	}
}
