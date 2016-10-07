package com.naoto.yamaguchi.miita.presenter;

import android.content.Context;

import com.naoto.yamaguchi.miita.api.APIException;
import com.naoto.yamaguchi.miita.entity.Item;
import com.naoto.yamaguchi.miita.model.TagItemModel;
import com.naoto.yamaguchi.miita.presenter.base.Presenter;

import java.util.List;

/**
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
        void reloadData(List<Item> items);
        void showError(APIException e);
    }

    private final TagItemModel model;

    public TagItemPresenter(Context context) {
        super(context);
        this.model = new TagItemModel(context);
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

    }

    public void refreshItems() {

    }

    public void nextLoadItems() {

    }
}
