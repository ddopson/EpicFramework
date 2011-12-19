package com.epic.framework.implementation;

import java.util.Set;

import org.xmlvm.iphone.UIEvent;
import org.xmlvm.iphone.UIInterfaceOrientation;
import org.xmlvm.iphone.UITouch;
import org.xmlvm.iphone.UIViewController;

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
}
