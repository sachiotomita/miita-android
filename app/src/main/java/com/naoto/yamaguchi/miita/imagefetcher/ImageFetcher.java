package com.naoto.yamaguchi.miita.imagefetcher;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.naoto.yamaguchi.miita.R;

/**
 * Image Fetcher Client Class.
 *
 * Created by naoto on 2016/11/26.
 */

public final class ImageFetcher {
    private static ImageFetcher instance = null;
    private Context context;
    private Bitmap loadingBitmap;

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

    public ImageFetcher setLoadingBitmap(Bitmap bitmap) {
        this.loadingBitmap = bitmap;
        return this;
    }

    public void load(String urlString, ImageView imageView) {
        if (this.context == null) {
            throw new RuntimeException("should be call setContext().");
        }

        // Http
        final BitmapWorkerTask task = new BitmapWorkerTask(imageView);
        final BitmapWorkerDrawable drawable = new BitmapWorkerDrawable(
                this.context.getResources(), this.getLoadingBitmap(), task);
        imageView.setImageDrawable(drawable);
        task.execute(urlString);
    }

    private Bitmap getLoadingBitmap() {
        if (this.loadingBitmap != null) {
            return this.loadingBitmap;
        }
        return BitmapFactory.decodeResource(this.context.getResources(),
                R.drawable.ic_loading_image_48px);
    }
}
