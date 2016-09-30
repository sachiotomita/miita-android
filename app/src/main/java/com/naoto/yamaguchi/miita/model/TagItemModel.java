package com.naoto.yamaguchi.miita.model;

import android.content.Context;

import com.naoto.yamaguchi.miita.api.APIException;
import com.naoto.yamaguchi.miita.entity.Item;
import com.naoto.yamaguchi.miita.model.base.BaseModel;
import com.naoto.yamaguchi.miita.model.base.BaseObjectListModel;
import com.naoto.yamaguchi.miita.service.TagItemService;
import com.naoto.yamaguchi.miita.model.base.RequestType;
import com.naoto.yamaguchi.miita.service.base.OnRequestListener;

import java.util.List;

/**
 * Created by naoto on 16/07/17.
 */
public final class TagItemModel extends BaseModel<List<Item>> {

    private String tagId;
    private TagItemService service;

    public TagItemModel(Context context) {
        super(context);
        this.service = new TagItemService(context);
    }

    public TagItemModel setTagId(String tagId) {
        this.tagId = tagId;
        return this;
    }

    @Override
    protected boolean isListView() {
        return true;
    }

    @Override
    protected void serviceRequest(RequestType type) {
        this.service.request(this.page, this.tagId, new OnRequestListener<List<Item>>() {
            @Override
            public void onSuccess(List<Item> results) {
                deliverSuccess(results);
            }

            @Override
            public void onError(APIException e) {
                deliverError(e);
            }
        });
    }

    @Override
    protected List<Item> processResults(RequestType type, List<Item> results) {
        return null;
    }
}
