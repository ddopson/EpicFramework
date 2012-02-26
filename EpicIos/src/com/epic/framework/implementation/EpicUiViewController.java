package com.epic.framework.implementation;

import java.util.Set;

import org.xmlvm.iphone.UIEvent;
import org.xmlvm.iphone.UIInterfaceOrientation;
import org.xmlvm.iphone.UITouch;
import org.xmlvm.iphone.UIViewController;

import com.epic.framework.common.util.EpicLog;

public class EpicUiViewController extends UIViewController {
	@Override
	public boolean shouldAutorotateToInterfaceOrientation(int orientation) {
		return (orientation == UIInterfaceOrientation.LandscapeLeft) || (orientation == UIInterfaceOrientation.LandscapeRight);
	}
	
	@Override
	public void loadView() {
		super.loadView();
		EpicUiView epicCanvasView = new EpicUiView(Main.window.getBounds());
		setView(epicCanvasView);
	}
	
	@Override
	public void viewWillDisappear(boolean animated) {
		super.viewWillDisappear(animated);
		EpicLog.i("viewWillDisappear in main VC");
		Main.navc.setNavigationBarHidden(false, true);
	}
	
	@Override
	public void viewWillAppear(boolean animated) {
		EpicLog.i("viewWillAppear in main VC");
		super.viewWillAppear(animated);
		Main.navc.setNavigationBarHidden(true, true);
		EpicSocialTabbedView.displayed = false;
	}
	
	@Override
	public void viewDidUnload() {
		super.viewDidUnload();
		EpicLog.i("viewDidUnload in main VC");
	}
}
