package com.naoto.yamaguchi.miita.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;

/**
 * Model Instance Factory.
 *
 * Created by naoto on 2016/09/29.
 */

public final class ModelFactory {
  @UiThread
  public static AllItemModel getAllItemModel(@NonNull Context context) {
    return new AllItemModel(context);
  }
  @UiThread
  public static CurrentUserModel getCurrentUserModel(@NonNull Context context) {
    return new CurrentUserModel(context);
  }
  @UiThread
  public static FollowTagModel getFollowTagModel(@NonNull Context context) {
    return new FollowTagModel(context);
  }
  @UiThread
  public static ItemModel getItemModel(@NonNull Context context) {
    return new ItemModel(context);
  }
  @UiThread
  public static StockItemModel getStockItemModel(@NonNull Context context) {
    return new StockItemModel(context);
  }
  @UiThread
  public static TagItemModel getTagItemModel(@NonNull Context context) {
    return new TagItemModel(context);
  }
}
