package com.naoto.yamaguchi.miita.model.base;

import android.support.annotation.UiThread;

import com.naoto.yamaguchi.miita.dao.base.BaseDao;

import java.util.List;

import io.realm.RealmObject;

/**
 * Created by naoto on 2016/09/28.
 */

public interface UsingDaoModelType<T extends RealmObject, D extends BaseDao<T>> {
    @UiThread
    D getDaoInstance();
    @UiThread
    public List<T> load();
    @UiThread
    public void close();
}
