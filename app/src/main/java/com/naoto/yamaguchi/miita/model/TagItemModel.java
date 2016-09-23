package com.naoto.yamaguchi.miita.model;

import android.content.Context;

import com.naoto.yamaguchi.miita.api.APIException;
import com.naoto.yamaguchi.miita.entity.Item;
import com.naoto.yamaguchi.miita.model.base.BaseObjectListModel;
import com.naoto.yamaguchi.miita.service.TagItemService;
import com.naoto.yamaguchi.miita.util.RequestType;
import com.naoto.yamaguchi.miita.util.ThreadType;
import com.naoto.yamaguchi.miita.util.ThreadUtil;

import java.util.List;

/**
 * Created by naoto on 16/07/17.
 */
public final class TagItemModel extends BaseObjectListModel<Item> {

    private String tagId;
    private TagItemService service;

    public TagItemModel(Context context) {
        super(context);
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    @Override
    public List<Item> load() {
        return null;
    }

    @Override
    public void close() {
        // NOOP
    }

    @Override
    protected void serviceRequest(final RequestType type) {
        this.service.request(this.page, this.tagId, new TagItemService.OnRequestListener() {
            @Override
            public void onSuccess(List<Item> results) {
                deliverSuccess(type, results);
            }

            @Override
            public void onError(APIException e) {
                deliverError(e);
            }
        });
    }

    @Override
    protected void deliverSuccess(RequestType type, final List<Item> results) {
        ThreadUtil.execute(ThreadType.MAIN, new Runnable() {
            @Override
            public void run() {
                isPaging = false;
                listener.onSuccess(results);
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
