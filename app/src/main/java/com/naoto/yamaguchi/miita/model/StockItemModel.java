package com.naoto.yamaguchi.miita.model;

import android.content.Context;

import com.naoto.yamaguchi.miita.api.APIException;
import com.naoto.yamaguchi.miita.dao.StockItemDao;
import com.naoto.yamaguchi.miita.entity.StockItem;
import com.naoto.yamaguchi.miita.model.base.BaseObjectListModel;
import com.naoto.yamaguchi.miita.oauth.CurrentUser;
import com.naoto.yamaguchi.miita.service.StockItemService;
import com.naoto.yamaguchi.miita.util.RequestType;
import com.naoto.yamaguchi.miita.util.ThreadType;
import com.naoto.yamaguchi.miita.util.ThreadUtil;

import java.util.List;

/**
 * Created by naoto on 16/06/30.
 */
public final class StockItemModel extends BaseObjectListModel<StockItem> {

    private StockItemService service;
    private StockItemDao dao;
    private CurrentUser currentUser;

    public StockItemModel(Context context) {
        super(context);
        this.service = new StockItemService(this.context);
        this.dao = new StockItemDao();
        this.currentUser = CurrentUser.getInstance();
    }

    @Override
    public List<StockItem> load() {
        return this.dao.findAll();
    }

    @Override
    public void close() {
        this.dao.close();
    }

    @Override
    protected void serviceRequest(final RequestType type) {
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

    @Override
    protected void deliverSuccess(final RequestType type, final List<StockItem> results) {
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

    @Override
    protected void deliverError(final APIException e) {
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
