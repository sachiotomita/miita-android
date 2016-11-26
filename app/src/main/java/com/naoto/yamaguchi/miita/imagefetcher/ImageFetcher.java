package com.naoto.yamaguchi.miita.imagefetcher;

import android.content.Context;
import android.widget.ImageView;

/**
 * Image Fetcher Client Class.
 *
 * Created by naoto on 2016/11/26.
 */

public final class ImageFetcher {
    private static ImageFetcher instance = null;
    private Context context;

    public ImageFetcher getInstance() {
        if (instance == null) {
            instance = new ImageFetcher();
        }
        return instance;
    }

    public ImageFetcher setContext(Context context) {
        this.context = context;
        return this;
    }

    public void load(String urlString, ImageView imageView) {
        // Http
        // TODO: set loading drawable.
        final BitmapWorkerTask task = new BitmapWorkerTask(imageView);
        final BitmapWorkerDrawable drawable = new BitmapWorkerDrawable(
                this.context.getResources(), null, task);
        imageView.setImageDrawable(drawable);
        task.execute(urlString);
    }
}
