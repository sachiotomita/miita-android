package com.naoto.yamaguchi.miita.model.base;

import com.naoto.yamaguchi.miita.dao.base.BaseDao;

import java.util.List;

import io.realm.RealmObject;

/**
 * Created by naoto on 2016/09/28.
 */

public interface UsingDaoModelType<T extends RealmObject, D extends BaseDao<T>> {
    D getDao();
    public List<T> load();
    public void close();
}
