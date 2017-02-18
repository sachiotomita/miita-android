package com.naoto.yamaguchi.miita.presenter;

import android.content.Context;

import com.naoto.yamaguchi.miita.entity.ui.Item;
import com.naoto.yamaguchi.miita.model.ModelFactory;
import com.naoto.yamaguchi.miita.model.TagItemModel;
import com.naoto.yamaguchi.miita.model.base.OnModelListener;
import com.naoto.yamaguchi.miita.model.base.RequestType;
import com.naoto.yamaguchi.miita.presenter.base.Presenter;
import com.naoto.yamaguchi.miita.util.exception.MiitaException;

import java.util.List;

/**
 * Tag Item Presenter.
 * <p>
 * Created by naoto on 2016/10/06.
 */

public final class TagItemPresenter extends Presenter<TagItemPresenter.View> {
    public interface View {
        void showLoading();

        void hideLoading();

        void showListView();

        void hideListView();

        void beginRefreshing();

        void endRefreshing();

        void addFooterView();

        void removeFooterView();

        void reloadData(boolean forceUpdate, List<Item> items);

        void showError(MiitaException e);
    }

    private final TagItemModel model;
    private String tagId;
    private int page;
    private boolean isPaging;

    public TagItemPresenter(Context context) {
        super(context);
        this.model = ModelFactory.getTagItemModel(context);
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

    public TagItemPresenter setTagId(String tagId) {
        this.tagId = tagId;
        return this;
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

        this.model.request(this.tagId, this.page, new OnModelListener<List<Item>>() {
            @Override
            public void onSuccess(List<Item> results) {
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

    private void reloadData(RequestType type, List<Item> results) {
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
