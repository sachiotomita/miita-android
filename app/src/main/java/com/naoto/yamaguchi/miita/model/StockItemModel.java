package com.naoto.yamaguchi.miita.model;

import android.content.Context;

import com.naoto.yamaguchi.miita.api.APIException;
import com.naoto.yamaguchi.miita.dao.DaoFactory;
import com.naoto.yamaguchi.miita.dao.StockItemDao;
import com.naoto.yamaguchi.miita.entity.StockItem;
import com.naoto.yamaguchi.miita.model.base.BaseModel;
import com.naoto.yamaguchi.miita.model.base.BaseRealmObjectListModel;
import com.naoto.yamaguchi.miita.model.base.UsingDaoModelType;
import com.naoto.yamaguchi.miita.oauth.CurrentUser;
import com.naoto.yamaguchi.miita.service.StockItemService;
import com.naoto.yamaguchi.miita.model.base.RequestType;
import com.naoto.yamaguchi.miita.service.base.OnRequestListener;

import java.util.List;

/**
 * Created by naoto on 16/06/30.
 */
public final class StockItemModel extends BaseModel<List<StockItem>>
        implements UsingDaoModelType<StockItem, StockItemDao> {

    private StockItemService service;
    private CurrentUser currentUser;
    private StockItemDao dao;

    public StockItemModel(Context context) {
        super(context);
        this.service = new StockItemService(this.context);
        this.currentUser = CurrentUser.getInstance();
        this.dao = this.getDaoInstance();
    }

    @Override
    public StockItemDao getDaoInstance() {
        return DaoFactory.getStockItemDao();
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
    protected boolean isListView() {
        return true;
    }

    @Override
    protected void serviceRequest(final RequestType type) {
        String userId = this.currentUser.getID(this.context);
        this.service.request(this.page, userId, new OnRequestListener<List<StockItem>>() {
            @Override
            public void onSuccess(List<StockItem> results) {
                deliverSuccessAndProcessResults(type, results);
            }

            @Override
            public void onError(APIException e) {
                deliverError(e);
            }
        });
    }

    @Override
    protected List<StockItem> processResults(RequestType type, List<StockItem> results) {
        List<StockItem> realmItems = null;

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
