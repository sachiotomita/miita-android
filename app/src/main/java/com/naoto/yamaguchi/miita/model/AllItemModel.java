package com.naoto.yamaguchi.miita.model;

import android.content.Context;

import com.naoto.yamaguchi.miita.api.APIException;
import com.naoto.yamaguchi.miita.dao.AllItemDao;
import com.naoto.yamaguchi.miita.entity.AllItem;
import com.naoto.yamaguchi.miita.model.base.BaseObjectListModel;
import com.naoto.yamaguchi.miita.service.AllItemService;
import com.naoto.yamaguchi.miita.util.RequestType;
import com.naoto.yamaguchi.miita.util.ThreadType;
import com.naoto.yamaguchi.miita.util.ThreadUtil;

import java.util.List;

/**
 * Created by naoto on 16/06/30.
 */
public final class AllItemModel extends BaseObjectListModel<AllItem> {

    private AllItemService service;
    private AllItemDao dao;

    public AllItemModel(Context context) {
        super(context);
        this.service = new AllItemService(this.context);
        this.dao = new AllItemDao();
    }

    @Override
    protected void serviceRequest(final RequestType type) {
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

    @Override
    protected List<AllItem> load() {
        return this.dao.findAll();
    }

    @Override
    protected void close() {
        this.dao.close();
    }

    @Override
    protected void deliverSuccess(final RequestType type, final List<AllItem> results) {
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
