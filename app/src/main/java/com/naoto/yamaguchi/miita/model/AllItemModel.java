package com.naoto.yamaguchi.miita.model;

import android.content.Context;

import com.naoto.yamaguchi.miita.api.API;
import com.naoto.yamaguchi.miita.api.Callback;
import com.naoto.yamaguchi.miita.api.HttpException;
import com.naoto.yamaguchi.miita.api.Response;
import com.naoto.yamaguchi.miita.dao.AllItemDao;
import com.naoto.yamaguchi.miita.dao.DaoFactory;
import com.naoto.yamaguchi.miita.entity.AllItem;
import com.naoto.yamaguchi.miita.model.base.OnModelListener;
import com.naoto.yamaguchi.miita.service.AllItemService;
import com.naoto.yamaguchi.miita.model.base.RequestType;
import com.naoto.yamaguchi.miita.util.exception.MiitaException;

import java.util.List;

/**
 * AllItem Mode.
 *
 * Created by naoto on 16/06/30.
 */
public final class AllItemModel {

  private Context context;
  private AllItemService service;
  private AllItemDao dao;
  private OnModelListener<List<AllItem>> listener;

  public AllItemModel(Context context) {
    this.context = context;
    this.service = new AllItemService(this.context);
    this.dao = DaoFactory.getAllItemDao();
  }

  public void request(int page, final RequestType type, OnModelListener<List<AllItem>> listener) {
    this.listener = listener;
    this.service.setPage(page);
    API.request(this.context, this.service, new Callback<List<AllItem>>() {
      @Override
      public void onResponse(Response<List<AllItem>> response) {
        callSuccessAndProcessResult(type, response);
      }

      @Override
      public void onFailure(HttpException e) {
        callError(e);
      }
    });
  }

  public List<AllItem> loadItems() {
    return this.dao.findAll();
  }

  public void close() {
    this.dao.close();
  }

  public void callSuccessAndProcessResult(RequestType type, Response<List<AllItem>> response) {
    List<AllItem> realmItems = null;

    switch (type) {
      case FIRST:
      case REFRESH:
        this.dao.truncate();
        realmItems = this.dao.insert(response.result());
        break;
      case PAGING:
        realmItems = this.dao.insert(response.result());
        break;
    }

    if (this.listener != null) {
      this.listener.onSuccess(realmItems);
      this.listener.onComplete();
    }
  }

  public void callError(HttpException e) {
    MiitaException exception = new MiitaException(e.getMessage());
    if (this.listener != null) {
      this.listener.onError(exception);
      this.listener.onComplete();
    }
  }
}
