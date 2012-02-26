package com.epic.framework.implementation;

import org.xmlvm.iphone.UIAlertView;
import org.xmlvm.iphone.UIAlertViewDelegate;

import com.epic.framework.common.Ui.EpicDialogBuilder;

public class EpicDialogImplementation {

	public static void dismissDialog(Object dialogObject) {
		UIAlertView d = (UIAlertView) dialogObject;
	}

	public synchronized static Object show(final EpicDialogBuilder epicDialogBuilder) {	
		UIAlertView dialog = new UIAlertView(
				epicDialogBuilder.getTitle(),
				epicDialogBuilder.getMessage(), 
				new UIAlertViewDelegate() {
					public void clickedButtonAtIndex(UIAlertView alertView, int buttonIndex) {
						switch(buttonIndex) {
							case 0:
								epicDialogBuilder.getNegativeButton().action.onClick();
								break;
							case 1:
								epicDialogBuilder.getPositiveButton().action.onClick();
								break;
						}
					}
				},				 
				epicDialogBuilder.getNegativeButton().text);
		
		dialog.addButtonWithTitle(epicDialogBuilder.getPositiveButton().text);
		dialog.show();
		
		return dialog;
	}

	public static int ask(String string, String[] challenges, int startAt) {
		return 0;
	}
}