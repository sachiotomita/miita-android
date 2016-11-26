package com.naoto.yamaguchi.miita.imagefetcher;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import java.lang.ref.WeakReference;

/**
 * {@BitmapDrawable} reference {@BitmapWorkerTask}.
 *
 * Created by naoto on 2016/11/26.
 */

final class BitmapWorkerDrawable extends BitmapDrawable {
    private final WeakReference<BitmapWorkerTask> bitmapWorkerTaskWeakReference;

    public BitmapWorkerDrawable(Resources res, Bitmap bitmap, BitmapWorkerTask task) {
        super(res, bitmap);
        this.bitmapWorkerTaskWeakReference = new WeakReference<BitmapWorkerTask>(task);
    }

    public BitmapWorkerTask getBitmapWorkerTask() {
        return this.bitmapWorkerTaskWeakReference.get();
    }
}
