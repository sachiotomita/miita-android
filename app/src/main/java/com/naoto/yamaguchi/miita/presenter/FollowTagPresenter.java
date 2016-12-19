package com.naoto.yamaguchi.miita.presenter;

import android.content.Context;

import com.naoto.yamaguchi.miita.entity.FollowTag;
import com.naoto.yamaguchi.miita.model.FollowTagModel;
import com.naoto.yamaguchi.miita.model.base.OnModelListener;
import com.naoto.yamaguchi.miita.model.base.RequestType;
import com.naoto.yamaguchi.miita.presenter.base.Presenter;
import com.naoto.yamaguchi.miita.util.exception.MiitaException;

import java.util.List;

/**
 * FollowTagPresenter.
 * <p>
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

        void reloadData(boolean forceUpdate, List<FollowTag> tags);

        void showError(MiitaException e);
    }

    private final FollowTagModel model;
    private int page;
    private boolean isPaging;

    public FollowTagPresenter(Context context) {
        super(context);
        this.model = new FollowTagModel(context);
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

        this.model.request(this.page, type, new OnModelListener<List<FollowTag>>() {
            @Override
            public void onSuccess(List<FollowTag> results) {
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

    private void reloadData(RequestType type, List<FollowTag> results) {
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
