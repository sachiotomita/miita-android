package com.naoto.yamaguchi.miita.imagefetcher;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Bitmap Worker Task.
 * After complete Bitmap load, set {@ImageView}.
 *
 * Created by naoto on 2016/11/26.
 */

public final class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {
    private final WeakReference<ImageView> imageViewWeakReference;
    private final int width;
    private final int height;
    private String path;

    public BitmapWorkerTask(ImageView imageView) {
        this.imageViewWeakReference = new WeakReference<ImageView>(imageView);
        this.width = imageView.getWidth();
        this.height = imageView.getHeight();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        try {
            final String urlString = strings[0];
            final URL imageUrl = new URL(urlString);
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
        if (this.imageViewWeakReference != null && bitmap != null) {
            final ImageView imageView = this.imageViewWeakReference.get();
            if (imageView != null) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }
}
