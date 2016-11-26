package com.naoto.yamaguchi.miita.imagefetcher;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/**
 * ImageFetcher Util class.
 *
 * Created by naoto on 2016/11/26.
 */

final class Util {
    public static BitmapWorkerTask getTaskFromImageView(ImageView imageView) {
        if (imageView != null) {
            final Drawable drawable = imageView.getDrawable();
            if (drawable instanceof BitmapWorkerDrawable) {
                final BitmapWorkerDrawable workerDrawable =
                        (BitmapWorkerDrawable)drawable;
                return workerDrawable.getBitmapWorkerTask();
            }
        }

        return null;
    }

    private Util() {};
}
