package com.naoto.yamaguchi.miita.presenter;

import android.content.Context;

import com.naoto.yamaguchi.miita.ex_api.APIException;
import com.naoto.yamaguchi.miita.entity.FollowTag;
import com.naoto.yamaguchi.miita.model.FollowTagModel;
import com.naoto.yamaguchi.miita.model.base.OnModelListener;
import com.naoto.yamaguchi.miita.model.base.RequestType;
import com.naoto.yamaguchi.miita.presenter.base.Presenter;

import java.util.List;

/**
 * FollowTagPresenter.
 *
 * Created by naoto on 2016/10/04.
 */

public final class FollowTagPresenter extends Presenter<FollowTagPresenter.View> {
  public interface View {
    void showLoading();
    void hideLoading();
    void showListView();
    void hideListView();
    void beginRefreshing();
    void endRefreshing();
    void addFooterView();
    void removeFooterView();
    void reloadData(List<FollowTag> tags);
    void showError(APIException e);
  }

  private final FollowTagModel model;

  public FollowTagPresenter(Context context) {
    super(context);
    this.model = new FollowTagModel(context);
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
    return this.model.getPage();
  }

  public void loadTags() {
    this.view.showLoading();
    this.view.hideListView();
    this.request(RequestType.FIRST);
  }

  public void refreshTags() {
    this.view.beginRefreshing();
    this.request(RequestType.REFRESH);
  }

  public void nextLoadTags() {
    if (this.model.isPaging()) return;
    this.view.addFooterView();
    this.request(RequestType.PAGING);
  }

  private void request(RequestType type) {
    this.model.request(type, new OnModelListener<List<FollowTag>>() {
      @Override
      public void onSuccess(List<FollowTag> results) {
        view.reloadData(results);
      }

      @Override
      public void onError(APIException e) {
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
