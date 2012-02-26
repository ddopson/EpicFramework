//package com.epic.framework.common.Ui2;
//
//import com.epic.framework.common.util.EpicSocial;
//import com.epic.framework.common.util.EpicSocial.EpicSocialSignInCompletionHandler;
//
//public class EnsureLoginAction extends EpicAction implements EpicSocialSignInCompletionHandler {
//
//	EpicAction success;
//	EpicAction failure;
//
//	@Override
//	public void run() {
//		if(EpicSocial.isLoggedIn()) {
//			if(success != null) {
//				success.run();
//			}
//		} else {
//			EpicSocial.signIn(this);
//		}
//	}
//
//	@Override
//	public void onSignedIn(String identity) {
//		if(success != null) {
//			success.run();
//		}
//	}
//}
