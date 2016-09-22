package com.naoto.yamaguchi.miita.model.base;

import android.content.Context;

import com.naoto.yamaguchi.miita.api.APIException;
import com.naoto.yamaguchi.miita.util.RequestType;

import java.util.List;

/**
 * Created by naoto on 16/09/22.
 */
public abstract class BaseObjectListModel<T> extends BaseModel<T> {

    protected int page;
    protected boolean isPaging;

    abstract void serviceRequest();
    abstract void deliverSuccess(final RequestType type, final List<T> list);
    abstract void deliverError(final APIException e);

    public BaseObjectListModel(Context context) {
        super(context);
        this.page = 1;
        this.isPaging = false;
    }

    protected int getPage() {
        return this.page;
    }

    protected boolean isPaging() {
        return this.isPaging;
    }

    protected void request(final RequestType type, OnModelListener<List<T>> listener) {
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

        this.serviceRequest();
    }
}
