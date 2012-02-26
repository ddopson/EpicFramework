package com.epic.framework.implementation;

import java.util.Timer;
import java.util.TimerTask;

import com.epic.framework.common.Ui.EpicTimer;
import com.epic.framework.common.Ui.EpicTimer.PlatformTimerInterface;

import android.os.Handler;
import android.os.Message;

public abstract class EpicTimerImplementation {

	private static class AndroidTimer implements PlatformTimerInterface {
		private final Timer timer = new Timer();
		private final TimerTask timerTask = new TimerTask() {		
			public void run() {
				handler.sendEmptyMessage(0);
			}
		};
		private final EpicTimer epicTimer;
		private final Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				epicTimer.onRawTick();
			}		
		};
		public AndroidTimer(EpicTimer epicTimer, int delay, int period) {
			this.epicTimer = epicTimer;
			timer.schedule(timerTask, delay, period);
		}
		
		public void cancel() {
			timer.cancel();
			timerTask.cancel();
		}		
	}
	
	public static PlatformTimerInterface createDelayTimer(EpicTimer epicTimer, int delay) {
		return new AndroidTimer(epicTimer, delay, 0);
	}

	public static PlatformTimerInterface createRateTimer(EpicTimer epicTimer, int milliseconds) {
		return new AndroidTimer(epicTimer, 0, milliseconds);
	}
}
