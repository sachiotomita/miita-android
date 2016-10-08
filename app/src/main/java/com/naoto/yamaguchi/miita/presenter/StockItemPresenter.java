package com.naoto.yamaguchi.miita.presenter;

import android.content.Context;

import com.naoto.yamaguchi.miita.ex_api.APIException;
import com.naoto.yamaguchi.miita.entity.StockItem;
import com.naoto.yamaguchi.miita.model.StockItemModel;
import com.naoto.yamaguchi.miita.model.base.OnModelListener;
import com.naoto.yamaguchi.miita.model.base.RequestType;
import com.naoto.yamaguchi.miita.presenter.base.Presenter;

import java.util.List;

/**
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
        void reloadData(List<StockItem> items);
        void showError(APIException e);
    }

    private final StockItemModel model;

    public StockItemPresenter(Context context) {
        super(context);
        this.model = new StockItemModel(context);
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
        if (this.model.isPaging()) return;
        this.view.addFooterView();
        this.request(RequestType.PAGING);
    }

    private void request(RequestType type) {
        this.model.request(type, new OnModelListener<List<StockItem>>() {
            @Override
            public void onSuccess(List<StockItem> results) {
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
