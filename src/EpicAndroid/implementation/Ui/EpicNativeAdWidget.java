//package com.epic.framework.implementation.Ui;
//
//import com.epic.framework.common.util.EpicLog;
//import com.google.ads.Ad;
//import com.google.ads.AdListener;
//import com.google.ads.AdRequest;
//import com.google.ads.AdRequest.ErrorCode;
//import com.google.ads.AdSize;
//import com.google.ads.AdView;
//
//import android.view.View;
//
//public class EpicNativeAdWidget extends EpicNativeWidget implements AdListener {
//
//	AdView ad;
//	public static final int TYPE_BANNER = 0;
//	public static final int TYPE_IAB_BANNER = 1;
//	public static final int TYPE_LEADERBOARD = 2;
//	
//	public EpicNativeAdWidget(int type) {
//		AdSize size;
//		switch(type) {
//			case TYPE_IAB_BANNER:
//				size = AdSize.IAB_BANNER;
//				break;
//			case TYPE_LEADERBOARD:
//				size = AdSize.IAB_LEADERBOARD;
//				break;
//			case TYPE_BANNER:
//			default:
//				size = AdSize.BANNER;
//				break;
//		}
//		
//		ad = new AdView(EpicAndroidActivity.getCurrentAndroidActivity(), size, "a14e49d642d9827");
//		ad.setAdListener(this);
//		AdRequest r = new AdRequest();
//		ad.loadAd(r);
//	}
//	
//	@Override
//	View getAndroidView() {
//		return ad;
//	}
//
//	@Override
//	public void onDismissScreen(Ad arg0) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void onFailedToReceiveAd(Ad arg0, ErrorCode arg1) {
//		EpicLog.w("Failed to receive ad.");
//	}
//
//	@Override
//	public void onLeaveApplication(Ad arg0) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void onPresentScreen(Ad arg0) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void onReceiveAd(Ad arg0) {
//		EpicLog.i("Ad received.");		
//	}
//
//}
