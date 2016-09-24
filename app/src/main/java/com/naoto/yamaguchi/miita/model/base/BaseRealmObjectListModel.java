package com.naoto.yamaguchi.miita.model.base;

import android.content.Context;

import com.naoto.yamaguchi.miita.api.APIException;
import com.naoto.yamaguchi.miita.dao.base.BaseDao;
import com.naoto.yamaguchi.miita.util.ThreadType;
import com.naoto.yamaguchi.miita.util.ThreadUtil;

import java.util.List;

import io.realm.RealmObject;

/**
 * Created by naoto on 2016/09/23.
 */

public abstract class BaseRealmObjectListModel<T extends RealmObject, D extends BaseDao<T>>
        extends BaseModel<List<T>> {

    protected int page;
    protected boolean isPaging;
    protected D dao;

    protected abstract D getDaoInstance();
    protected abstract void serviceRequest(RequestType type);

    public BaseRealmObjectListModel(Context context) {
        super(context);
        this.page = 1;
        this.isPaging = false;
        this.dao = this.getDaoInstance();
    }

    public int getPage() {
        return this.page;
    }

    public boolean isPaging() {
        return this.isPaging;
    }

    public List<T> load() {
        return this.dao.findAll();
    }

    public void close() {
        this.dao.close();
    }

    public void request(RequestType type, OnModelListener<List<T>> listener) {
        this.addModelListener(listener);

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

    protected void deliverSuccess(final RequestType type, final List<T> results) {
        ThreadUtil.execute(ThreadType.MAIN, new Runnable() {
            @Override
            public void run() {
                isPaging = false;
                List<T> objects = null;

                switch (type) {
                    case FIRST:
                    case REFRESH:
                        dao.truncate();
                        objects = dao.insert(results);
                        break;
                    case PAGING:
                        objects = dao.insert(results);
                        break;
                }

                listener.onSuccess(objects);
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
