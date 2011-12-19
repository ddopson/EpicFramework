package com.epic.framework.implementation;

import com.epic.framework.common.Ui.EpicTimer;
import com.epic.framework.common.Ui.EpicTimer.PlatformTimerInterface;

public class EpicTimerImplementation {
	public static PlatformTimerInterface createDelayTimer(EpicTimer epicTimer, int delay) {
		return new PlatformTimerInterface() {
			public void cancel() {
			}
		};
	}
	public static PlatformTimerInterface createRateTimer(EpicTimer epicTimer, int period) {
		return new PlatformTimerInterface() {
			public void cancel() {
			}
		};
	}
}
