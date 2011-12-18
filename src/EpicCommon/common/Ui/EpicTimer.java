package com.epic.framework.common.Ui;

import com.epic.framework.common.util.EpicLog;
import com.epic.framework.implementation.EpicTimerImplementation;

public abstract class EpicTimer {
	private int expectedMillis = -1;
	public int n = 0;
	private long lastFireMillis;
	private PlatformTimerInterface platformTimer = null;
	private boolean paused = false;
	public static boolean raceUnhinged = false;

	//	private boolean hasHadAPaint

	public EpicTimer() { 

	}

	public void onRawTick() {
		if(paused || platformTimer == null) {
			// skip
			//			EpicLog.d("Skipping Tick...");
		}
		else {
			if(expectedMillis < 0) {
				this.onTimerTick(n++);
			}
			else {
				long currentMillis = System.currentTimeMillis();
				if(currentMillis - lastFireMillis > expectedMillis / 2) {
					// DDOPSON-2011-06-11 -- Just in case, this guarantees the timer won't cause racing threads.
					this.onTimerTick(n++);						
					lastFireMillis = currentMillis;
				}
			}
		}
	}

	public void doTickNow() {
		this.onTimerTick(n++);
	}
	public static interface PlatformTimerInterface {
		public void cancel();
	}

	public EpicTimer schedule(int delay) {
		cancel();
		platformTimer = EpicTimerImplementation.createDelayTimer(this, delay);
		paused = false;
		return this;
	}
	public EpicTimer scheduleAtFixedRate(int milliseconds) {
		cancel();
		platformTimer = EpicTimerImplementation.createRateTimer(this, milliseconds);
		this.expectedMillis = milliseconds;
		paused = false;
		return this;
	}

	public void cancel() {
		if(this.platformTimer != null) {
			EpicLog.i("Cancelling Timer. t=" + n + ", this=" + this);
			this.platformTimer.cancel();
			this.platformTimer = null;
		}
		else {
			EpicLog.i("Cancelling Timer (multiple). t=" + n + ", this=" + this);

		}
	}

	protected abstract void onTimerTick(int n);

	public void pause() {
		paused = true;
	}

	public void resume() {
		paused = false;
	}

	public boolean isPaused() {
		return paused;
	}

	public static void onPaintFinish() {
		if(raceUnhinged) {

		}
	}
}
