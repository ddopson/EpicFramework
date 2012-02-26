package com.epic.framework.Ui;

import java.util.Timer;
import java.util.TimerTask;

import com.epic.framework.Ui.EpicTimer.PlatformTimerInterface;

import net.rim.device.api.system.Application;

public class EpicTimerImplementation {
	private static class BlackberryTimer implements PlatformTimerInterface{
		private final Timer timer = new Timer();
		private final TimerTask timerTask = new TimerTask() {
			public void run() {
				synchronized(Application.getEventLock()) {
					epicTimer.onRawTick();
				}
			}
		};
		private final EpicTimer epicTimer;
		public BlackberryTimer(EpicTimer epicTimer) {
			this.epicTimer = epicTimer;
		}
		public void cancel() {
			this.timer.cancel();
			this.timerTask.cancel();
		}
	}
	
	public static PlatformTimerInterface createDelayTimer(EpicTimer epicTimer, int delay) {
		BlackberryTimer timer = new BlackberryTimer(epicTimer);
		timer.timer.schedule(timer.timerTask, delay);
		return timer;
	}
	public static PlatformTimerInterface createRateTimer(EpicTimer epicTimer, int period) {
		BlackberryTimer timer = new BlackberryTimer(epicTimer);
		timer.timer.schedule(timer.timerTask, 0, period);
		return timer;
	}
}
