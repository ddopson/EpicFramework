package com.epic.framework.implementation;


import org.xmlvm.iphone.NSTimer;
import org.xmlvm.iphone.NSTimerDelegate;

import com.epic.framework.common.Ui.EpicTimer;
import com.epic.framework.common.Ui.EpicTimer.PlatformTimerInterface;

public class EpicTimerImplementation {
	public static PlatformTimerInterface createDelayTimer(EpicTimer epicTimer, int delay) {
		return new PlatformTimerInterface() {
			public void cancel() {
			}
		};
	}
	public static class TimerDelegateAdapter implements NSTimerDelegate {
		EpicTimer epicTimer;
		public TimerDelegateAdapter(EpicTimer epicTimer) {
			this.epicTimer = epicTimer;
		}
		public void timerEvent(NSTimer arg0) {
			this.epicTimer.onRawTick();
		}
	}

	public static PlatformTimerInterface createRateTimer(EpicTimer epicTimer, int milliseconds) {
		final NSTimer timer = NSTimer.scheduledTimerWithTimeInterval(milliseconds / 1000.0f, new TimerDelegateAdapter(epicTimer), null, true);
		return new PlatformTimerInterface() {
			public void cancel() {
				timer.release();
			}
		};
	}
}
