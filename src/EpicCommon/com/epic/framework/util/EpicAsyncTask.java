package com.epic.framework.util;

public abstract class EpicAsyncTask<Params, Progress, Result> {
	public static final int CREATED = 0;
	public static final int RUNNING = 1;
	public static final int COMPLETED = 2;
	public static final int CANCELLED = 3;

	private int state = CREATED;
	private Object stateLock = new Object();
	private Thread thread;
	private Result result;

	public void cancel(boolean mayInterruptIfRunning) {
		synchronized(stateLock) {
			if(mayInterruptIfRunning && thread.isAlive()) {
				thread.interrupt();
			}
			state = CANCELLED;
		}
	}

	final EpicAsyncTask<Params, Progress, Result> execute(final Params... params) {
		thread = new Thread(new Runnable() {
			public void run() {
				result = doInBackground(params);
			}
		});
		thread.start();
		return this;
	}
	final Result get() {
		try {
			thread.wait();
		} catch (InterruptedException e) {
			EpicLog.e("got interrupted", e);
		}
		return result;
	}
	final boolean isCancelled() {
		return state == CANCELLED;
	}

	protected abstract Result doInBackground(Params... params);
	protected void onCancelled(Result result) { onCancelled(); }
	protected void onCancelled() { }
	protected void onPostExecute(Result result) { }
	protected void onPreExecute() { }


	//	public static void execute(Runnable runnable) { }
	//	final Result get(long timeout, TimeUnit unit) { }
	//	final AsyncTask.Status	 getStatus() { }
	//	protected void onProgressUpdate(Progress... values)
	//	final void publishProgress(Progress... values)
}
