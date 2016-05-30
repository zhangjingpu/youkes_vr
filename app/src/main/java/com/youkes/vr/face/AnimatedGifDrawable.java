/**
 * 优分享VR
 * copy right: youkes.com
 * author:xuming
 * licence:GPL2
 */
package com.youkes.vr.face;

import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class AnimatedGifDrawable extends AnimationDrawable {

	private int mCurrentIndex = 0;
	private UpdateListener mListener;

	public AnimatedGifDrawable(InputStream source,int itemWidth,int itemHeight, UpdateListener listener) {
		mListener = listener;
		GifDecoder decoder = new GifDecoder();
		decoder.read(source);
		// Iterate through the gif frames, add each as animation frame
		for (int i = 0; i < decoder.getFrameCount(); i++) {
			Bitmap bitmap = decoder.getFrame(i);
			BitmapDrawable drawable = new BitmapDrawable(bitmap);
			// Explicitly set the bounds in order for the frames to display
			int w=bitmap.getWidth();
			int h=bitmap.getHeight();
			w=itemWidth;
			h=itemHeight;
			drawable.setBounds(0, 0, w, h);
			addFrame(drawable, decoder.getDelay(i));
			if (i == 0) {
				// Also set the bounds for this container drawable
				setBounds(0, 0, w, h);
			}
		}
	}

	/**
	 * Naive method to proceed to next frame. Also notifies listener.
	 */
	public void nextFrame() {
		mCurrentIndex = (mCurrentIndex + 1) % getNumberOfFrames();
		if (mListener != null)
			mListener.update();
	}

	/**
	 * Return display duration for current frame
	 */
	public int getFrameDuration() {
		return getDuration(mCurrentIndex);
	}

	/**
	 * Return drawable for current frame
	 */
	public Drawable getDrawable() {
		return getFrame(mCurrentIndex);
	}

	/**
	 * Interface to notify listener to update/redraw Can't figure out how to
	 * invalidate the drawable (or span in which it sits) itself to force redraw
	 */
	public interface UpdateListener {
		void update();
	}

}