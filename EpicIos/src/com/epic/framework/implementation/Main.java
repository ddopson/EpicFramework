/*
 * XMLVM demo template of Hello World application
 */
package com.epic.framework.implementation;

import java.util.ArrayList;

import org.xmlvm.iphone.UIApplication;
import org.xmlvm.iphone.UIApplicationDelegate;
import org.xmlvm.iphone.UINavigationController;
import org.xmlvm.iphone.UIScreen;
import org.xmlvm.iphone.UIViewController;
import org.xmlvm.iphone.UIWindow;

import com.epic.config.EpicProjectConfig;
import com.epic.framework.common.Ui.EpicPlatform;
import com.epic.framework.common.Ui.EpicTimer;
import com.epic.framework.common.util.EpicLog;
import com.realcasualgames.words.PlayerState;

public class Main extends UIApplicationDelegate {
	public static UIWindow window;
	public static UINavigationController navc;

    @Override
    public void applicationDidFinishLaunching(UIApplication app) {
        window = new UIWindow(UIScreen.mainScreen().getBounds());
        
        EpicUiViewController gameController = new EpicUiViewController();
        
        navc = new UINavigationController(gameController);
        navc.setNavigationBarHidden(true, false);
        window.setRootViewController(navc);
        window.addSubview(gameController.getView());
        window.makeKeyAndVisible();
        
        EpicProjectConfig.onApplicationStart();
        
        EpicLog.i("Main.applicationDidFinishLaunching() complete");
    }
    
    

    public static void main(String[] args) {
        UIApplication.main(args, null, Main.class);
    }
}
