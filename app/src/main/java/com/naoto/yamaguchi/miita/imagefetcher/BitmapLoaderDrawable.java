package com.naoto.yamaguchi.miita.imagefetcher;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import java.lang.ref.WeakReference;

/**
 * {@BitmapLoaderDrawable} reference {@BitmapLoaderTask}.
 *
 * Created by naoto on 2016/11/26.
 */

final class BitmapLoaderDrawable extends BitmapDrawable {
    private final WeakReference<BitmapLoaderTask> bitmapLoaderTaskWeakReference;

    public BitmapLoaderDrawable(Resources res, Bitmap bitmap, BitmapLoaderTask task) {
        super(res, bitmap);
        this.bitmapLoaderTaskWeakReference = new WeakReference<BitmapLoaderTask>(task);
    }

    public BitmapLoaderTask getBitmapLoaderTask() {
        return this.bitmapLoaderTaskWeakReference.get();
    }
}
