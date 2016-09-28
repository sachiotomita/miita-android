package com.naoto.yamaguchi.miita.model;

import android.content.Context;

import com.naoto.yamaguchi.miita.api.APIException;
import com.naoto.yamaguchi.miita.dao.AllItemDao;
import com.naoto.yamaguchi.miita.dao.DaoFactory;
import com.naoto.yamaguchi.miita.entity.AllItem;
import com.naoto.yamaguchi.miita.model.base.BaseModel;
import com.naoto.yamaguchi.miita.model.base.BaseRealmObjectListModel;
import com.naoto.yamaguchi.miita.model.base.UsingDaoModelType;
import com.naoto.yamaguchi.miita.service.AllItemService;
import com.naoto.yamaguchi.miita.model.base.RequestType;
import com.naoto.yamaguchi.miita.service.base.BaseService;
import com.naoto.yamaguchi.miita.service.base.OnRequestListener;

import java.util.List;

/**
 * Created by naoto on 16/06/30.
 */
public final class AllItemModel extends BaseModel<List<AllItem>> implements UsingDaoModelType<AllItem, AllItemDao> {

    private AllItemService service;
    private AllItemDao dao;

    public AllItemModel(Context context) {
        super(context);
        this.service = new AllItemService(this.context);
        this.dao = this.getDaoInstance();
    }

    @Override
    public AllItemDao getDaoInstance() {
        return DaoFactory.getAllItemDao();
    }

    @Override
    public List<AllItem> load() {
        return this.dao.findAll();
    }

    @Override
    public void close() {
        this.dao.close();
    }

    @Override
    protected boolean isListView() {
        return true;
    }

    @Override
    protected void serviceRequest(final RequestType type) {
        this.service.request(this.page, new OnRequestListener<List<AllItem>>() {
            @Override
            public void onSuccess(List<AllItem> results) {
                deliverSuccessAndProcessResults(type, results);
            }

            @Override
            public void onError(APIException e) {
                deliverError(e);
            }
        });
    }

    @Override
    protected List<AllItem> processResults(RequestType type, List<AllItem> results) {
        List<AllItem> realmItems = null;

        switch (type) {
            case FIRST:
            case REFRESH:
                this.dao.truncate();
                realmItems = this.dao.insert(results);
                break;
            case PAGING:
                realmItems = this.dao.insert(results);
                break;
        }

        return realmItems;
    }
}
