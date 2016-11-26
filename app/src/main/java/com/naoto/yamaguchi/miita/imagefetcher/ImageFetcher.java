package com.naoto.yamaguchi.miita.imagefetcher;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.naoto.yamaguchi.miita.R;

/**
 * Image Fetcher Client Class.
 *
 * Reference
 * - http://y-anz-m.blogspot.jp/2012/08/android-bitmap.html
 * - http://y-anz-m.blogspot.jp/2012/08/androidbitmap.html
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

    public void fetch(String urlString, ImageView imageView) {
        if (this.context == null) {
            throw new RuntimeException("should be call setContext().");
        }

        // Http
        this.load(urlString, imageView);
    }

    private void load(String urlString, ImageView imageView) {
        if (Util.cancelPotentialWork(urlString, imageView)) {
            final BitmapLoaderTask task = new BitmapLoaderTask(imageView);
            final BitmapLoaderDrawable drawable = new BitmapLoaderDrawable(
                    this.context.getResources(), this.getLoadingBitmap(), task);
            imageView.setImageDrawable(drawable);
            task.execute(urlString);
        }
    }

    private Bitmap getLoadingBitmap() {
        if (this.loadingBitmap != null) {
            return this.loadingBitmap;
        }
        return BitmapFactory.decodeResource(this.context.getResources(),
                R.drawable.ic_loading_image_48px);
    }
}
