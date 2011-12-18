package com.epic.framework.Ui;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.SwingUtilities;

import com.epic.framework.Ui.EpicTimer.PlatformTimerInterface;

public abstract class EpicTimerImplementation {

	private static class DesktopTimer implements PlatformTimerInterface {
		private final Timer timer = new Timer();
		private final EpicTimer epicTimer;
		private final TimerTask timerTask = new TimerTask() {
			Runnable toRun = new Runnable() {
				public void run() {
					epicTimer.onRawTick();
				}
			};
			public void run() {
				SwingUtilities.invokeLater(toRun);
			}
		};
		public DesktopTimer(EpicTimer epicTimer, int delay, int period) {
			this.epicTimer = epicTimer;
			timer.schedule(timerTask, delay, period);
		}
		public void cancel() {
			timer.cancel();
			timerTask.cancel();
		}
	}
	public static PlatformTimerInterface createDelayTimer(EpicTimer epicTimer, int delay) {
		return new DesktopTimer(epicTimer, delay, 0);
	}

	public static PlatformTimerInterface createRateTimer(EpicTimer epicTimer, int milliseconds) {
		return new DesktopTimer(epicTimer, 0, milliseconds);
	}
}
