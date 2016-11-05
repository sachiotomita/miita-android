package com.naoto.yamaguchi.miita.model;

import android.content.Context;

import com.naoto.yamaguchi.miita.api.API;
import com.naoto.yamaguchi.miita.api.Callback;
import com.naoto.yamaguchi.miita.api.HttpException;
import com.naoto.yamaguchi.miita.api.Response;
import com.naoto.yamaguchi.miita.dao.DaoFactory;
import com.naoto.yamaguchi.miita.dao.StockItemDao;
import com.naoto.yamaguchi.miita.entity.StockItem;
import com.naoto.yamaguchi.miita.model.base.OnModelListener;
import com.naoto.yamaguchi.miita.oauth.CurrentUser;
import com.naoto.yamaguchi.miita.service.StockItemService;
import com.naoto.yamaguchi.miita.model.base.RequestType;
import com.naoto.yamaguchi.miita.util.exception.MiitaException;

import java.util.List;

/**
 * Stock Item Model.
 *
 * Created by naoto on 16/06/30.
 */
public final class StockItemModel {

  private final Context context;
  private final StockItemService service;
  private final StockItemDao dao;
  private final CurrentUser currentUser;
  private OnModelListener<List<StockItem>> listener;

  public StockItemModel(Context context) {
    this.context = context;
    this.service = new StockItemService();
    this.dao = DaoFactory.getStockItemDao();
    this.currentUser = CurrentUser.getInstance();
  }

  public void request(final int page, final RequestType type,
                      OnModelListener<List<StockItem>> listener) {
    final String userId = this.currentUser.getID();
    this.listener = listener;
    this.service
            .setUserId(userId)
            .setPage(page);
    API.request(this.service, new Callback<List<StockItem>>() {
      @Override
      public void onResponse(Response<List<StockItem>> response) {
        callSuccessAndProcessResult(type, response);
      }

      @Override
      public void onFailure(HttpException e) {
        callError(e);
      }
    });
  }

  public List<StockItem> loadItems() {
    return this.dao.findAll();
  }

  public void close() {
    this.dao.close();
  }

  private void callSuccessAndProcessResult(RequestType type, Response<List<StockItem>> response) {
    List<StockItem> realmItems = null;

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

  private void callError(HttpException e) {
    MiitaException exception = new MiitaException(e.getMessage());
    if (this.listener != null) {
      this.listener.onError(exception);
      this.listener.onComplete();
    }
  }
}
