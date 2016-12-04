package com.naoto.yamaguchi.miita.imagefetcher;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
    private boolean isDebug;
    private Bitmap loadingBitmap;

    private final MemoryCache memoryCache;

    public static synchronized ImageFetcher getInstance() {
        if (instance == null) {
            instance = new ImageFetcher();
        }
        return instance;
    }

    private ImageFetcher() {
        this.isDebug = false;
        this.memoryCache = new MemoryCache();
    }

    public ImageFetcher setContext(Context context) {
        this.context = context;
        return this;
    }

    // TODO: debug mode. marking Bitmap.
    public ImageFetcher setDebug(boolean isDebug) {
        this.isDebug = isDebug;
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

        // from Memory Cache
        if (this.memoryCache.get(urlString) != null) {
            final Bitmap bitmap = this.memoryCache.get(urlString);
            imageView.setImageBitmap(bitmap);
            return;
        }

        // TODO: from Disk Cache

        // from Http Download
        this.load(urlString, imageView);
    }

    private void load(String urlString, ImageView imageView) {
        if (Util.cancelPotentialWork(urlString, imageView)) {
            final BitmapLoaderTask task = new BitmapLoaderTask(urlString, imageView,
                    this.loadListener());
            final BitmapLoaderDrawable drawable = new BitmapLoaderDrawable(
                    this.context.getResources(), this.getLoadingBitmap(), task);
            imageView.setImageDrawable(drawable);
            task.execute();
        }
    }

    private BitmapLoaderTask.onLoadListener loadListener() {
        return new BitmapLoaderTask.onLoadListener() {
            @Override
            public void onComplete(@NonNull String urlString, @Nullable Bitmap bitmap) {
                if (bitmap == null) {
                    return;
                }

                memoryCache.put(urlString, bitmap);
            }
        };
    }

    private Bitmap getLoadingBitmap() {
        if (this.loadingBitmap != null) {
            return this.loadingBitmap;
        }
        // TODO: use custom drawable.
        return BitmapFactory.decodeResource(this.context.getResources(),
                R.drawable.ic_menu_gallery);
    }
}
