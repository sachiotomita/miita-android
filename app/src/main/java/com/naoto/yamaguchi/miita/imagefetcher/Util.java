package com.naoto.yamaguchi.miita.imagefetcher;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/**
 * ImageFetcher Util class.
 *
 * Created by naoto on 2016/11/26.
 */

final class Util {
    public static BitmapLoaderTask getTaskFromImageView(ImageView imageView) {
        if (imageView != null) {
            final Drawable drawable = imageView.getDrawable();
            if (drawable instanceof BitmapLoaderDrawable) {
                final BitmapLoaderDrawable workerDrawable =
                        (BitmapLoaderDrawable)drawable;
                return workerDrawable.getBitmapLoaderTask();
            }
        }

        return null;
    }

    public static boolean cancelPotentialWork(String urlString, ImageView imageView) {
        final BitmapLoaderTask task = Util.getTaskFromImageView(imageView);

        if (task != null) {
            final String executingUrlString = task.getUrlString();
            if (executingUrlString.equals(urlString)) {
                return false;
            } else {
                task.cancel(true);
                return true;
            }
        }

        return true;
    }

    private Util() {}
}
