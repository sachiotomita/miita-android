package com.naoto.yamaguchi.miita.model;

import android.content.Context;

import com.naoto.yamaguchi.miita.api.APIException;
import com.naoto.yamaguchi.miita.entity.StockItem;
import com.naoto.yamaguchi.miita.service.StockItemService;

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

    public StockItemModel(Context context) {
        this.context = context;
        this.page = 1;
        this.isPaging = false;
        this.service = new StockItemService(this.context);
    }

}
