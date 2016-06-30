package com.naoto.yamaguchi.miita.model;

import android.content.Context;

import com.naoto.yamaguchi.miita.api.APIException;
import com.naoto.yamaguchi.miita.dao.AllItemDao;
import com.naoto.yamaguchi.miita.entity.AllItem;
import com.naoto.yamaguchi.miita.service.AllItemService;
import com.naoto.yamaguchi.miita.util.RequestType;
import com.naoto.yamaguchi.miita.util.ThreadType;
import com.naoto.yamaguchi.miita.util.ThreadUtil;

import java.util.List;

/**
 * Created by naoto on 16/06/30.
 */
public final class AllItemModel {

    public interface OnRequestListener {
        void onSuccess(List<AllItem> results);
        void onError(APIException e);
        void onComplete();
    }

    private Context context;
    private int page;
    private boolean isPaging;
    private OnRequestListener listener;
    private AllItemService service;
    private AllItemDao dao;

    public AllItemModel(Context context) {
        this.context = context;
        this.page = 1;
        this.isPaging = false;
        this.service = new AllItemService(this.context);
        this.dao = new AllItemDao();
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

        this.service.request(this.page, new AllItemService.OnRequestListener() {
            @Override
            public void onSuccess(List<AllItem> result) {
                deliverSuccess(type, result);
            }

            @Override
            public void onError(APIException e) {
                deliverError(e);
            }
        });
    }

    public List<AllItem> loadItem() {
        return this.dao.findAll();
    }

    public void close() {
        this.dao.close();
    }

    private void addRequestListener(OnRequestListener listener) {
        this.listener = listener;
    }

    private void deliverSuccess(final RequestType type, final List<AllItem> results) {
        ThreadUtil.execute(ThreadType.MAIN, new Runnable() {
            @Override
            public void run() {
                isPaging = false;
                List<AllItem> items = null;

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
