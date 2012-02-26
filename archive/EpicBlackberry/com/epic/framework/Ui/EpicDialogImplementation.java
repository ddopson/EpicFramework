package com.epic.framework.Ui;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Keypad;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.SeparatorField;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.PopupScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;

public class EpicDialogImplementation implements FieldChangeListener {

	PopupScreen theDialog;
	ButtonField positiveButton = null;
	EpicClickListener positiveAction = null;
	ButtonField negativeButton = null;
	EpicClickListener negativeAction = null;
	ButtonField neutralButton = null;
	EpicClickListener neutralAction = null;
	
	public static void dismissDialog(Object dialogObject) {
		((EpicDialogImplementation)dialogObject).theDialog.close();
	}

	public synchronized static Object show(EpicDialogBuilder epicDialogBuilder) {		
		EpicDialogImplementation impl = new EpicDialogImplementation();
		
		if(epicDialogBuilder.getPositiveButton() != null && epicDialogBuilder.getPositiveButton().action != null) {
			impl.positiveButton = new ButtonField(epicDialogBuilder.getPositiveButton().text, ButtonField.CONSUME_CLICK);
			impl.positiveButton.setChangeListener(impl);
			impl.positiveAction = epicDialogBuilder.getPositiveButton().action;
		}
		
		if(epicDialogBuilder.getNeutralButton() != null && epicDialogBuilder.getNeutralButton().action != null) {
			impl.neutralButton = new ButtonField(epicDialogBuilder.getNeutralButton().text, ButtonField.CONSUME_CLICK);
			impl.neutralButton.setChangeListener(impl);
			impl.neutralAction = epicDialogBuilder.getNeutralButton().action;
		}
		
		if(epicDialogBuilder.getNegativeButton() != null && epicDialogBuilder.getNegativeButton().action != null) {
			impl.negativeButton = new ButtonField(epicDialogBuilder.getNegativeButton().text, ButtonField.CONSUME_CLICK);
			impl.negativeButton.setChangeListener(impl);
			impl.negativeAction = epicDialogBuilder.getNegativeButton().action;
		}
		
		VerticalFieldManager vfm = new VerticalFieldManager();
		
		if(epicDialogBuilder.getTitle() != null) {
			vfm.add(new LabelField(epicDialogBuilder.getTitle()));
			vfm.add(new SeparatorField());
		}
		
		vfm.add(new LabelField(epicDialogBuilder.getMessage()));
		if(epicDialogBuilder.textInput != null) {
			vfm.add(epicDialogBuilder.textInput.getBlackberryField());
		}
		vfm.add(new SeparatorField());
		HorizontalFieldManager hfm = new HorizontalFieldManager();
		if(impl.positiveButton != null && impl.positiveAction != null) {
			hfm.add(impl.positiveButton);
		}
		if(impl.neutralButton != null && impl.neutralAction != null) {
			hfm.add(impl.neutralButton);
		}
		if(impl.negativeButton != null && impl.negativeAction != null) {
			hfm.add(impl.negativeButton);
		}

		vfm.add(hfm);

		final boolean c = epicDialogBuilder.isCancellable();
		impl.theDialog = new PopupScreen(vfm) {
			protected boolean keyDown(int keycode, int status)
			{
			     if(Keypad.key(keycode) == Keypad.KEY_ESCAPE)
			     {
			         return !c;
			     }

			     return false;
			}
		};
		
		synchronized (UiApplication.getEventLock()) {
			UiApplication.getUiApplication().pushScreen(impl.theDialog);	
		}
				
		return impl;
	}

	public void fieldChanged(Field field, int context) {
		if(field == this.positiveButton) {
			positiveAction.onClick();
			this.theDialog.close();
		} else if(field == this.negativeButton) {
			negativeAction.onClick();
			this.theDialog.close();
		}
	}

	public static int ask(String string, String[] challenges, int startAt) {
		return Dialog.ask(string, challenges, startAt);
	}

}