package com.naoto.yamaguchi.miita.model;

import android.content.Context;

import com.naoto.yamaguchi.miita.api.APIException;
import com.naoto.yamaguchi.miita.dao.AllItemDao;
import com.naoto.yamaguchi.miita.entity.AllItem;
import com.naoto.yamaguchi.miita.model.base.BaseRealmObjectListModel;
import com.naoto.yamaguchi.miita.service.AllItemService;
import com.naoto.yamaguchi.miita.model.base.RequestType;

import java.util.List;

/**
 * Created by naoto on 16/06/30.
 */
public final class AllItemModel extends BaseRealmObjectListModel<AllItem, AllItemDao> {

    private AllItemService service;

    public AllItemModel(Context context) {
        super(context);
        this.service = new AllItemService(this.context);
    }

    @Override
    protected AllItemDao getDaoInstance() {
        return new AllItemDao();
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
}
