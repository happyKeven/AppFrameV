package com.keven.library.utils;

import android.os.Handler;
import android.os.HandlerThread;

import java.util.concurrent.CountDownLatch;

/**
 * @author bojuzi.com
 * @date 2013-6-25
 */
public class SubTaskHelper {

	private static SubTaskHelper mHelper;

	private MyHandlerThread mThread;

	private boolean mIsQuited;

	private CountDownLatch mLatch = new CountDownLatch(1);

	private SubTaskHelper() {
		mThread = new MyHandlerThread("SubTaskHelper-Thread");
		mThread.start();
	}

	public static SubTaskHelper getInstance() {
		if (mHelper == null) {
			synchronized (SubTaskHelper.class) {
				if (mHelper == null) {
					mHelper = new SubTaskHelper();
				}
			}
		}
		return mHelper;
	}

	public void executeBackground(Runnable task) {
		if (mIsQuited) {
			throw new IllegalStateException(
					"the looper is quited, call getInstance() to retrieve new SubTaskHelper instance");
		}
		mThread.post(task);
	}
	
	public void executeBackground(Runnable task,long delay) {
		if (mIsQuited) {
			throw new IllegalStateException(
					"the looper is quited, call getInstance() to retrieve new SubTaskHelper instance");
		}
		mThread.postDelay(task, delay);
	}

	public void removeCallbacks(Runnable task) {
		mThread.removeCallbacks(task);
	}

	public void quit() {
		mIsQuited = true;
		mThread.quit();
		mHelper = null;
	}

	private class MyHandlerThread extends HandlerThread {
		Handler handler = null;

		public MyHandlerThread(String name) {
			super(name);
		}

		protected void onLooperPrepared() {
			handler = new Handler();
			mLatch.countDown();
		}

		public void post(Runnable task) {
			ensureHandler();
			handler.post(task);
		}
		
		public void postDelay(Runnable task, long delayMillis) {
			ensureHandler();
			handler.postDelayed(task,delayMillis);
		}

		public void removeCallbacks(Runnable task) {
			ensureHandler();
			handler.removeCallbacks(task);
		}

		private void ensureHandler() {
			if (handler == null) {
				try {
					mLatch.await();
				} catch (InterruptedException e) {
				}
			}
		}

	}

}
