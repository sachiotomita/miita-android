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
 * Bitmap Loader Task extends {@AsyncTask}.
 *
 * Created by naoto on 2016/11/26.
 */

final class BitmapLoaderTask extends AsyncTask<String, Void, Bitmap> {
    private final WeakReference<ImageView> imageViewWeakReference;
    private String urlString;

    public BitmapLoaderTask(ImageView imageView) {
        this.imageViewWeakReference = new WeakReference<ImageView>(imageView);
    }

    public String getUrlString() {
        return this.urlString;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        try {
            this.urlString = strings[0];
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
    }
}
