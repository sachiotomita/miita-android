package com.naoto.yamaguchi.miita.model;

import android.content.Context;

import com.naoto.yamaguchi.miita.api.APIException;
import com.naoto.yamaguchi.miita.dao.StockItemDao;
import com.naoto.yamaguchi.miita.entity.StockItem;
import com.naoto.yamaguchi.miita.oauth.CurrentUser;
import com.naoto.yamaguchi.miita.service.StockItemService;
import com.naoto.yamaguchi.miita.util.RequestType;
import com.naoto.yamaguchi.miita.util.ThreadType;
import com.naoto.yamaguchi.miita.util.ThreadUtil;

import java.util.List;

/**
 * Created by naoto on 16/06/30.
 */
public final class StockItemModel {

    public interface OnRequestListener {
        void onSuccess(List<StockItem> results);
        void onError(APIException e);
        void onComplete();
    }

    private Context context;
    private int page;
    private boolean isPaging;
    private OnRequestListener listener;
    private StockItemService service;
    private StockItemDao dao;
    private CurrentUser currentUser;

    public StockItemModel(Context context) {
        this.context = context;
        this.page = 1;
        this.isPaging = false;
        this.service = new StockItemService(this.context);
        this.dao = new StockItemDao();
        this.currentUser = CurrentUser.getInstance();
    }

    public int getPage() {
        return this.page;
    }

    public boolean isPaging() {
        return this.isPaging;
    }

    public void request(final RequestType type, OnRequestListener listener) {
        this.addRequestListener(listener);

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

        String userId = this.currentUser.getID(this.context);

        this.service.request(this.page, userId, new StockItemService.OnRequestListener() {
            @Override
            public void onSuccess(List<StockItem> results) {
                deliverSuccess(type, results);
            }

            @Override
            public void onError(APIException e) {
                deliverError(e);
            }
        });
    }

    public List<StockItem> loadItem() {
        return this.dao.findAll();
    }

    public void close() {
        this.dao.close();
    }

    private void addRequestListener(OnRequestListener listener) {
        this.listener = listener;
    }

    private void deliverSuccess(final RequestType type, final List<StockItem> results) {
        ThreadUtil.execute(ThreadType.MAIN, new Runnable() {
            @Override
            public void run() {
                isPaging = false;
                List<StockItem> items = null;

                switch (type) {
                    case FIRST:
                    case REFRESH:
                        dao.truncate();
                        items = dao.insert(results);
                        break;
                    case PAGING:
                        items = dao.insert(results);
                        break;
                }

                listener.onSuccess(items);
                listener.onComplete();
            }
        });
    }

    private void deliverError(final APIException e) {
        ThreadUtil.execute(ThreadType.MAIN, new Runnable() {
            @Override
            public void run() {
                isPaging = false;
                listener.onError(e);
                listener.onComplete();
            }
        });
    }
}
