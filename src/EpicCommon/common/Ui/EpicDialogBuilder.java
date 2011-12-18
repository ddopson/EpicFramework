package com.epic.framework.common.Ui;

import com.epic.framework.common.Ui.EpicDialogBuilder.EpicRowSelectListener;
import com.epic.framework.common.util.EpicFail;
import com.epic.framework.implementation.Ui.EpicDialogImplementation;
import com.epic.framework.implementation.Ui.EpicTextEntryWidget;

public class EpicDialogBuilder {
	public class EpicProgressDialog {

	}

	private String message = null;
	private String title = null;
	private EpicDialogButton positiveButton = null;
	private EpicDialogButton negativeButton = null;
	private EpicDialogButton neutralButton = null;
	private boolean isModal = true;
	private boolean cancelable = true;
	private Object dialogObject = null;
	public EpicTextEntryWidget textInput = null;
	private String[] listItems;
	private int listStartAt;
	private EpicRowSelectListener listListener;

	public static class EpicDialogButton {
		public String text;
		public EpicClickListener action;
		public EpicDialogButton(String text, EpicClickListener action) {
			this.text = text;
			this.action = action;
		}
	}
	
	public static abstract class EpicRowSelectListener {
		public abstract void onSelectRow(int row);
	}

	public EpicDialogBuilder() { }

	public EpicDialogBuilder setMessage(String message) {
		this.message = message;
		return this;
	}
	
	public EpicDialogBuilder setTitle(String title) {
		this.title = title;
		return this;
	}
	
	public EpicDialogBuilder setCancelable(boolean isCancelable) {
		this.cancelable = isCancelable;
		return this;
	}
	public EpicDialogBuilder setPositiveButton(String text, final EpicClickListener action) {
		this.positiveButton = new EpicDialogButton(text, action);
		return this;
	}
	public EpicDialogBuilder setNegativeButton(String text, final EpicClickListener action) {
		this.negativeButton = new EpicDialogButton(text, action);
		return this;
	}
	public EpicDialogBuilder setNeutralButton(String text, final EpicClickListener action) {
		this.neutralButton = new EpicDialogButton(text, action);
		return this;
	}
	
	public void show() {
		if(dialogObject != null) {
			throw EpicFail.framework("show() called more than once");
		}
		this.isModal = true;
		this.dialogObject = EpicDialogImplementation.show(this);
		EpicFail.assertNotNull(this.dialogObject);
	}
	
	public void showModal() {
		if(dialogObject != null) {
			throw EpicFail.framework("show() called more than once");
		}
		this.isModal = true;
		this.dialogObject = EpicDialogImplementation.show(this);
		EpicFail.assertNotNull(this.dialogObject);
	}

	public String getMessage() {
		return message;
	}
	
	public EpicDialogButton getPositiveButton() {
		return positiveButton;
	}

	public EpicDialogButton getNegativeButton() {
		return negativeButton;
	}
	
	public EpicDialogButton getNeutralButton() {
		return neutralButton;
	}
	
	public boolean isCancellable() {
		return cancelable;
	}

	public boolean isModal() {
		return isModal;
	}
	
	public void dismiss() {
		EpicFail.assertNotNull(this.dialogObject);
		EpicDialogImplementation.dismissDialog(this.dialogObject);
	}

	public String getTitle() {
		return title;
	}

	public EpicDialogBuilder setTextInput(EpicTextEntryWidget textEntry) {
		this.textInput = textEntry;
		return this;
	}

	public static int ask(String string, String[] challenges, int startAt) {
		return EpicDialogImplementation.ask(string, challenges, startAt);
	}

	public void setList(String[] listItems, int listStartAt,
			EpicRowSelectListener listListener) {
		this.listItems = listItems;
		this.listStartAt = listStartAt;
		this.listListener = listListener;
	}
}
