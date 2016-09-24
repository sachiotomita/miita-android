package com.naoto.yamaguchi.miita.model;

import android.content.Context;

/**
 * Created by naoto on 2016/09/24.
 */

// FIXME: contextが必要かどうか
public final class ModelSelector {
    public static AllItemModel getAllItemModel(Context context) {
        return new AllItemModel(context);
    }

    public static CurrentUserModel getCurrentUserModel(Context context) {
        return new CurrentUserModel(context);
    }

    public static FollowTagModel getFollowTagModel(Context context) {
        return new FollowTagModel(context);
    }

    public static ItemModel getItemModel(Context context) {
        return new ItemModel(context);
    }

    public static StockItemModel getStockItemModel(Context context) {
        return new StockItemModel(context);
    }

    public static TagItemModel getTagItemModel(Context context) {
        return new TagItemModel(context);
    }
}
