package com.naoto.yamaguchi.miita.model;

import android.content.Context;

import com.naoto.yamaguchi.miita.api.API;
import com.naoto.yamaguchi.miita.api.Callback;
import com.naoto.yamaguchi.miita.api.HttpException;
import com.naoto.yamaguchi.miita.api.Method;
import com.naoto.yamaguchi.miita.api.Response;
import com.naoto.yamaguchi.miita.model.base.OnModelListener;
import com.naoto.yamaguchi.miita.service.ItemService;
import com.naoto.yamaguchi.miita.util.exception.MiitaException;

/**
 * Item Model.
 *
 * Created by naoto on 16/08/15.
 */
public final class ItemModel {

  public enum Type {
    CHECK {
      @Override
      public Method toMethod() {
        return Method.GET;
      }
    },
    STOCK {
      @Override
      public Method toMethod() {
        return Method.PUT;
      }
    },
    UNSTOCK {
      @Override
      public Method toMethod() {
        return Method.DELETE;
      }
    };

    public abstract Method toMethod();
  }

  private final Context context;
  private final ItemService service;
  private OnModelListener<Void> listener;

  public ItemModel(Context context) {
    this.context = context;
    this.service = new ItemService();
  }

  public void request(final Type type, final String itemId, OnModelListener<Void> listener) {
    this.listener = listener;
    this.service
            .setMethod(type.toMethod())
            .setItemId(itemId);
    API.request(this.context, this.service, new Callback<Void>() {
      @Override
      public void onResponse(Response<Void> response) {
        callSuccess();
      }

      @Override
      public void onFailure(HttpException e) {
        callError(e);
      }
    });
  }

  private void callSuccess() {
    if (this.listener != null) {
      this.listener.onSuccess(null);
      this.listener.onComplete();
    }
  }

  private void callError(HttpException e) {
    MiitaException exception = new MiitaException(e.getMessage());
    if (this.listener != null) {
      this.listener.onError(exception);
      this.listener.onComplete();
    }
  }
}
