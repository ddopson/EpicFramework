package com.epic.framework.implementation.Ui;

import com.epic.framework.common.Ui.EpicClickListener;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

public class EpicNativeVideoWidget extends EpicNativeWidget implements OnCompletionListener {
	VideoView t;
	Uri location;
	private EpicClickListener listener;

	public EpicNativeVideoWidget(String stringLocation, EpicClickListener l) {
		this.listener = l;
		t = new VideoView(EpicAndroidActivity.getCurrentAndroidActivity());
		t.setOnCompletionListener(this);
		this.location = Uri.parse(stringLocation);
		t.setVideoURI(this.location);
        t.setMediaController(new MediaController(EpicAndroidActivity.getCurrentAndroidActivity()));
//      t.requestFocus();
        t.start();
	}

	View getAndroidView() {
		return t;
	}

	public void onCompletion(MediaPlayer arg0) {
		this.listener.onClick();
	}
}
