package com.naoto.yamaguchi.miita.task;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by naoto on 16/07/05.
 */
public final class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

    private Context context;
    private ImageView imageView;

    public DownloadImageTask(Context context, ImageView imageView) {
        this.context = context;
        this.imageView = imageView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        // TODO: argsがcontextだとおかしいっぽい
        synchronized (this.context) {
            try {
                String urlString = strings[0];
                URL imageUrl = new URL(urlString);
                InputStream stream = imageUrl.openStream();
                Bitmap bitmap = BitmapFactory.decodeStream(stream);
                return bitmap;
            } catch (IOException e) {
                return null;
            }
        }
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (bitmap != null) {
            this.imageView.setImageBitmap(bitmap);
        }
    }
}
