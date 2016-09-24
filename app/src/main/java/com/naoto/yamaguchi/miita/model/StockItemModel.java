package com.naoto.yamaguchi.miita.model;

import android.content.Context;

import com.naoto.yamaguchi.miita.api.APIException;
import com.naoto.yamaguchi.miita.dao.StockItemDao;
import com.naoto.yamaguchi.miita.entity.StockItem;
import com.naoto.yamaguchi.miita.model.base.BaseRealmObjectListModel;
import com.naoto.yamaguchi.miita.oauth.CurrentUser;
import com.naoto.yamaguchi.miita.service.StockItemService;
import com.naoto.yamaguchi.miita.model.base.RequestType;

import java.util.List;

/**
 * Created by naoto on 16/06/30.
 */
public final class StockItemModel extends BaseRealmObjectListModel<StockItem, StockItemDao> {

    private StockItemService service;
    private CurrentUser currentUser;

    public StockItemModel(Context context) {
        super(context);
        this.service = new StockItemService(this.context);
        this.currentUser = CurrentUser.getInstance();
    }

    @Override
    protected StockItemDao getDaoInstance() {
        return new StockItemDao();
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
}
