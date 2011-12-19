package com.epic.framework.implementation;

import com.epic.framework.common.Ui.EpicDialogBuilder;
import com.epic.framework.common.Ui.EpicDialogBuilder.EpicDialogButton;
import com.epic.framework.common.Ui.EpicPlatform;
import com.realcasualgames.words.Challenge;
import com.realcasualgames.words.ScreenTrophyRoomDetails;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.widget.EditText;

public class EpicDialogImplementation {
	public static Object show(EpicDialogBuilder epicDialogBuilder) {
		Builder builder = new Builder(EpicAndroidActivity.getCurrentAndroidActivity());
		
		if(epicDialogBuilder.textInput != null) {
			final EditText inputText = (EditText) epicDialogBuilder.textInput.getAndroidView();
			builder.setView(inputText);
		}
		
		// Message
		if(epicDialogBuilder.getMessage() != null) {
			builder.setMessage(epicDialogBuilder.getMessage());
		}
		
		if(epicDialogBuilder.getTitle() != null) {
			builder.setTitle(epicDialogBuilder.getTitle());
		}
		
		// Positive Button
		final EpicDialogButton positiveButton = epicDialogBuilder.getPositiveButton();
		if(positiveButton != null) {
			builder.setPositiveButton(positiveButton.text, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					if(positiveButton.action != null) {
						positiveButton.action.onClick();
					}
					dialog.dismiss();
				}
			});
		}
		
		// Negative Button
		final EpicDialogButton negativeButton = epicDialogBuilder.getNegativeButton();
		if(negativeButton != null) {
			builder.setNegativeButton(negativeButton.text, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					if(negativeButton.action != null) {
						negativeButton.action.onClick();
					}
					dialog.dismiss();
				}
			});
		}
		
		// Neutral Button
		final EpicDialogButton neutralButton = epicDialogBuilder.getNeutralButton();
		if(neutralButton != null) {
			builder.setNeutralButton(neutralButton.text, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					if(neutralButton.action != null) {
						neutralButton.action.onClick();
					}
					dialog.dismiss();
				}
			});
		}
		
		// Cancellable
		builder.setCancelable(epicDialogBuilder.isCancellable());
		
		if(epicDialogBuilder.isModal()) {
			// DDOPSON-2011-06-06 - calling builder.show() does NOT block, whereas create() followed by show() DOES block.  Not very obvious.
			AlertDialog dialog = builder.create();
			dialog.show();
			return dialog;
		}
		else {
			return builder.show();
		}
	}
	
	public static void dismissDialog(Object dialogObject) {
		Dialog dialog = (Dialog)dialogObject;
		dialog.dismiss();
	}

	public static int ask(String string, String[] challenges, int startAt) {
		AlertDialog.Builder alert = new AlertDialog.Builder(EpicAndroidActivity.getCurrentAndroidActivity());

		alert.setTitle(string);
		
		alert.setSingleChoiceItems(challenges, startAt, new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				if(which >= 0) {
					EpicPlatform.changeScreen(new ScreenTrophyRoomDetails(Challenge.challenges[which]));
				}
			}
		});
		
		alert.setNeutralButton("Back", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		
		alert.show();
		
		return -1;
	}
}
