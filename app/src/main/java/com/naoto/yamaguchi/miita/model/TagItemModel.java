package com.naoto.yamaguchi.miita.model;

import android.content.Context;

import com.naoto.yamaguchi.miita.api.APIException;
import com.naoto.yamaguchi.miita.entity.Item;
import com.naoto.yamaguchi.miita.service.TagItemService;
import com.naoto.yamaguchi.miita.util.RequestType;
import com.naoto.yamaguchi.miita.util.ThreadType;
import com.naoto.yamaguchi.miita.util.ThreadUtil;

import java.util.List;

/**
 * Created by naoto on 16/07/17.
 */
public final class TagItemModel {

    public interface OnRequestListener {
        void onSuccess(List<Item> results);
        void onError(APIException e);
        void onComplete();
    }

    private Context context;
    private int page;
    private String tagId;
    private boolean isPaging;
    private OnRequestListener listener;
    private TagItemService service;

    public TagItemModel(Context context) {
        this.context = context;
        this.page = 1;
        this.isPaging = false;
    }

    public int getPage() {
        return this.page;
    }

    public boolean isPaging() {
        return this.isPaging;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public void request(RequestType type, OnRequestListener listener) {
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


        this.service.request(this.page, this.tagId, new TagItemService.OnRequestListener() {
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

    private void addRequestListener(OnRequestListener listener) {
        this.listener = listener;
    }

    private void deliverSuccess(final List<Item> results) {
        ThreadUtil.execute(ThreadType.MAIN, new Runnable() {
            @Override
            public void run() {
                isPaging = false;
                listener.onSuccess(results);
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
