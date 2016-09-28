package com.naoto.yamaguchi.miita.dao;

import android.support.annotation.UiThread;

/**
 * Created by naoto on 2016/09/28.
 */

public final class DaoFactory {
    @UiThread
    public static AllItemDao getAllItemDao() {
        return new AllItemDao();
    }

    @UiThread
    public static StockItemDao getStockItemDao() {
        return new StockItemDao();
    }

    @UiThread
    public static FollowTagDao getFollowTagDao() {
        return new FollowTagDao();
    }
}
