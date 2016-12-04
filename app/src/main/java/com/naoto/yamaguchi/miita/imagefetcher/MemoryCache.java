package com.naoto.yamaguchi.miita.imagefetcher;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Image Memory Cache.
 *
 * Created by naoto on 2016/11/26.
 */

final class MemoryCache {
    final LruCache<String, Bitmap> cache;

    public MemoryCache() {
        this.cache = new LruCache<String, Bitmap>(this.getCacheSize()) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount() / 1024;
            }
        };
    }

    public Bitmap get(String key) {
        return this.cache.get(key);
    }

    public void put(String key, Bitmap bitmap) {
        if (this.get(key) == null) {
            this.cache.put(key, bitmap);
        }
    }

    // TODO: fix cache size
    // https://developer.android.com/training/displaying-bitmaps/cache-bitmap.html
    private int getCacheSize() {
        final int maxMemory = (int)(Runtime.getRuntime().maxMemory() / 1024);
        return maxMemory / 8;
    }
}
