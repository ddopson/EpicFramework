/*
 * XMLVM demo template of Hello World application
 */
package com.epic.framework.implementation;

import java.util.Set;

import org.xmlvm.iphone.UIApplication;
import org.xmlvm.iphone.UIApplicationDelegate;
import org.xmlvm.iphone.UIEvent;
import org.xmlvm.iphone.UIInterfaceOrientation;
import org.xmlvm.iphone.UIScreen;
import org.xmlvm.iphone.UITouch;
import org.xmlvm.iphone.UIViewController;
import org.xmlvm.iphone.UIWindow;

import com.epic.framework.common.Ui.EpicPlatform;
import com.epic.framework.common.Ui.EpicTimer;
import com.epic.framework.common.util.EpicLog;

public class Main extends UIApplicationDelegate {
	public static UIWindow window;
	
    @Override
    public void applicationDidFinishLaunching(UIApplication app) {
        window = new UIWindow(UIScreen.mainScreen().getBounds());
        
        final EpicUiViewController controller = new EpicUiViewController();
        
        window.setRootViewController(controller);
        window.addSubview(controller.getView());
        window.makeKeyAndVisible();
        
        EpicTimer t = new EpicTimer() {
			protected void onTimerTick(int n) {
				EpicPlatform.onPlatformTimerTick();
			}
        };
        t.scheduleAtFixedRate(33);
        
        EpicLog.i("Main.applicationDidFinishLaunching() complete");
    }
    
    

    public static void main(String[] args) {
        UIApplication.main(args, null, Main.class);
    }
}
