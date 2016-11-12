package com.naoto.yamaguchi.miita.presenter;

import android.content.Context;

import com.naoto.yamaguchi.miita.entity.StockItem;
import com.naoto.yamaguchi.miita.model.StockItemModel;
import com.naoto.yamaguchi.miita.model.base.OnModelListener;
import com.naoto.yamaguchi.miita.model.base.RequestType;
import com.naoto.yamaguchi.miita.presenter.base.Presenter;
import com.naoto.yamaguchi.miita.util.exception.MiitaException;

import java.util.List;

/**
 * StockItem Presenter.
 *
 * Created by naoto on 2016/10/02.
 */

public final class StockItemPresenter extends Presenter<StockItemPresenter.View> {
  public interface View {
    void showLoading();
    void hideLoading();
    void showListView();
    void hideListView();
    void beginRefreshing();
    void endRefreshing();
    void addFooterView();
    void removeFooterView();
    void reloadData(boolean forceUpdate, List<StockItem> items);
    void showError(MiitaException e);
  }

  private final StockItemModel model;
  private int page;
  private boolean isPaging;

  public StockItemPresenter(Context context) {
    super(context);
    this.model = new StockItemModel(context);
    this.page = 1;
    this.isPaging = false;
  }

  @Override
  public void onResume() {
    // NOOP
  }

  @Override
  public void onPause() {
    // NOOP
  }

  public int getPage() {
    return this.page;
  }

  public boolean isPaging() {
    return this.isPaging;
  }

  public void loadItems() {
    this.view.showLoading();
    this.view.hideListView();
    this.request(RequestType.FIRST);
  }

  public void refreshItems() {
    this.view.beginRefreshing();
    this.request(RequestType.REFRESH);
  }

  public void nextLoadItems() {
    if (this.isPaging()) return;
    this.view.addFooterView();
    this.request(RequestType.PAGING);
  }

  private void request(final RequestType type) {
    switch (type) {
      case FIRST:
      case REFRESH:
        this.page = 1;
        this.isPaging = false;
        break;
      case PAGING:
        this.page++;
        this.isPaging = true;
        break;
    }

    this.model.request(this.page, type, new OnModelListener<List<StockItem>>() {
      @Override
      public void onSuccess(List<StockItem> results) {
        isPaging = false;
        reloadData(type, results);
      }

      @Override
      public void onError(MiitaException e) {
        isPaging = false;
        view.showError(e);
      }

      @Override
      public void onComplete() {
        invalidateView();
      }
    });
  }

  private void reloadData(RequestType type, List<StockItem> results) {
    switch (type) {
      case FIRST:
      case REFRESH:
        this.view.reloadData(true, results);
        break;
      case PAGING:
        this.view.reloadData(false, results);
        break;
    }
  }

  private void invalidateView() {
    this.view.showListView();
    this.view.hideLoading();
    this.view.endRefreshing();
    this.view.removeFooterView();
  }
}
