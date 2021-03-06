package com.naoto.yamaguchi.miita.imagefetcher;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Bitmap Loader Task extends {@AsyncTask}.
 *
 * Created by naoto on 2016/11/26.
 */

final class BitmapLoaderTask extends AsyncTask<Void, Void, Bitmap> {
    public interface onLoadListener {
        void onComplete(@NonNull String urlString, @Nullable Bitmap bitmap);
    }

    private final String urlString;
    private final WeakReference<ImageView> imageViewWeakReference;
    private final onLoadListener listener;

    public BitmapLoaderTask(String urlString, ImageView imageView, onLoadListener listener) {
        this.urlString = urlString;
        this.imageViewWeakReference = new WeakReference<ImageView>(imageView);
        this.listener = listener;
    }

    public String getUrlString() {
        return this.urlString;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Bitmap doInBackground(Void... voids) {
        try {
            final URL imageUrl = new URL(this.urlString);
            final InputStream stream = imageUrl.openStream();
            final Bitmap bitmap = BitmapFactory.decodeStream(stream);
            return bitmap;
        } catch (MalformedURLException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (isCancelled()) {
            bitmap = null;
        }

        if (this.imageViewWeakReference != null && bitmap != null) {
            final ImageView imageView = this.imageViewWeakReference.get();
            final BitmapLoaderTask task = Util.getTaskFromImageView(imageView);
            if (imageView != null && task == this) {
                imageView.setImageBitmap(bitmap);
            }
        }

        this.loadComplete(bitmap);
    }

    private void loadComplete(@Nullable Bitmap bitmap) {
        if (this.listener != null) {
            this.listener.onComplete(this.urlString, bitmap);
        }
    }
}
