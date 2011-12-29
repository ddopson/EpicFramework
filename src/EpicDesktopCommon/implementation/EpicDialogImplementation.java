package com.epic.framework.implementation

import javax.swing.JOptionPane;

import com.epic.framework.util.EpicLog;


public class EpicDialogImplementation {
	
	public static void dismissDialog(Object dialogObject) {
		EpicLog.i("No need to dismiss with showConfirmDialog--only supports non-dismissable messages.");
	}

	public static Object show(EpicDialogBuilder epicDialogBuilder) {
		int selection = -1;
		
		if(epicDialogBuilder.getPositiveButton() != null && epicDialogBuilder.getNegativeButton() != null) {
			selection = JOptionPane.showConfirmDialog(null, epicDialogBuilder.getMessage(), "Word Farm", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, epicDialogBuilder.getMessage(), "Word Farm", JOptionPane.INFORMATION_MESSAGE);
		}
		
		if(selection == JOptionPane.OK_OPTION) {
			if(epicDialogBuilder.getPositiveButton() != null && 
					epicDialogBuilder.getPositiveButton().action != null) 
				epicDialogBuilder.getPositiveButton().action.onClick();
		} else if(selection == JOptionPane.CANCEL_OPTION) {
			if(epicDialogBuilder.getNegativeButton() != null && 
					epicDialogBuilder.getNegativeButton().action != null) 
				epicDialogBuilder.getNegativeButton().action.onClick();
		}
		
		// TODO: disgusting hack to avoid assert :)
		return new Object();
	}

	public static int ask(String string, String[] challenges) {
		return -1;
	}

	public static int ask(String string, String[] challenges, int startAt) {
		// TODO Auto-generated method stub
		return 0;
	}
}
