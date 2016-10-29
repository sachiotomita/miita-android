package com.naoto.yamaguchi.miita.presenter;

import android.content.Context;

import com.naoto.yamaguchi.miita.entity.AllItem;
import com.naoto.yamaguchi.miita.model.AllItemModel;
import com.naoto.yamaguchi.miita.model.base.OnModelListener;
import com.naoto.yamaguchi.miita.model.base.RequestType;
import com.naoto.yamaguchi.miita.presenter.base.Presenter;
import com.naoto.yamaguchi.miita.util.exception.MiitaException;

import java.util.List;

/**
 * AllItem Presenter.
 *
 * Created by naoto on 2016/10/01.
 */

public final class AllItemPresenter extends Presenter<AllItemPresenter.View> {

  public interface View {
    void showLoading();
    void hideLoading();
    void showListView();
    void hideListView();
    void beginRefreshing();
    void endRefreshing();
    void addFooterView();
    void removeFooterView();
    void reloadData(List<AllItem> items);
    void showError(MiitaException e);
  }

  private final AllItemModel model;
  private int page;
  private boolean isPaging;

  public AllItemPresenter(Context context) {
    super(context);
    this.model = new AllItemModel(context);
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
    if (this.isPaging()) {
      return;
    }

    this.view.addFooterView();
    this.request(RequestType.PAGING);
  }

  private void request(RequestType type) {
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

    this.model.request(this.page, type, new OnModelListener<List<AllItem>>() {
      @Override
      public void onSuccess(List<AllItem> results) {
        isPaging = false;
        view.reloadData(results);
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

  private void invalidateView() {
    this.view.showListView();
    this.view.hideLoading();
    this.view.endRefreshing();
    this.view.removeFooterView();
  }
}
