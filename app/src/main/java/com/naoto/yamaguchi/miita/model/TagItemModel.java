package com.naoto.yamaguchi.miita.model;

import android.content.Context;

import com.naoto.yamaguchi.miita.api.API;
import com.naoto.yamaguchi.miita.api.Callback;
import com.naoto.yamaguchi.miita.api.HttpException;
import com.naoto.yamaguchi.miita.api.Response;
import com.naoto.yamaguchi.miita.entity.Item;
import com.naoto.yamaguchi.miita.model.base.OnModelListener;
import com.naoto.yamaguchi.miita.service.TagItemService;
import com.naoto.yamaguchi.miita.util.exception.MiitaException;

import java.util.List;

/**
 * Tag Item Model.
 *
 * Created by naoto on 16/07/17.
 */
public final class TagItemModel {

  private final Context context;
  private final TagItemService service;
  private OnModelListener<List<Item>> listener;

  public TagItemModel(Context context) {
    this.context = context;
    this.service = new TagItemService(this.context);
  }

  public void request(final String tagId, final int page,
                      OnModelListener<List<Item>> listener) {
    this.listener = listener;
    this.service
            .setTagId(tagId)
            .setPage(page);
    API.request(this.context, this.service, new Callback<List<Item>>() {
      @Override
      public void onResponse(Response<List<Item>> response) {
        callSuccess(response);
      }

      @Override
      public void onFailure(HttpException e) {
        callError(e);
      }
    });
  }

  private void callSuccess(Response<List<Item>> response) {
    if (this.listener != null) {
      this.listener.onSuccess(response.result());
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
