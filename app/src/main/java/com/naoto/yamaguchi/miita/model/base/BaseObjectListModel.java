package com.naoto.yamaguchi.miita.model.base;

import android.content.Context;

import com.naoto.yamaguchi.miita.api.APIException;
import com.naoto.yamaguchi.miita.util.RequestType;
import com.naoto.yamaguchi.miita.util.ThreadType;
import com.naoto.yamaguchi.miita.util.ThreadUtil;

import java.util.List;

/**
 * Created by naoto on 16/09/22.
 */
public abstract class BaseObjectListModel<T> extends BaseModel<List<T>> {

    protected int page;
    protected boolean isPaging;

    protected abstract void serviceRequest(RequestType type);

    public BaseObjectListModel(Context context) {
        super(context);
        this.page = 1;
        this.isPaging = false;
    }

    public int getPage() {
        return this.page;
    }

    public boolean isPaging() {
        return this.isPaging;
    }

    public void request(RequestType type, OnModelListener<List<T>> listener) {
        super.addModelListener(listener);

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

        this.serviceRequest(type);
    }

    protected void deliverSuccess(RequestType type, final List<T> results) {
        ThreadUtil.execute(ThreadType.MAIN, new Runnable() {
            @Override
            public void run() {
                isPaging = false;
                listener.onSuccess(results);
                listener.onComplete();
            }
        });
    }

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
