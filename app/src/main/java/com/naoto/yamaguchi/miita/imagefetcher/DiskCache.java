package com.naoto.yamaguchi.miita.imagefetcher;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Bitmap Disk Cache.
 * Reference: https://developer.android.com/training/displaying-bitmaps/cache-bitmap.html
 *
 * Created by naoto on 2016/12/03.
 */

// TODO: implement DiskLruCache class. WIP,,,.
final class DiskCache {
    private static final int DISK_CACHE_SIZE = 1024 * 1024 * 10; // 10MB
    private static final String DISK_CACHE_SUB_DIR = "mitta_bitmap";
    private final Context context;

    public DiskCache(Context context) {
        this.context = context;
    }

    // TODO: init, get, put

    private File getCacheDir(Context context, String uniqueName) {
        final String cachePath = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED ||
                !Environment.isExternalStorageRemovable() ?
                context.getExternalCacheDir().getPath() :
                context.getCacheDir().getPath();
        return new File(cachePath + File.separator + uniqueName);
    }
}
