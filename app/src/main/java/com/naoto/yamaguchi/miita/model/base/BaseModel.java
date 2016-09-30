package com.naoto.yamaguchi.miita.model.base;

import android.content.Context;
import android.support.annotation.UiThread;

import com.naoto.yamaguchi.miita.api.APIException;

/**
 * Created by naoto on 16/09/22.
 */
public abstract class BaseModel<T> {
    // base
    protected final Context context;
    protected OnModelListener<T> listener;

    // for list view
    // optional variable
    protected int page;
    protected boolean isPaging;

    // if this method is true, handling page, isPaging by request type.
    protected abstract boolean isListView();
    protected abstract void serviceRequest(RequestType type);

    // `deliverSuccessAndProcessResults` call this method.
    // insert, update, truncate to Realm.
    protected abstract T processResults(RequestType type, T results);

    public BaseModel(Context context) {
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

    protected void request(RequestType type, OnModelListener<T> listener) {
        this.addModelListener(listener);

        if (this.isListView()) {
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
        }

        this.serviceRequest(type);
    }

    protected void addModelListener(OnModelListener<T> listener) {
        this.listener = listener;
    }

    @UiThread
    protected void deliverSuccessAndProcessResults(RequestType type, T results) {
        T objects = this.processResults(type, results);
        this.deliverSuccess(objects);
    }

    @UiThread
    protected void deliverSuccess(T results) {
        this.isPaging = false;
        this.listener.onSuccess(results);
        this.listener.onComplete();
    }

    @UiThread
    protected void deliverError(APIException e) {
        this.isPaging = false;
        this.listener.onError(e);
        this.listener.onComplete();
    }
}
